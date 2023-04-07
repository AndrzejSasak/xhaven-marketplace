package com.xhaven.xhavenserver.dto.auth;

import lombok.Data;

@Data
public class RegisterRequestDto {

    private final String name;
    private final String surname;
    private final String email;
    private final String phoneNumber;
    private final String password;

}
