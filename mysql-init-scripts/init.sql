-- 刪除表格如果已經存在
DROP TABLE IF EXISTS bot_hashtags;
DROP TABLE IF EXISTS channels;
DROP TABLE IF EXISTS bots;

-- 創建 bots 表
CREATE TABLE bots
(
    bot_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    bot_username VARCHAR(255) NOT NULL,
    bot_token    VARCHAR(512) NOT NULL,
    description  VARCHAR(512),
    status       ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 創建 bot_hashtags 表
CREATE TABLE bot_hashtags
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    bot_id    BIGINT       NOT NULL,
    hashtag   VARCHAR(255) NOT NULL,
    count     INT       DEFAULT 1,
    last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bot FOREIGN KEY (bot_id) REFERENCES bots (bot_id) ON DELETE CASCADE
);

-- 創建 channels 表
CREATE TABLE channels
(
    channel_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    bot_id           BIGINT       NOT NULL,
    channel_username VARCHAR(255) NOT NULL,
    description      VARCHAR(512),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bot_channel FOREIGN KEY (bot_id) REFERENCES bots (bot_id) ON DELETE CASCADE
);