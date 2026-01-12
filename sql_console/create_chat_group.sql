-- 定义群类型枚举（公有群、私有群等）
CREATE TYPE group_type AS ENUM ('public', 'private', 'channel');
CREATE SEQUENCE chat_group_id_seq START WITH 10000;

CREATE TABLE public.chat_group
(
    group_id    bigint PRIMARY KEY default nextval('chat_group_id_seq'),
    group_name  varchar(64) NOT NULL,
    owner_id    bigint      NOT NULL,             -- 群主ID
    description text,                             -- 群简介
    type        group_type           DEFAULT 'public',

    -- 群配置
    members     int                  DEFAULT 1,
    max_members int                  DEFAULT 100, -- 最大人数限制

    -- 审计信息
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE INDEX idx_group_owner ON chat_group (owner_id);

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON public.chat_group
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();