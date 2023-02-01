package kz.postavshik.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private RoleType role;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    // to avoid infinite recursion
//    @JsonBackReference
    @JsonIgnore
    private List<User> users;

    public Role(RoleType role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }

    public String toString(Set<Role> roles) {
        Object[] rolesArray = roles.toArray();
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i=0; i < rolesArray.length; i++) {
            Role role1 = (Role) rolesArray[i];
            str.append("{\"role\":").append("\"").append(role1.getRole().name()).append("\"}");
            if (i != rolesArray.length-1) str.append(",");
        }
        str.append("]");
        return String.valueOf(str);
    }
}
