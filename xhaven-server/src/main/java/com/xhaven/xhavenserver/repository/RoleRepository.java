package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
