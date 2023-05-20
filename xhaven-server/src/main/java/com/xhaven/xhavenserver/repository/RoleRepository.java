package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.RoleEnum;
import com.xhaven.xhavenserver.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);

}
