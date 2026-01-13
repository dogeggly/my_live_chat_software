package com.dely.chat.controller;

import com.dely.chat.config.UserHolder;
import com.dely.chat.dto.Result;
import com.dely.chat.entity.ChatGroup;
import com.dely.chat.entity.GroupMember;
import com.dely.chat.entity.GroupType;
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

    /**
     * 创建群聊，并且群主自动入群
     */
    @PostMapping("/create")
    public Result create(ChatGroup chatGroup) {
        chatGroupService.create(chatGroup);
        return Result.success();
    }

    /**
     * 搜索群聊，私有的搜索不到，已解散的也搜索不到
     */
    @GetMapping("/find/{id}")
    public Result<ChatGroup> find(@PathVariable Long id) {
        ChatGroup chatGroup = chatGroupService.getById(id);
        if (chatGroup.getIsDeleted()) return Result.success();
        if (chatGroup.getType().equals(GroupType.PRIVATE_GROUP)) return Result.success();
        return Result.success(chatGroup);
    }

    /**
     * 查用户所有已加入的群聊
     */
    @GetMapping("/list")
    public Result<List<Long>> list() {
        List<Long> list = groupMemberService.selectGroupsByUserId();
        return Result.success(list);
    }

    /**
     * 查用户所有自己是群主的群聊
     */
    @GetMapping("/list/own")
    public Result<List<Long>> listOwn() {
        List<Long> list = chatGroupService.listOwn();
        return Result.success(list);
    }

    /**
     * 修改群聊信息，比如修改群简介，最大加入人数
     */
    @PutMapping("/update")
    public Result update(@RequestBody ChatGroup chatGroup) {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        if (!userId.equals(chatGroup.getOwnerId()))
            return Result.fail("你不是群主，你改尼玛呢");
        if (chatGroup.getType().equals(GroupType.CHANNEL) && chatGroup.getMaxAmount() > 1000)
            return Result.fail("频道群聊最大人数不能超过1000");
        if (chatGroup.getMaxAmount() > 100)
            return Result.fail("普通群聊最大人数不能超过100");
        chatGroupService.updateById(chatGroup);
        return Result.success();
    }

    /**
     * 修改自己在群聊里的昵称
     */
    @PutMapping("/update/memberName")
    public Result updateMemberName(@RequestBody GroupMember groupMember) {
        groupMemberService.updateById(groupMember);
        return Result.success();
    }

    /**
     * 查看本群成员列表
     */
    @GetMapping("/member/{id}")
    public Result<List<GroupMember>> memberList(@PathVariable Long id) {
        List<GroupMember> list = groupMemberService.lambdaQuery().eq(GroupMember::getGroupId, id).list();
        return Result.success(list);
    }

    /**
     * 退出群聊
     */
    @DeleteMapping("/quit/{id}")
    public Result quit(@PathVariable Long id) {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        groupMemberService.lambdaUpdate().eq(GroupMember::getGroupId, id).eq(GroupMember::getUserId, userId).remove();
        return Result.success();
    }

    /**
     * 解散群聊
     */
    @DeleteMapping("/dismiss/{id}")
    public Result dismiss(@PathVariable Long id) {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        ChatGroup chatGroup = chatGroupService.getById(id);
        if (!userId.equals(chatGroup.getOwnerId()))
            return Result.fail("你不是群主，你解散尼玛呢");
        chatGroup.setIsDeleted(true);
        chatGroupService.updateById(chatGroup);
        return Result.success();
    }
}
