package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.TwoFactorAuthentication.OTPUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password,
                                        HttpServletRequest request) {
        try {
            if (password.length() < 7) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password + OTP is too short");
            }
            String actualPassword = password.substring(0, password.length() - 6);
            String otpString = password.substring(password.length() - 6);
            int otpCode;
            try {
                otpCode = Integer.parseInt(otpString);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP format");
            }
            String sharedSecret = "4OCBCSMUUK6VLR3I";
            boolean otpValid = OTPUtil.verifyCode(sharedSecret, otpCode);
            if (!otpValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, actualPassword);
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return ResponseEntity.ok("Login Successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        request.getSession(false).invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged Out Successfully");
    }
    @GetMapping("/session/status")
    public ResponseEntity<?> sessionStatus(HttpServletRequest request){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication!=null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
        Map<String,Object> response=new HashMap<String, Object>();
        response.put("authenticated",isAuthenticated);
        if(isAuthenticated){
            response.put("username",authentication.getName());
            response.put("role",authentication.getAuthorities());
            HttpSession session=request.getSession(false);
            if (session != null) {
                response.put("sessionCreationTime", new Date(session.getCreationTime()));
                response.put("lastAccessedTime", new Date(session.getLastAccessedTime()));
                response.put("maxInactiveIntervalSeconds", session.getMaxInactiveInterval());
                response.put("sessionExpiryTime", new Date(session.getLastAccessedTime() + session.getMaxInactiveInterval() * 1000L));
            }
        }
        return ResponseEntity.ok(response);
    }
    //4OCBCSMUUK6VLR3I
    @GetMapping("/is-admin")
    public ResponseEntity<Map<String, Object>> isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
        Map<String, Object> response = new HashMap<>();
        if (isAuthenticated) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            response.put("isAdmin", isAdmin);
        } else {
            response.put("isAdmin", false);
        }
        return ResponseEntity.ok(response);
    }

}