package com.dely.chat.entity;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType implements IEnum<String> {
    unknown("unknown"),
    male("male"),
    female("female"),
    helicopter("helicopter"),
    plastic_bag("plastic_bag");

    private final String value;

}
