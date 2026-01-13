CREATE TABLE public.group_member
(
    id           bigserial PRIMARY KEY,
    group_id     bigint NOT NULL,
    user_id      bigint NOT NULL,

    member_name  varchar(64),  -- 群名片（在群里的昵称）

    create_time  timestamp with time zone DEFAULT now(),
    updated_time timestamp with time zone DEFAULT now(),

    -- 约束
    UNIQUE (group_id, user_id) -- 防止重复加群
);

-- 索引：极大提升“我的群列表”查询速度
CREATE INDEX idx_member_user_id ON group_member (user_id);
-- 索引：查询某个群的成员列表
CREATE INDEX idx_member_group_id ON group_member (group_id);

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON public.group_member
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();