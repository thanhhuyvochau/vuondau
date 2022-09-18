package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByCode(String code);
}
