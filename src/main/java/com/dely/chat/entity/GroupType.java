package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupType implements IEnum<String> {

    // Java 里避开关键字，分别定义为公开、私有、频道
    PUBLIC_GROUP("public"),
    PRIVATE_GROUP("private"),
    CHANNEL("channel");

    // 这个 value 必须对应数据库 ENUM 里的字符串
    private final String value;

}