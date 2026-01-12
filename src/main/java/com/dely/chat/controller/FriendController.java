package com.dely.chat.controller;

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

    @GetMapping("/list")
    public Result<List<Long>> list() {
        List<Long> list = friendService.selectFriendsByUserId();
        return Result.success(list);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Friend friend) {
        friendService.updateById(friend);
        return Result.success();
    }

}
