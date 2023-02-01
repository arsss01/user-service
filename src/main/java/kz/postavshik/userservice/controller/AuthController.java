package kz.postavshik.userservice.controller;

import kz.postavshik.userservice.dto.AuthRequest;
import kz.postavshik.userservice.dto.AuthSignupRequest;
import kz.postavshik.userservice.dto.JwtResponse;
import kz.postavshik.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticateUser(authRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<UUID> signupUser(@RequestBody AuthSignupRequest authRequest) {
        return ResponseEntity.ok(authService.signupUser(authRequest));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("authorization") String token) {
        return ResponseEntity.ok(authService.validateToken(token));
    }

}
