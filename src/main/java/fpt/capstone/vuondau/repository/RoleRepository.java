package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByCode(EAccountRole eAccountRole);

    List<Role> findRoleByCodeIn(List<EAccountRole> eAccountRole);
}
