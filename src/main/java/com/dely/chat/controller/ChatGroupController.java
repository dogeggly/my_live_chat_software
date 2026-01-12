package com.dely.chat.controller;


import com.dely.chat.config.UserHolder;
import com.dely.chat.dto.Result;
import com.dely.chat.entity.ChatGroup;
import com.dely.chat.entity.GroupMember;
import com.dely.chat.service.IChatGroupService;
import com.dely.chat.service.IGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@RestController
@RequestMapping("/group")
public class ChatGroupController {

    @Autowired
    private IChatGroupService chatGroupService;
    private IGroupMemberService groupMemberService;

    @PostMapping
    @Transactional
    public Result create(ChatGroup chatGroup) {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        String nickname = (String) userMap.get("nickname");
        chatGroup.setOwnerId(userId);
        chatGroupService.save(chatGroup);
        GroupMember groupMember = GroupMember.builder()
                .groupId(chatGroup.getGroupId())
                .userId(userId)
                .memberName(nickname)
                .build();
        groupMemberService.save(groupMember);
        return Result.success();
    }

    @PostMapping("/join")
    public Result<Long> join(Long groupId) {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        String nickname = (String) userMap.get("nickname");
        GroupMember groupMember = GroupMember.builder()
                .groupId(groupId)
                .userId(userId)
                .memberName(nickname)
                .build();
        groupMemberService.save(groupMember);
        return Result.success();
    }
}
