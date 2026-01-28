package com.imo.cemetery.model.entity;

import com.imo.cemetery.model.enums.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class TokenResponse {

    private final String token;
    private final RoleType role;

}