package com.smallmail.smallmail.model.entity;

import java.util.Set;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private Set<Role> roles;
}
