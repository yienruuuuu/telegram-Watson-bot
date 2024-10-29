ALTER TABLE tg_bot.gif
    ADD COLUMN file_bot_file_id text NOT NULL AFTER telegram_file_id;