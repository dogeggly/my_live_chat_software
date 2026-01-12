package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Data
@TableName(value = "apply", autoResultMap = true)
public class Apply implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long senderId;

    private Boolean targetType;

    private Long targetId;

    private String reason;

    private ApplyStatus status;

}
