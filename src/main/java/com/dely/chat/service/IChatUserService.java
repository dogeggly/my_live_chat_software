package com.dely.chat.service;

import com.dely.chat.dto.Result;
import com.dely.chat.entity.ChatUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
public interface IChatUserService extends IService<ChatUser> {

    Result<List<ChatUser>> findByNickname(String nickname);
}
