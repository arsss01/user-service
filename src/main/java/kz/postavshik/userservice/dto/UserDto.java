package kz.postavshik.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.postavshik.userservice.entity.Role;
import kz.postavshik.userservice.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
//@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @JsonIgnore
    private String password;
    private String status;
    private String type;
    private Set<Role> roles;

    public static UserDto build(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .status(user.getStatus())
                .type(user.getType())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return
                "{\"userId\":" + "\"" + userId + "\"" +
                ", \"firstName\":" + "\"" + firstName + "\"" +
                ", \"lastName\":" + "\"" + lastName + "\"" +
                ", \"email\":" + "\"" + email + "\"" +
                ", \"phone\":" + "\"" + phone + "\"" +
//                ", \"password\":" + "\""+password + "\"" +
                ", \"status\":" + "\"" + status + "\"" +
                ", \"roles\":" + roles.iterator().next().toString(roles) +
                ", \"authorities\":" + roles.iterator().next().toString(roles) +
                ", \"type\":" + "\"" + type + "\"}";
    }
}
