INSERT INTO tg_bot.bots (bot_id, bot_username, bot_token, description)
VALUES (1, 'testBot', '', NULL);


INSERT INTO tg_bot.language (language_code, language_name, bot_id)
VALUES ('zh-TW', '繁體中文', 1),
       ('en', 'English', 1);