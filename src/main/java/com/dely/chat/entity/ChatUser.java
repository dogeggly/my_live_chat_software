package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dely.chat.utils.PostgresInetTypeHandler;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 用户实体
 */
@Data
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

    private OffsetDateTime lastLoginAt;

    @TableField(value = "last_login_ip", typeHandler = PostgresInetTypeHandler.class)
    private String lastLoginIp;

    @TableField(value = "created_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private OffsetDateTime createdTime;

    @TableField(value = "updated_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private OffsetDateTime updatedTime;
}
