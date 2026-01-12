package com.dely.chat.mapper;

import com.dely.chat.entity.Friend;
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
public interface FriendMapper extends BaseMapper<Friend> {

    @Select("select friend_id from friend where user_id = #{userId}")
    List<Long> selectFriendsByUserId(Long userId);
}
