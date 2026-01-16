package com.dely.chat.controller;

import com.dely.chat.config.UserHolder;
import com.dely.chat.dto.Result;
import com.dely.chat.entity.Friend;
import com.dely.chat.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private IFriendService friendService;

    /**
     * 查用户所有已添加的好友
     */
    @GetMapping("/list")
    public Result<List<Friend>> list() {
        Long userId = (Long) UserHolder.getCurrent().get("userId");
        List<Friend> list = friendService.lambdaQuery().eq(Friend::getUserId, userId).list();
        return Result.success(list);
    }

    /**
     * 修改好友备注，加上userId的判断逻辑以防绕过前端修改数据
     */
    @PutMapping
    public Result update(@RequestBody Friend friend) {
        Long userId = (Long) UserHolder.getCurrent().get("userId");
        friendService.lambdaUpdate()
                .eq(Friend::getId, friend.getId())
                .eq(Friend::getUserId, userId)
                .set(Friend::getRemark, friend.getRemark())
                .update();
        return Result.success();
    }

    /**
     * 删除好友
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        Long userId = (Long) UserHolder.getCurrent().get("userId");
        friendService.lambdaUpdate()
                .eq(Friend::getId, id)
                .eq(Friend::getUserId, userId)
                .remove();
        return Result.success();
    }
}
