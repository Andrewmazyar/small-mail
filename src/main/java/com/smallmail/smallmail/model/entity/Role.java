package com.smallmail.smallmail.model.entity;

import lombok.Data;

@Data
public class Role {
    private Long id;
    private RoleName roleName;

    public Role() {}

    public Role(Long id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public enum RoleName {
        APICALL, USER
    }
}
