package com.dely.chat.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.dely.chat.config.JwtProperties;
import com.dely.chat.config.UserHolder;
import com.dely.chat.dto.UserAddDTO;
import com.dely.chat.dto.Result;
import com.dely.chat.dto.UserUpdateDTO;
import com.dely.chat.entity.ChatUser;
import com.dely.chat.service.IChatUserService;
import com.dely.chat.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    /**
     * 手机号正则：目前国内手机号主要以 1 开头，第二位为 3-9，后面 9 位数字
     */
    private static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";

    /**
     * 邮箱正则：符合基本的 email 格式规范
     */
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @Autowired
    private IChatUserService chatUserService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserAddDTO userAddDTO) {
        String nickname = userAddDTO.getNickname();
        String phone = userAddDTO.getPhone();
        String password = userAddDTO.getPassword();
        String email = userAddDTO.getEmail();
        if (StrUtil.isBlank(nickname) || StrUtil.isBlank(phone) || StrUtil.isBlank(password)) {
            return Result.fail("前端传参错误，昵称，手机号和密码是必填项");
        }

        if (!Pattern.matches(REGEX_MOBILE, phone))
            return Result.fail("手机号格式错误");
        if (!StrUtil.isBlank(email) && !Pattern.matches(REGEX_EMAIL, email))
            return Result.fail("邮箱格式错误");

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

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserAddDTO userAddDTO, HttpServletRequest request) {
        String phone = userAddDTO.getPhone();
        String email = userAddDTO.getEmail();
        ChatUser user;
        if (userAddDTO.getUserId() != null) {
            user = chatUserService.getById(userAddDTO.getUserId());
        } else if (!StrUtil.isBlank(phone)) {
            if (!Pattern.matches(REGEX_MOBILE, phone)) return Result.fail("手机号格式错误");
            user = chatUserService.lambdaQuery().eq(ChatUser::getPhone, userAddDTO.getPhone()).one();
        } else if (!StrUtil.isBlank(email)) {
            if (!Pattern.matches(REGEX_EMAIL, email)) return Result.fail("邮箱格式错误");
            user = chatUserService.lambdaQuery().eq(ChatUser::getEmail, userAddDTO.getEmail()).one();
        } else {
            return Result.fail("前端传参错误，userId，手机号，邮箱得给一个吧");
        }

        if (user == null) return Result.fail("用户未注册，你登录个几把");

        if (!user.getPassword().equals(DigestUtil.md5Hex(userAddDTO.getPassword())))
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

    /**
     * 查自己的用户信息
     */
    @GetMapping("/profile")
    public Result<ChatUser> profile() {
        Long userId = (Long) UserHolder.getCurrent().get("userId");
        ChatUser user = chatUserService.getById(userId);
        return Result.success(user);
    }

    /**
     * 修改自己的用户信息
     */
    @PutMapping
    public Result update(@RequestBody UserUpdateDTO userUpdateDTO) {
        ChatUser chatUser = BeanUtil.copyProperties(userUpdateDTO, ChatUser.class);
        Long userId = (Long) UserHolder.getCurrent().get("userId");
        chatUser.setUserId(userId);
        chatUserService.updateById(chatUser);
        return Result.success();
    }

    /**
     * 根据id搜索用户
     */
    @GetMapping("/{id}")
    public Result<ChatUser> findById(@PathVariable Long id) {
        return Result.success(chatUserService.getById(id));
    }

    /**
     * 根据昵称搜索用户
     */
    @GetMapping
    public Result<List<ChatUser>> findByNickname(String nickname) {
        // 没有加入分页逻辑，可以让前端传上一个相似度，然后查10条大于这个相似度的数据
        return chatUserService.findByNickname(nickname);
    }

    /**
     * 根据昵称精确搜索用户
     */
    @GetMapping("/exact")
    public Result<List<ChatUser>> findByNicknameExact(String nickname) {
        List<ChatUser> list = chatUserService.lambdaQuery().eq(ChatUser::getNickname, nickname).list();
        return Result.success(list);
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public Result logout() {
        Long userId = (Long) UserHolder.getCurrent().get("userId");
        chatUserService.lambdaUpdate()
                .eq(ChatUser::getUserId, userId)
                .set(ChatUser::getIsOnline, false).update();
        return Result.success();
    }

}
