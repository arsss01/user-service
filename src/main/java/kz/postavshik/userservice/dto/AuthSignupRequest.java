package kz.postavshik.userservice.dto;

import kz.postavshik.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignupRequest extends AuthRequest {
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
