package com.dely.chat.mapper;

import com.dely.chat.entity.ChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
public interface ChatGroupMapper extends BaseMapper<ChatGroup> {

    @Select("select group_id from chat_group where owner_id = #{userId} and is_deleted = false")
    List<Long> listOwn(Long userId);
}
