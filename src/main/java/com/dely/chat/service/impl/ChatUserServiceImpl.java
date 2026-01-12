package com.dely.chat.service.impl;

import com.dely.chat.entity.ChatUser;
import com.dely.chat.mapper.ChatUserMapper;
import com.dely.chat.service.IChatUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Service
public class ChatUserServiceImpl extends ServiceImpl<ChatUserMapper, ChatUser> implements IChatUserService {

}
