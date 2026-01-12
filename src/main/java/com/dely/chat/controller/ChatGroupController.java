package com.dely.chat.controller;

import com.dely.chat.config.UserHolder;
import com.dely.chat.dto.Result;
import com.dely.chat.entity.ChatGroup;
import com.dely.chat.entity.GroupMember;
import com.dely.chat.service.IChatGroupService;
import com.dely.chat.service.IGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Autowired
    private IGroupMemberService groupMemberService;

    @PostMapping("/create")
    public Result create(ChatGroup chatGroup) {
        chatGroupService.create(chatGroup);
        return Result.success();
    }

    @PostMapping("/join")
    public Result<Long> join(Long groupId) {
        // TODO 要根据群聊类型来判断
        chatGroupService.getById(groupId);
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

    @GetMapping("/find/{id}")
    public Result<ChatGroup> find(@PathVariable Long id) {
        ChatGroup chatGroup = chatGroupService.getById(id);
        return Result.success(chatGroup);
    }

    @GetMapping("/list")
    public Result<List<Long>> list() {
        List<Long> list = groupMemberService.selectGroupsByUserId();
        return Result.success(list);
    }

    @PutMapping("/update")
    public Result update(@RequestBody ChatGroup chatGroup) {
        chatGroupService.updateById(chatGroup);
        return Result.success();
    }

    @PutMapping("/update/memberName")
    public Result updateMemberName(@RequestBody GroupMember groupMember) {
        groupMemberService.updateById(groupMember);
        return Result.success();
    }


    @GetMapping("/member/{id}")
    public Result<List<GroupMember>> memberList(@PathVariable Long id) {
        List<GroupMember> list = groupMemberService.lambdaQuery().eq(GroupMember::getGroupId, id).list();
        return Result.success(list);
    }
}
