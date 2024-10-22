-- 刪除表格如果已經存在
DROP TABLE IF EXISTS bots;


-- 創建 bots 表
CREATE TABLE bot
(
    bot_id       INT AUTO_INCREMENT PRIMARY KEY,
    bot_username VARCHAR(255) NOT NULL,
    bot_token    VARCHAR(512) NOT NULL,
    description  VARCHAR(512),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 刪除 language 表如果已經存在
DROP TABLE IF EXISTS language;

-- 創建 language 表，並且加入 bot_id 欄位來進行關聯
CREATE TABLE `language`
(
    id            INT PRIMARY KEY AUTO_INCREMENT, -- 語系ID
    bot_id        BIGINT,                         -- 關聯到 bots 表的 bot_id
    language_code VARCHAR(10) NOT NULL,           -- 語系代碼（例如：'zh-TW', 'en'）
    language_name VARCHAR(50) NOT NULL            -- 語系名稱（例如：'繁體中文', 'English')
);

-- 刪除 language 表如果已經存在
DROP TABLE IF EXISTS gif;

-- 創建 language 表，並且加入 bot_id 欄位來進行關聯
CREATE TABLE gif
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    bot_id           INT         NOT NULL,
    type             VARCHAR(20) NOT NULL,
    telegram_file_id TEXT        NOT NULL,
    description      TEXT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)


