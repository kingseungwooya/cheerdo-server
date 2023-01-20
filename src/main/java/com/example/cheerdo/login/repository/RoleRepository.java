package com.example.cheerdo.login.repository;



import com.example.cheerdo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByMemberId(String memberId);

}
