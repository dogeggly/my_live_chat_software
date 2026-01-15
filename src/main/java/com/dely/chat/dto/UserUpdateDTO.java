package com.dely.chat.dto;

import com.dely.chat.entity.GenderType;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private String nickname;
    private String bio;
    private GenderType gender;
}
