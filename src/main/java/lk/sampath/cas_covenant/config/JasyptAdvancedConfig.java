package lk.sampath.cas_covenant.config;

import lk.sampath.cas_covenant.decryptor.Decryptor;
import lk.sampath.cas_covenant.enums.CipherAlgo;
import lk.sampath.cas_covenant.enums.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class JasyptAdvancedConfig {

  // private static final org.apache.logging.log4j.Logger log =
  // org.apache.logging.log4j.LogManager.getLogger(JasyptAdvancedConfig.class);

  private final Decryptor decryptor;

  @Value("${enc.key}")
  private String key;

  @Value("${cert.path}")
  private String certPath;

  @Value("${cert.password}")
  private String certPassword;

  @Bean(name = "jasyptStringEncryptor")
  public StringEncryptor getPasswordEncryptor() throws Exception {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    log.info("CERT_PATH:{}", certPath);
    String decryptValue = "";
    decryptValue =
        decryptor.decryptString(key, certPath, certPassword, FileType.P12, CipherAlgo.RSA);
    config.setPassword(decryptValue);
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
    return encryptor;
  }
}
