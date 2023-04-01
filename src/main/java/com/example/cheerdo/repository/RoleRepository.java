package com.example.cheerdo.repository;



import com.example.cheerdo.entity.Role;
import com.example.cheerdo.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByName(RoleName roleName);
    boolean existsByName(RoleName roleName);

}
