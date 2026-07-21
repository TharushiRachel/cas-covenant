package com.itechro.cas.util;


import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.PairDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author: chamara
 */
public class PasswordUtil {

    public static String generateRandomPlainPassword(Integer length) {

        final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuffer sb = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            int ndx = (int) (Math.random() * ALPHA_NUM.length());
            sb.append(ALPHA_NUM.charAt(ndx));
        }

        return sb.toString();
    }

    public static PairDTO<String, String> generateRandomEncodedPassword(PasswordEncoder passwordEncoder, Integer length) throws AppsException {
        String plainPassword = generateRandomPlainPassword(length);
        String encodedPassword = generateEncodedPassword(passwordEncoder, plainPassword);
        return PairDTO.of(plainPassword, encodedPassword);
    }

    public static String generateEncodedPassword(PasswordEncoder passwordEncoder, String plainPassword) throws AppsException {
        String encodedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(plainPassword.getBytes());
            byte[] digest = messageDigest.digest();
            encodedPassword = passwordEncoder.encode(new String(Base64.getEncoder().encode(digest)));
        } catch (Exception e) {
            throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED, e);
        }

        return encodedPassword;
    }

    public static String getCombinedUserDetailsStr(String userName, String password) {
        return userName + ":" + password;
    }

    public static String[] getSplittedUserDetailsStr(String userDetailsStr) {
        if(StringUtils.isEmpty(userDetailsStr)) {
            return null;
        }
        return userDetailsStr.split(":");
    }
}
