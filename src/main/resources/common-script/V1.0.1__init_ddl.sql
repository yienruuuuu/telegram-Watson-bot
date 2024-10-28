-- 刪除表格如果已經存在
DROP TABLE IF EXISTS bot;


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
    type             VARCHAR(50) NOT NULL,
    telegram_file_id TEXT        NOT NULL,
    description      TEXT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 刪除 pic 表如果已經存在
DROP TABLE IF EXISTS pic;

-- 創建 pic 表，並且加入 bot_id 欄位來進行關聯
CREATE TABLE pic
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    bot_id           INT         NOT NULL,
    type             VARCHAR(50) NOT NULL,
    telegram_file_id TEXT        NOT NULL,
    file_bot_file_id TEXT        NOT NULL,
    description      TEXT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 刪除 manager 表如果已經存在
DROP TABLE IF EXISTS manager;

-- 創建 manager 表，並且加入 bot_id 欄位來進行關聯
CREATE TABLE manager
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    bot_id      INT  NOT NULL,
    telegram_id TEXT NOT NULL,
    description TEXT
);

-- 刪除 text 表如果已經存在
DROP TABLE IF EXISTS text;

-- 創建 text 表
CREATE TABLE text
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    type          VARCHAR(50) NOT NULL,
    language_type VARCHAR(50) NOT NULL,
    content       TEXT        NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- 創建 Card 表
DROP TABLE IF EXISTS tarot_card;
CREATE TABLE tarot_card
(
    id     INT AUTO_INCREMENT PRIMARY KEY COMMENT '每張牌的唯一標識',
    name        VARCHAR(50) NOT NULL COMMENT '塔羅牌名稱',
    category    VARCHAR(50) NOT NULL COMMENT '塔羅牌的分類（如大阿爾卡納）',
    description TEXT COMMENT '牌的基本描述'
) COMMENT = '存儲塔羅牌的基本信息';

-- 創建 CardPosition 表
DROP TABLE IF EXISTS card_position;
CREATE TABLE card_position
(
    id         INT AUTO_INCREMENT PRIMARY KEY COMMENT '每個解釋類型的唯一標識',
    card_id             INT         NOT NULL COMMENT '外鍵，參照 tarot_card 表的 id',
    position            VARCHAR(50) NOT NULL COMMENT '牌的位置（正位或逆位）',
    interpretation_type VARCHAR(50) NOT NULL COMMENT '解釋的類型（如現狀、對方想法、未來等）'
) COMMENT = '記錄塔羅牌的正位和逆位及其解釋類型';

-- 創建 CardInterpretation 表
DROP TABLE IF EXISTS card_interpretation;
CREATE TABLE card_interpretation
(
    id   INT AUTO_INCREMENT PRIMARY KEY COMMENT '每條解釋內容的唯一標識',
    position_id         INT         NOT NULL COMMENT '外鍵，參照 card_position 表的 id',
    content             TEXT        NOT NULL COMMENT '詳細的解釋內容'
) COMMENT = '存儲塔羅牌的詳細解釋內容';


