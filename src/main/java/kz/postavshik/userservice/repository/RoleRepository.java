package kz.postavshik.userservice.repository;

import kz.postavshik.userservice.entity.Role;
import kz.postavshik.userservice.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleType> {
}
