CREATE TABLE public.group_member
(
    id          bigserial PRIMARY KEY,
    group_id    bigint NOT NULL,
    user_id     bigint NOT NULL,
    member_name varchar(64),                            -- 群名片（在群里的昵称）

    joined_at   timestamp with time zone DEFAULT now(),

    -- 约束
    UNIQUE (group_id, user_id)                         -- 防止重复加群
);

-- 索引：极大提升“我的群列表”查询速度
CREATE INDEX idx_member_user_id ON group_member (user_id);