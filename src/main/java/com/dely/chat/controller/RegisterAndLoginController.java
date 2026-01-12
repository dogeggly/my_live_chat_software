package com.dely.chat.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.dely.chat.config.JwtProperties;
import com.dely.chat.dto.ChatUserDTO;
import com.dely.chat.dto.Result;
import com.dely.chat.entity.ChatUser;
import com.dely.chat.service.IChatUserService;
import com.dely.chat.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterAndLoginController {

    @Autowired
    private IChatUserService chatUserService;

    @PostMapping("/register")
    public Result register(@RequestBody ChatUserDTO chatUserDTO) {
        String nickname = chatUserDTO.getNickname();
        String phone = chatUserDTO.getPhone();
        String password = chatUserDTO.getPassword();
        String email = chatUserDTO.getEmail();
        if (StrUtil.isBlank(nickname) || StrUtil.isBlank(phone) || StrUtil.isBlank(password)) {
            return Result.fail("前端传参错误，昵称，手机号和密码是必填项");
        }

        boolean hasUser = chatUserService.lambdaQuery()
                .eq(ChatUser::getPhone, phone)
                .or()
                .eq(ChatUser::getEmail, email)
                .exists();
        if (hasUser) return Result.fail("用户已存在，换个手机号或者邮箱");

        password = DigestUtil.md5Hex(password);
        chatUserService.save(ChatUser.builder()
                .nickname(nickname).phone(phone).password(password).email(email)
                .build());
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody ChatUserDTO chatUserDTO, HttpServletRequest request) {
        ChatUser user;
        if (chatUserDTO.getUserId() != null) {
            user = chatUserService.getById(chatUserDTO.getUserId());
        } else if (!StrUtil.isBlank(chatUserDTO.getPhone())) {
            user = chatUserService.lambdaQuery().eq(ChatUser::getPhone, chatUserDTO.getPhone()).one();
        } else if (!StrUtil.isBlank(chatUserDTO.getEmail())) {
            user = chatUserService.lambdaQuery().eq(ChatUser::getEmail, chatUserDTO.getEmail()).one();
        } else {
            return Result.fail("前端传参错误，userId，手机号，邮箱得给一个吧");
        }

        if (user == null) return Result.fail("用户未注册，你登录个几把");


        if (!user.getPassword().equals(DigestUtil.md5Hex(chatUserDTO.getPassword())))
            return Result.fail("密码错误，你登录个几把");

        user.setIsOnline(true);
        user.setLastLoginAt(LocalDateTime.now());
        user.setLastLoginIp(IpUtils.getIpAddr(request));
        chatUserService.updateById(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("nickname", user.getNickname());
        String jwt = JwtProperties.createJWT(claims);
        // TODO 返回给前端的数据还没定下来，总之要返回 token
        return Result.success(jwt);
    }

}
