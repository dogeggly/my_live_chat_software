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
    public Result<List<Long>> list() {
        List<Long> list = friendService.selectFriendsByUserId();
        return Result.success(list);
    }

    /**
     * 修改好友备注
     */
    @PutMapping("/update")
    public Result update(@RequestBody Friend friend) {
        friendService.updateById(friend);
        return Result.success();
    }

    /**
     * 删除好友
     */
    @DeleteMapping("/delete/{friendId}")
    public Result delete(@PathVariable Long friendId) {
        Long userId = (Long) UserHolder.getCurrent().get("userId");
        friendService.lambdaUpdate()
                .eq(Friend::getUserId, userId)
                .eq(Friend::getFriendId, friendId)
                .remove();
        return Result.success();
    }
}
