package com.dely.chat.controller;


import com.dely.chat.config.UserHolder;
import com.dely.chat.dto.Result;
import com.dely.chat.entity.Apply;
import com.dely.chat.entity.ApplyStatus;
import com.dely.chat.service.IApplyService;
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
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private IApplyService applyService;

    /**
     * 发送加好友或加群申请
     */
    @PostMapping("/send")
    public Result create(@RequestBody Apply apply) {
        // TODO 还没考虑频道的加入
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        apply.setSenderId(userId);
        applyService.save(apply);
        return Result.success();
    }

    /**
     * 处理加好友或加群申请
     */
    @PutMapping("/handle")
    public Result handle(@RequestBody Apply apply) {
        return applyService.handle(apply);
    }

    /**
     * 查询发给我的好友申请
     */
    @GetMapping("/list/friend")
    public Result<List<Apply>> listFriend() {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        List<Apply> list = applyService.lambdaQuery()
                .eq(Apply::getTargetId, userId)
                .eq(Apply::getIsFriend, true)
                .eq(Apply::getStatus, ApplyStatus.PENDING)
                .list();
        return Result.success(list);
    }

    /**
     * 查询发给我的群聊申请
     */
    @GetMapping("/list/group")
    public Result<List<Apply>> listGroup() {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        List<Apply> list = applyService.lambdaQuery()
                .eq(Apply::getTargetId, userId)
                .eq(Apply::getIsFriend, false)
                .eq(Apply::getStatus, ApplyStatus.PENDING)
                .list();
        return Result.success(list);
    }
}
