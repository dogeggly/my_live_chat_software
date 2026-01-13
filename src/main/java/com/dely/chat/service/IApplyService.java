package com.dely.chat.service;

import com.dely.chat.dto.Result;
import com.dely.chat.entity.Apply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dely
 * @since 2026-01-12
 */
public interface IApplyService extends IService<Apply> {

    Result handle(Apply apply);
}
