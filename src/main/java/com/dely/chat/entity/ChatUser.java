package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dely.chat.utils.PostgresInetTypeHandler;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@Builder
@TableName(value = "public.chat_user", autoResultMap = true)
public class ChatUser {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    private String nickname;

    private String email;

    private String phone;

    private String password;

    private String bio;

    private GenderType gender;

    private Boolean isOnline;

    private LocalDateTime lastLoginAt;

    @TableField(value = "last_login_ip", typeHandler = PostgresInetTypeHandler.class)
    private String lastLoginIp;

}
