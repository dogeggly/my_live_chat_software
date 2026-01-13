-- 定义处理状态：待处理、已通过、已拒绝
CREATE TYPE apply_status AS ENUM ('pending', 'accepted', 'rejected');

CREATE TABLE public.apply
(
    id           bigserial PRIMARY KEY,
    sender_id    bigint  NOT NULL, -- 发起申请的人
    is_friend    boolean NOT NULL, -- 类型：friend 或 group
    target_id    bigint  NOT NULL, -- 目标ID：对方用户ID 或 群主ID
    group_id     bigint,           -- 如果是群聊，则记录群ID

    reason       varchar(255),     -- 申请备注
    status       apply_status             DEFAULT 'pending',

    created_time timestamp with time zone DEFAULT now(),
    updated_time timestamp with time zone DEFAULT now()
);

-- 索引：查询发给我的申请（我的待办列表）
CREATE INDEX idx_apply_target ON apply (target_id, is_friend) WHERE status = 'pending';
-- 索引：查询我发出的申请（我发出的请求记录）
CREATE INDEX idx_apply_sender ON apply (sender_id);