package com.itechro.cas.config;


import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class SecurityKeys {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityKeys.class);

	@Autowired
	private CasProperties casProperties;

	@Bean
	public PrivateKey privateKey() throws Exception {
		String absolutePathToKey = casProperties.getPrivateKeyDerAbsolutePath();
		byte[] privKeyByteArray = null;
		try {
			InputStream privateKeyInputStream = new ClassPathResource(absolutePathToKey).getInputStream();
			privKeyByteArray = ByteStreams.toByteArray(privateKeyInputStream);
		} catch (NoSuchFileException e) {
			LOG.error("Defined private key file path :{} does not exist", absolutePathToKey);
			throw e;
		}
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privKeyByteArray);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}


	@Bean
	public String publicKeyString() throws IOException {
		StringBuffer keyString = new StringBuffer();
		String keyPath = casProperties.getPublicKeyPemAbsolutePath();
		InputStream publicKeyPemInputStream = new ClassPathResource(keyPath).getInputStream();
		String line;
		try (InputStream fis = publicKeyPemInputStream; InputStreamReader isr = new InputStreamReader(fis,
			Charset.forName("UTF-8")); BufferedReader br = new BufferedReader(isr)) {
			while ((line = br.readLine()) != null) {
				keyString.append(line).append(System.lineSeparator());
			}
		} catch (NoSuchFileException e) {
			LOG.error("Defined private key file path :{} does not exist", keyPath);
		}
		return keyString.toString();
	}

	@Bean
	public PublicKey publicKey() throws Exception {
		String keyPath = casProperties.getPublicKeyDerAbsolutePath();

		InputStream publicKeyInputStream = new ClassPathResource(keyPath).getInputStream();
		byte[] keyBytes = ByteStreams.toByteArray(publicKeyInputStream);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

}
