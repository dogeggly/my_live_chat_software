package com.dely.chat.mapper;

import com.dely.chat.entity.GroupMember;
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
public interface GroupMemberMapper extends BaseMapper<GroupMember> {

    @Select("select group_id from group_member where user_id = #{userId}")
    List<Long> selectGroupsByUserId(Long userId);
}
