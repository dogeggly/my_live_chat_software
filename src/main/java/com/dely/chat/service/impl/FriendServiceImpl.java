package com.dely.chat.service.impl;

import com.dely.chat.config.UserHolder;
import com.dely.chat.entity.Friend;
import com.dely.chat.mapper.FriendMapper;
import com.dely.chat.service.IFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public List<Long> selectFriendsByUserId() {
        Map<String, Object> userMap = UserHolder.getCurrent();
        Long userId = (Long) userMap.get("userId");
        return friendMapper.selectFriendsByUserId(userId);
    }
}
