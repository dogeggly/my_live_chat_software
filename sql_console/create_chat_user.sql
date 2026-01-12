CREATE TYPE gender_type AS ENUM ('unknown', 'male', 'female', 'helicopter', 'plastic_bag');
CREATE SEQUENCE chat_user_id_seq START WITH 10000;

CREATE TABLE IF NOT EXISTS public.chat_user
(
    -- 1. 主键
    user_id       BIGINT PRIMARY KEY       DEFAULT nextval('chat_user_id_seq'),

    -- 2. 账号信息
    nickname      VARCHAR(100)       not null, -- 显示昵称
    email         VARCHAR(255) UNIQUE,         -- 邮箱（可选，用于找回密码）
    phone         VARCHAR(20) UNIQUE not null, -- 手机号
    password      VARCHAR(255)       NOT NULL, -- 加密后的密码

    -- 3. 个人资料
    bio           VARCHAR(255),                -- 个人简介
    gender        gender_type              DEFAULT 'unknown',

    -- 4. 状态管理
    is_online     BOOLEAN,                     -- 在线状态

    -- 5. 时间审计
    last_login_at TIMESTAMP,
    last_login_ip inet,
    created_time  TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_time  TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- 6. 创建索引优化查询
CREATE INDEX idx_chat_user_username ON public.chat_user (nickname);

-- 7. 为 chat_user 表绑定触发器
-- 注意：created_time 靠 DEFAULT，updated_time 靠这个 Trigger
CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON public.chat_user
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();