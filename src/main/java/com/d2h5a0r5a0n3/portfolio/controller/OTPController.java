package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.TwoFactorAuthentication.OTPUtil;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OTPController {
    @Value("${otp.secret}")
    private String storedSecret;
    @Profile("!prod")
    @GetMapping("/generate")
    public Map<String, String> generateQR() {
        String username = "Admin";
        String issuer = "Portfolio-App";

        GoogleAuthenticatorKey key = OTPUtil.generateCredentials();
        storedSecret = key.getKey();

        String qrUrl = OTPUtil.getQRCodeURL(username, issuer, key);

        Map<String, String> response = new HashMap<>();
        response.put("secret", storedSecret);
        response.put("qrUrl", qrUrl);

        return response;
    }
    @PostMapping("/verify")
    public Map<String, Object> verify(@RequestParam int code) {
        boolean isValid = OTPUtil.verifyCode(storedSecret, code);

        Map<String, Object> response = new HashMap<>();
        response.put("verified", isValid);
        return response;
    }
}
