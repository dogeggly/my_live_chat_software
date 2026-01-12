package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 好友实体
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Data
@TableName("friend")
public class Friend implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long friendId;

    private String remark;

}
