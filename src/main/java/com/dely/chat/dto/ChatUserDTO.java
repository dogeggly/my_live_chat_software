package com.dely.chat.dto;

import lombok.Data;

@Data
public class ChatUserDTO {
    private Long userId;
    private String nickname;
    private String email;
    private String phone;
    private String password;
}
