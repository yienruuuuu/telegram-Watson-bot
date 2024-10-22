INSERT INTO tg_bot.bot (bot_id, bot_username, bot_token, description)
VALUES (1, 'testBot', '', NULL);


INSERT INTO tg_bot.language (language_code, language_name, bot_id)
VALUES ('ZH_TW', '繁體中文', 1),
       ('EN', 'English', 1);