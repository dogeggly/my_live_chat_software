package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 群组成员实体
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Data
@Builder
@TableName("group_member")
public class GroupMember implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long groupId;

    private Long userId;

    private String memberName;

}
