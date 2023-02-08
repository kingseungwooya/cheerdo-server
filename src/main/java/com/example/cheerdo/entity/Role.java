package com.example.cheerdo.entity;

import com.example.cheerdo.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 역할이름 admin, user

    @Enumerated(EnumType.STRING)
    private RoleName name;
}
