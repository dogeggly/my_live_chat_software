package com.dely.chat.service;

import com.dely.chat.entity.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
public interface IChatGroupService extends IService<ChatGroup> {

    void create(ChatGroup chatGroup);
}
