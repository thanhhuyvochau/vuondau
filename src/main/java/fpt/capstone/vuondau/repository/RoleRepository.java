package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByCode(String eAccountRole);
}
