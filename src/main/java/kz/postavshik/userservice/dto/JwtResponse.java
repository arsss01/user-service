package kz.postavshik.userservice.dto;

import kz.postavshik.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private UUID userId;
    private String email;
    private Set<Role> roles;
    private String token;
    private String tokenType;
}
