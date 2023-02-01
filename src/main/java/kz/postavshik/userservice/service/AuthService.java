package kz.postavshik.userservice.service;

import kz.postavshik.userservice.dto.AuthRequest;
import kz.postavshik.userservice.dto.AuthSignupRequest;
import kz.postavshik.userservice.dto.JwtResponse;
import kz.postavshik.userservice.dto.UserDto;
import kz.postavshik.userservice.entity.Role;
import kz.postavshik.userservice.entity.RoleType;
import kz.postavshik.userservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    public JwtResponse authenticateUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        String jwt = jwtUtils.generateToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDto user = userService.getUserByEmail(authRequest.getEmail());

        return JwtResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .roles(user.getRoles())
                .tokenType("Bearer")
                .token(jwt)
                .build();
    }

    public UUID signupUser(AuthSignupRequest authRequest) {
        userService.throwErrorIfUserExitsByEmail(authRequest.getEmail());
        return userService.createUser(convertAuthRequestToUserDto(authRequest));
    }

    public Boolean validateToken(String token) {
        return jwtUtils.validateToken(jwtUtils.parseAuthorizationToken(token));
    }

    private UserDto convertAuthRequestToUserDto(AuthSignupRequest authRequest) {
        ifRolesEmptySetDefaultRole(authRequest);

        return UserDto.builder()
                .email(authRequest.getEmail())
                .password(encoder.encode(authRequest.getPassword()))
                .firstName(authRequest.getFirstName())
                .lastName(authRequest.getLastName())
                .roles(authRequest.getRoles())
                .build();
    }

    private void ifRolesEmptySetDefaultRole(AuthSignupRequest authRequest) {
        if (authRequest.getRoles() == null || authRequest.getRoles().isEmpty()) {
            authRequest.setRoles(new HashSet<>());
            authRequest.getRoles().add(new Role(RoleType.CLIENT));
        }
    }

}
