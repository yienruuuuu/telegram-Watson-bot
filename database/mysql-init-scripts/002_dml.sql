INSERT INTO tg_bot.bot (bot_id, bot_username, bot_token, description)
VALUES (1, 'DivinationBot', '', '每日運勢BOT');

INSERT INTO tg_bot.bot (bot_id, bot_username, bot_token, description)
VALUES (2, 'ChangeFileBot', '', '上傳資料BOT');

INSERT INTO tg_bot.language (language_code, language_name, bot_id)
VALUES ('ZH_TW', '繁體中文', 1),
       ('EN', 'English', 1);

INSERT INTO tg_bot.gif (id, bot_id, type, telegram_file_id, description)
VALUES (1, 1, 'CARD_DRAWING_ANIMATION', 'CgACAgUAAxkBAAOfZxhUKD8ZfaYC4QVdLk99javgCnAAAgYUAAJrE8BUHNo1TB9p5x02BA',
        '爆炸');

INSERT INTO tg_bot.gif (bot_id, type, telegram_file_id, description)
VALUES (2, 'QUESTION_ANIMATION', 'CgACAgUAAxkBAAMJZxkOL336vuAEHFtTOiLa6RQO0fEAAigQAAI2fQFUblKUr19mN7o2BA', '烏薩奇蛤');

INSERT INTO tg_bot.manager (bot_id, telegram_id, description)
VALUES (1, '1513052214', 'boss'),
       (2, '1513052214', 'boss');
