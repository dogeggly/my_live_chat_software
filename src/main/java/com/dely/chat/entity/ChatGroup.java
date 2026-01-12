package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.Data;

/**
 * 群组实体
 */
@Data
@TableName(value = "chat_group", autoResultMap = true)
public class ChatGroup implements Serializable {

    @TableId(value = "group_id", type = IdType.AUTO)
    private Long groupId;

    private String groupName;

    private Long ownerId;

    private String description;

    private GroupType type;

    private Integer amount;

    private Integer maxAmount;

}
