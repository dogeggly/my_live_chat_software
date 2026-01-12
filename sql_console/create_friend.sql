CREATE TABLE public.friend
(
    id           bigserial PRIMARY KEY,
    user_id      bigint NOT NULL, -- 所属用户 ID
    friend_id    bigint NOT NULL, -- 好友用户 ID

    -- 业务属性
    remark       varchar(64),     -- 我给好友起的备注

    -- 审计信息
    created_time timestamp with time zone DEFAULT now(),
    updated_time timestamp with time zone DEFAULT now(),

    -- 约束：同一个用户不能多次添加同一个好友
    UNIQUE (user_id, friend_id)
);

-- 索引：查询“我的好友列表”的核心索引
CREATE INDEX idx_friendship_user_id ON friend (user_id);

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON public.friend
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();