package com.d2h5a0r5a0n3.portfolio.TwoFactorAuthentication;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

public class OTPUtil {

    private static final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // Returns both secret key and QR URL for scanning
    public static GoogleAuthenticatorKey generateCredentials() {
        return gAuth.createCredentials();
    }

    public static String getQRCodeURL(String user, String host, GoogleAuthenticatorKey key) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(host, user, key);
    }

    public static boolean verifyCode(String secret, int code) {
        return gAuth.authorize(secret, code);
    }
}
