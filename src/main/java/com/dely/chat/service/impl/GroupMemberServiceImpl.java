package com.dely.chat.service.impl;

import com.dely.chat.config.UserHolder;
import com.dely.chat.entity.GroupMember;
import com.dely.chat.mapper.GroupMemberMapper;
import com.dely.chat.service.IGroupMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Service
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember> implements IGroupMemberService {

    @Autowired
    private GroupMemberMapper groupMemberMapper;

    @Override
    public List<Long> selectGroupsByUserId() {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        return groupMemberMapper.selectGroupsByUserId(userId);
    }
}
