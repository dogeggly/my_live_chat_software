package com.dely.chat.controller;

import com.dely.chat.dto.Result;
import com.dely.chat.entity.ChatUser;
import com.dely.chat.service.IChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@RestController
@RequestMapping("/user")
public class ChatUserController {

    @Autowired
    private IChatUserService chatUserService;

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        chatUserService.removeById(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ChatUser chatUser) {
        chatUserService.updateById(chatUser);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<ChatUser> find(@PathVariable Long id) {
        return Result.success(chatUserService.getById(id));
    }

}
