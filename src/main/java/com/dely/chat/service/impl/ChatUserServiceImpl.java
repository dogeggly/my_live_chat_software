package com.dely.chat.service.impl;

import com.dely.chat.dto.Result;
import com.dely.chat.entity.ChatUser;
import com.dely.chat.mapper.ChatUserMapper;
import com.dely.chat.service.IChatUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ChatUserServiceImpl extends ServiceImpl<ChatUserMapper, ChatUser> implements IChatUserService {

    @Autowired
    private ChatUserMapper chatUserMapper;

    @Override
    public Result<List<ChatUser>> findByNickname(String nickname) {
        List<ChatUser> list = chatUserMapper.findByNickname(nickname);
        return Result.success(list);
    }
}
