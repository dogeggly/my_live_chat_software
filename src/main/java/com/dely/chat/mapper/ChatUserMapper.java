package com.dely.chat.mapper;

import com.dely.chat.entity.ChatUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
public interface ChatUserMapper extends BaseMapper<ChatUser> {

    @Select("select * from chat_user " +
            "where nickname like concat('%', #{nickname}::varchar, '%') or nickname % #{nickname}::varchar " +
            "order by (nickname like concat('%', #{nickname}::varchar, '%')) desc, " +
            "similarity(nickname, #{nickname}::varchar) desc " +
            "limit 30")
    List<ChatUser> findByNickname(String nickname);
}
