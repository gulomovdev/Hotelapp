package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String Id;
    private String name;
    private String surName;
    private Address address;
    private Double balance;
    private Role role;
    private String password;
}
