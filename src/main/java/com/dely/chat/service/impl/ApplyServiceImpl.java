package com.dely.chat.service.impl;

import com.dely.chat.dto.Result;
import com.dely.chat.entity.*;
import com.dely.chat.mapper.ApplyMapper;
import com.dely.chat.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {

    @Autowired
    private IFriendService friendService;
    @Autowired
    private IGroupMemberService groupMemberService;
    @Autowired
    private IChatUserService chatUserService;

    @Override
    @Transactional
    public Result handle(Apply apply) {
        ApplyStatus status = apply.getStatus();
        if (status.equals(ApplyStatus.PENDING)) {
            return Result.fail("前端传参错误，给个同意或者拒绝啊");
        }
        if (status.equals(ApplyStatus.REJECTED)) {
            this.updateById(apply);
            return Result.success();
        }
        ChatUser user = chatUserService.getById(apply.getSenderId());
        if (apply.getIsFriend()) {
            Friend f1 = Friend.builder()
                    .userId(apply.getSenderId())
                    .friendId(apply.getTargetId())
                    .remark(user.getNickname()).build();
            Friend f2 = Friend.builder()
                    .userId(apply.getTargetId())
                    .friendId(apply.getSenderId())
                    .remark(user.getNickname()).build();
            friendService.saveBatch(List.of(f1, f2));
        } else {
            GroupMember groupMember = GroupMember.builder()
                    .groupId(apply.getGroupId())
                    .userId(apply.getSenderId())
                    .memberName(user.getNickname()).build();
            groupMemberService.save(groupMember);
        }
        this.updateById(apply);
        return Result.success();
    }
}
