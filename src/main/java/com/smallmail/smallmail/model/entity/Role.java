package com.smallmail.smallmail.model.entity;

import lombok.Data;

@Data
public class Role {
    private Long id;
    private RoleName roleName;

    public enum RoleName {
        APICALL, USER
    }
}
