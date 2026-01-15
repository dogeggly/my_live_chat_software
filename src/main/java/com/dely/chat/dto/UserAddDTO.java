package com.dely.chat.dto;

import lombok.Data;

@Data
public class UserAddDTO {
    private Long userId;
    private String nickname;
    private String email;
    private String phone;
    private String password;
}
