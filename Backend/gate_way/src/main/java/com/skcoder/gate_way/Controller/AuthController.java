package com.skcoder.gate_way.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//
//import com.skcoder.gate_way.Models.LoginRequest;
//import com.skcoder.gate_way.Models.VerifyDTO;
//import com.skcoder.gate_way.Repo.RegisterRequest;
//import com.skcoder.gate_way.Services.AuthService;
//import com.skcoder.gate_way.Services.EmailService;
//import com.skcoder.gate_way.Services.JwtService;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/authv1")
//public class AuthController {
//    
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private EmailService emailService;
//    
//
//    
//    
//    @PostMapping("/register")
//    public String register(@RequestBody RegisterRequest request) {
//      String otp=   authService.register(request.getUsername(), request.getPassword(),request.getGmail());
//      System.out.println(otp);
//      emailService.sendEmail(request.getGmail(), "otp verification", "Your otp is ->"+otp);  
//         return "verify the otp to confirm registration";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//        return authService.login(request.getUsername(), request.getPassword());
//    }
//
//    @GetMapping("/validate")
//    public boolean validateToken(@RequestParam String token) {
//    	jwtService.printTokenInfo(token);
//        return jwtService.validateToken(token);
//    }
//    
//    @PostMapping("/verify")
//    public ResponseEntity<?> verify(@RequestBody  VerifyDTO verifyDTO) {
//        try {
//            String token = authService.verifyOtp(verifyDTO.getEmail(), verifyDTO.getOtp());
//            return ResponseEntity.ok(token);  
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//    
//    @GetMapping("/test")
//    public ResponseEntity<Boolean> testJwt(HttpServletRequest request) {
//        String jwt = extractJwtFromCookies(request);
//
//        if (jwt != null) {
//            System.out.println("JWT Found: " + jwt);
//            return ResponseEntity.ok(true); // ✅ JWT exists
//        } 
//
//        System.out.println("JWT Not Found");
//        return ResponseEntity.ok(false); // ❌ JWT not found
//    }
//
//    private String extractJwtFromCookies(HttpServletRequest request) {
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("jwt".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
//    
//}
