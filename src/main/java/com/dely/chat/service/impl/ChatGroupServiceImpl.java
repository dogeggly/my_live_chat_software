package com.dely.chat.service.impl;

import com.dely.chat.config.UserHolder;
import com.dely.chat.entity.ChatGroup;
import com.dely.chat.entity.GroupMember;
import com.dely.chat.mapper.ChatGroupMapper;
import com.dely.chat.service.IChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dely.chat.service.IGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup> implements IChatGroupService {

    @Autowired
    private ChatGroupMapper chatGroupMapper;
    @Autowired
    private IGroupMemberService groupMemberService;

    @Override
    public List<Long> listOwn() {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        return chatGroupMapper.listOwn(userId);
    }

    @Override
    @Transactional
    public void create(ChatGroup chatGroup) {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        String nickname = (String) userMap.get("nickname");
        chatGroup.setOwnerId(userId);
        this.save(chatGroup);
        GroupMember groupMember = GroupMember.builder()
                .groupId(chatGroup.getGroupId())
                .userId(userId)
                .memberName(nickname)
                .build();
        groupMemberService.save(groupMember);
    }
}
