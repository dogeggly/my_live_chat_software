package com.dely.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private final String value;

}
