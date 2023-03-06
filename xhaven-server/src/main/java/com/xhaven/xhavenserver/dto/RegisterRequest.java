package com.xhaven.xhavenserver.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private final String name;
    private final String surname;
    private final String email;
    private final String phoneNumber;
    private final String password;

}
