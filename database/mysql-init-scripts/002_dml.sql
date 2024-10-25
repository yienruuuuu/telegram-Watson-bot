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

INSERT INTO tg_bot.text (type, language_type, content)
VALUES ('WELCOME_MESSAGE', 'EN', 'welcome to use daily divination !'),
       ('WELCOME_MESSAGE', 'ZH_TW', '歡迎使用 每日運勢抽獎~');

INSERT INTO tg_bot.text (type, language_type, content)
VALUES ('ASK_FOR_LANGUAGE', 'ZH_TW', '請選擇大哥你想使用的語言~');
INSERT INTO tg_bot.text (type, language_type, content)
VALUES ('ASK_FOR_LANGUAGE', 'EN', 'please choose you favorite language');

# 塔羅sql
-- 插入 Card 表數據
INSERT INTO tg_bot.tarot_card (name, category, description)
VALUES ('愚者', '大阿爾卡納', '愚者牌象徵著冒險與未知的可能性。'),
       ('戰車', '大阿爾卡納', '戰車牌象徵著掌控和勝利。'),
       ('皇后/女帝', '大阿爾卡納', '女帝牌象徵著愛與豐饒。'),
       ('惡魔', '大阿爾卡納', '惡魔牌象徵著誘惑和束縛。'),
       ('正義', '大阿爾卡納', '正義牌象徵著公正和平衡。'),
       ('皇帝', '大阿爾卡納', '皇帝牌象徵著權威與秩序。'),
       ('魔術師', '大阿爾卡納', '魔術師牌象徵著創造和行動。'),
       ('戀人', '大阿爾卡納', '戀人牌象徵著選擇和愛情。'),
       ('死神', '大阿爾卡納', '死神牌象徵著變革和結束。'),
       ('星星', '大阿爾卡納', '星星牌象徵著希望和靈感。'),
       ('教皇', '大阿爾卡納', '教皇牌象徵著指導與道德。'),
       ('隱士', '大阿爾卡納', '隱士牌象徵著內省和探索。'),
       ('太陽', '大阿爾卡納', '太陽牌象徵著快樂和成功。'),
       ('運命之輪', '大阿爾卡納', '運命之輪牌象徵著轉折和命運。'),
       ('世界', '大阿爾卡納', '世界牌象徵著完成和新開始。'),
       ('力量', '大阿爾卡納', '力量牌象徵著勇氣與決心。'),
       ('節制', '大阿爾卡納', '節制牌象徵著平衡與自律。'),
       ('塔', '大阿爾卡納', '塔牌象徵著災難和驚變。'),
       ('月亮', '大阿爾卡納', '月亮牌象徵著迷惑和恐懼。'),
       ('審判', '大阿爾卡納', '審判牌象徵著重新開始和審視。'),
       ('女教皇/女祭司', '大阿爾卡納', '女教皇牌象徵著直覺和智慧。'),
       ('倒吊人', '大阿爾卡納', '倒吊人牌象徵著犧牲和耐心。');


-- 插入 CardPosition 表數據（包含正位和逆位位置及解析類型）
INSERT INTO tg_bot.card_position (card_id, position, interpretation_type)
VALUES (1, '正位', '現狀'),
       (1, '逆位', '現狀'),
       (2, '正位', '現狀'),
       (2, '逆位', '現狀'),
       (3, '正位', '現狀'),
       (3, '逆位', '現狀'),
       (4, '正位', '現狀'),
       (4, '逆位', '現狀'),
       (5, '正位', '現狀'),
       (5, '逆位', '現狀'),
       (6, '正位', '現狀'),
       (6, '逆位', '現狀'),
       (7, '正位', '現狀'),
       (7, '逆位', '現狀'),
       (8, '正位', '現狀'),
       (8, '逆位', '現狀'),
       (9, '正位', '現狀'),
       (9, '逆位', '現狀'),
       (10, '正位', '現狀'),
       (10, '逆位', '現狀'),
       (11, '正位', '現狀'),
       (11, '逆位', '現狀'),
       (12, '正位', '現狀'),
       (12, '逆位', '現狀'),
       (13, '正位', '現狀'),
       (13, '逆位', '現狀'),
       (14, '正位', '現狀'),
       (14, '逆位', '現狀'),
       (15, '正位', '現狀'),
       (15, '逆位', '現狀'),
       (16, '正位', '現狀'),
       (16, '逆位', '現狀'),
       (17, '正位', '現狀'),
       (17, '逆位', '現狀'),
       (18, '正位', '現狀'),
       (18, '逆位', '現狀'),
       (19, '正位', '現狀'),
       (19, '逆位', '現狀'),
       (20, '正位', '現狀'),
       (20, '逆位', '現狀'),
       (21, '正位', '現狀'),
       (21, '逆位', '現狀'),
       (22, '正位', '現狀'),
       (22, '逆位', '現狀'),
       -- 未來解析
       (1, '正位', '未來'),
       (1, '逆位', '未來'),
       (2, '正位', '未來'),
       (2, '逆位', '未來'),
       (3, '正位', '未來'),
       (3, '逆位', '未來'),
       (4, '正位', '未來'),
       (4, '逆位', '未來'),
       (5, '正位', '未來'),
       (5, '逆位', '未來'),
       (6, '正位', '未來'),
       (6, '逆位', '未來'),
       (7, '正位', '未來'),
       (7, '逆位', '未來'),
       (8, '正位', '未來'),
       (8, '逆位', '未來'),
       (9, '正位', '未來'),
       (9, '逆位', '未來'),
       (10, '正位', '未來'),
       (10, '逆位', '未來'),
       (11, '正位', '未來'),
       (11, '逆位', '未來'),
       (12, '正位', '未來'),
       (12, '逆位', '未來'),
       (13, '正位', '未來'),
       (13, '逆位', '未來'),
       (14, '正位', '未來'),
       (14, '逆位', '未來'),
       (15, '正位', '未來'),
       (15, '逆位', '未來'),
       (16, '正位', '未來'),
       (16, '逆位', '未來'),
       (17, '正位', '未來'),
       (17, '逆位', '未來'),
       (18, '正位', '未來'),
       (18, '逆位', '未來'),
       (19, '正位', '未來'),
       (19, '逆位', '未來'),
       (20, '正位', '未來'),
       (20, '逆位', '未來'),
       (21, '正位', '未來'),
       (21, '逆位', '未來'),
       (22, '正位', '未來'),
       (22, '逆位', '未來'),
       (1, '正位', '對方的想法'),
       (1, '逆位', '對方的想法'),
       (2, '正位', '對方的想法'),
       (2, '逆位', '對方的想法'),
       (3, '正位', '對方的想法'),
       (3, '逆位', '對方的想法'),
       (4, '正位', '對方的想法'),
       (4, '逆位', '對方的想法'),
       (5, '正位', '對方的想法'),
       (5, '逆位', '對方的想法'),
       (6, '正位', '對方的想法'),
       (6, '逆位', '對方的想法'),
       (7, '正位', '對方的想法'),
       (7, '逆位', '對方的想法'),
       (8, '正位', '對方的想法'),
       (8, '逆位', '對方的想法'),
       (9, '正位', '對方的想法'),
       (9, '逆位', '對方的想法'),
       (10, '正位', '對方的想法'),
       (10, '逆位', '對方的想法'),
       (11, '正位', '對方的想法'),
       (11, '逆位', '對方的想法'),
       (12, '正位', '對方的想法'),
       (12, '逆位', '對方的想法'),
       (13, '正位', '對方的想法'),
       (13, '逆位', '對方的想法'),
       (14, '正位', '對方的想法'),
       (14, '逆位', '對方的想法'),
       (15, '正位', '對方的想法'),
       (15, '逆位', '對方的想法'),
       (16, '正位', '對方的想法'),
       (16, '逆位', '對方的想法'),
       (17, '正位', '對方的想法'),
       (17, '逆位', '對方的想法'),
       (18, '正位', '對方的想法'),
       (18, '逆位', '對方的想法'),
       (19, '正位', '對方的想法'),
       (19, '逆位', '對方的想法'),
       (20, '正位', '對方的想法'),
       (20, '逆位', '對方的想法'),
       (21, '正位', '對方的想法'),
       (21, '逆位', '對方的想法'),
       (22, '正位', '對方的想法'),
       (22, '逆位', '對方的想法');


INSERT INTO tg_bot.card_interpretation (position_id, interpretation_type, content)
VALUES
#  正位的現狀解釋
(1, '現狀', '現在兩人的關係處在無法確定的狀態。從今往後，考慮一起前往快樂的場所如何?'),
(3, '現狀', '兩人正處於變化之中。或許你們正在朝向全新關係前進的路上。'),
(5, '現狀', '目前兩人處於非常滿足的狀態。和他在一起，就能讓你感覺到被充實。'),
(7, '現狀', '兩個人正互相束縛著。比如說一個蠻橫、一個服從。'),
(9, '現狀', '兩人處於旗鼓相當的狀態。彼此期望的，也正是對方期望的。'),
(11, '現狀', '兩人的關係不好不壞，頃向安定的樣子。也不太期望有什麼變化，暫時不會有所變化的吧。'),
(13, '現狀', '現在兩人的關係才剛剛開始，或許說你們倆雙方都想開始一段，與以往都不一樣的交往關係。'),
(15, '現狀', '二人的關係正處在關鍵分歧點上，今後也許會發生某些變化，往好的方向。'),
(17, '現狀', '兩人將迎來巨大的變化。也許迄今為止的關係將畫上句點。'),
(19, '現狀', '無論現在兩人處於怎樣的狀態，你都始終抱持著對於未來的希望，並接受現狀。'),
(21, '現狀', '兩個人的關係是認真而克制的。也因如此，趣味性上可能會有所欠缺。不過近期內這種狀態將會持續。'),
(23, '現狀', '兩個人到了這樣的時期，都著重於重新探尋自己內心的希冀，而忽視於思考與對方的關聯。'),
(25, '現狀', '現在的兩人，即使是一瞬間的心意相通，也能嘗得喜悅。'),

(27, '現狀', '兩人的關係在轉換時期。發生了什麼大變化的時候。這個轉折點靜靜地注視吧。'),
(29, '現狀', '無論是好是壞，兩人的關係都要出現一個結果。之後又是新的開始。'),
(31, '現狀', '現在的兩人關係即使困難重重，你從中尋覓到的安靜，並慢慢的接受了。'),
(33, '現狀', '現在兩個人關係上沒有大變化。互相間是在下一步發展中一點點慢慢觀察吧。'),
(35, '現狀', '由突發性事件，雙方的關係現在，很可能會導致重大的變化。它很可能是不能迴避的。'),
(37, '現狀', '現在兩人的狀態傾向非常不安定。這以後會變成怎麼樣，現在無法預測。'),
(39, '現狀', '兩人關係現在為止的問題逐漸出現了。一直無法解決的問題，答案將呼之慾出了。'),
(41, '現狀', '現在兩人之間貌似還存在隔閡的樣子。但，現在兩人有必要保持距離。'),
(43, '現狀', '停滯的時候。到現在為止，你發現了兩人的關係停滯在那了。'),
-- 逆位的現狀解釋
(2, '現狀', '兩人現在處於非常不穩定的狀態。某一方、或者兩個人都在迴避要承擔責任的關係。'),
(4, '現狀', '兩人目前的交往穩定不下來。似乎某一方並不想要有形式的交往。'),
(6, '現狀', '兩人的關係構築於各自的幻想。沒能考慮到對方的狀況和心情。'),
(8, '現狀', '你的心意現在傳達不到對方。現在他的興趣在別的地方。'),
(10, '現狀', '現在兩人關係表現出有點在幼稚(任性)。或許都相互考慮著自我滿足的事情。'),
(12, '現狀', '兩個人走進了死胡同。也許這是因為你內心的固執造成的。'),
(14, '現狀', '兩人的關係瀕臨崩壞，你卻不能接受，仍在掙扎。'),
(16, '現狀', '現在兩人目前不知道該如何去尋找目標呢。不知為何現在的狀態是滿足甘願的也說不定呢。'),
(18, '現狀', '現在的兩人面臨著迷惑。還不能決斷是否應該將自己的心願付諸實行。'),
(20, '現狀', '兩人各自內心有自己的想法，不能如願的關係或許讓人感到疲憊。'),
(22, '現狀', '兩個人的關係處在不平衡的狀態。有可能是因為你沒跟得上對方的變化。'),
(24, '現狀', '如果說兩人的關係面臨著問題，今後也會持續。現在還不是出結論的時候。'),
(26, '現狀', '你似乎不想直面兩人的現狀。又或者說兩人都想從現在的問題上轉移視線。'),
(28, '現狀', '兩人不忍正視現實。自己兩人有無法直視的問題吧。'),
(30, '現狀', '目前兩人的關係明明不是改變的時候，你卻意圖改變狀況。'),
(32, '現狀', '兩人的關係有些不對頭。那就是，相互期望的東西卻相左了的原因吧。'),
(34, '現狀', '現在的關係彼此對方根據自己的行動被束縛著，但彼此也注意到了這個狀態了。'),
(36, '現狀', '目前為止的關係已經一去不復返不如從前了，似乎你不想承認這想法。'),
(38, '現狀', '兩個人的關係好像有點兒陷入僵局。「想這樣」這種自己的想法可能傳達不到給對方吧。'),
(40, '現狀', '常識過於拘束、兩人的關係無法前進的樣子。試試用更自由地角度相互考慮彼此的關係吧。'),
(42, '現狀', '兩人實現不可能的事情一直像做夢一樣。現實無法接受吧。'),
(44, '現狀', '兩人的關係意圖的結果將漸漸而來，下個階段迎來前進。再次新的開始我們必須開始。'),
-- 正位的未來解釋
(45, '未來', '兩人的關係，將寄希望於遙遠的未來，由於你獻身性的愛而得以培育。'),
(47, '未來', '兩個人的關係將變得豐厚充盈。在一起的時候，兩人都能感到放鬆和愜意。'),
(49, '未來', '預感到兩人的關係即將沿著你期望的樣子來發展。'),
(51, '未來', '兩人從此將迎來新的開始。你已經準備好了如何傳遞自己的心意。'),
(53, '未來', '兩人的關係將迎來一個完結的節點。這之後，將迎來新的開始。'),
(55, '未來', '兩人的關係也許到達選擇的時機。按照你的心的旨意向前進吧。'),
(57, '未來', '兩人目前的問題迎來解決的時刻。你的心將從煩惱中解脫。'),
(59, '未來', '兩個人的關係將變得充滿生機。享受當下的快樂，向對方袒露心意。'),
(61, '未來', '兩人今後將能稍微冷靜一下。這個過程中將有新的發現。'),
(63, '未來', '兩人的關係將會變得相互束縛，儘管並非本意。'),
(65, '未來', '兩人終於決定開始前進，採取行動。這張牌象徵堅強。'),
(67, '未來', '兩人的關係將打上一個休止符，不管好壞都無法維持之前的交往。'),
(69, '未來', '兩人將迎來停滯。被固定觀念束縛，將成為改變現今關係的一步。'),
(71, '未來', '兩個人的關係將緩慢的發生變化，捨棄我執而加深理解。'),
(73, '未來', '兩人的關係將變得穩健，會有更多機會討論彼此和未來。'),
(75, '未來', '兩人將迎來轉換期，會有大的變化發生。'),
(77, '未來', '兩人的關係將帶有緊張。需要認真審視自己的心意。'),
(79, '未來', '兩人的關係將因發生的某件事而迎來巨變。'),
(81, '未來', '兩人的關係將迎來不穩定狀態，暫時看不清未來。'),
(83, '未來', '兩人的關係將維持穩定的狀態，卻缺乏樂趣。'),
(85, '未來', '兩人將出現隔閡，重新確認彼此的主張和差異十分重要。'),
(87, '未來', '兩人的未來前途未卜，但也因此暗藏不可預知的可能性。'),
-- 逆位的未來解釋
(46, '未來', '兩人的關係將出現迷惑，無法做出決斷。'),
(48, '未來', '兩人的關係將出現依賴端倪，彼此只考慮自己。'),
(50, '未來', '兩人的關係建立在臆測上，應該拿出面對現實的堅強。'),
(52, '未來', '兩個人的關係將面臨困難，需正視自己心底的慾望。'),
(54, '未來', '兩人的關係暫無定論，現在不是出結論的時候。'),
(56, '未來', '兩人完全背道而馳，固執不改將加深對立。'),
(58, '未來', '兩人的關係即將崩壞，應正視不穩定的交往。'),
(60, '未來', '兩人不打算讓關係前進，將有一段時間無法見面。'),
(62, '未來', '兩人的關係將變得膚淺，應好好溝通一次。'),
(64, '未來', '兩人都沉浸於各自想法，需認真思考真正想要什麼。'),
(66, '未來', '二人的關係早晚將迎來改變的時刻。'),
(68, '未來', '兩人的關係不會如你所想發生變化，時機未到。'),
(70, '未來', '兩人將會覺得對方難以理解，需慢慢溝通。'),
(72, '未來', '兩人的關係將走進死胡同，無法前進。'),
(74, '未來', '兩人的關係無法前進，將忍耐目前的狀況。'),
(76, '未來', '兩人的關係不能隨你意願發展，需體察對方心意。'),
(78, '未來', '兩人關係將陷入幻想中，應該多給予對方愛。'),
(80, '未來', '兩人交往穩定不下來，無法負起責任。'),
(82, '未來', '兩人的關係再也無法如從前，應進入下一階段。'),
(84, '未來', '兩人關係未達預期，重建新規的目標。'),
(86, '未來', '兩人關係可能不會進展，需堅強的意志。'),
(88, '未來', '兩人關係中可能試圖避開真實，心有芥蒂。'),
(89, '對方的想法', '他失去了自信。也許他受到了很大刺激，並打算一個人來承受。'),
(91, '對方的想法', '現在的他情緒非常穩定。他完全的享受和你現在的關係。'),
(93, '對方的想法', '他現在處於不能掙脫的狀態。他似乎意識到了自己不能表達出自己的心意。'),
(95, '對方的想法', '現在的他正以十分溫柔的心情接納對方。即便願望不能實現，他也將繼續企盼下去。'),
(97, '對方的想法', '他似乎感到了與你之間的隔閡。也許他想先保持一下距離。'),
(99, '對方的想法', '他現在面臨著選擇。他似乎想好了怎麼處理和你之間的關係。'),
(101, '對方的想法', '他正為現實的東西而忙得不可開交，無法沉浸於戀愛的氣氛。'),
(103, '對方的想法', '他的心意還定不下來。他好像還不清楚自己的想法將會變成怎樣。'),
(105, '對方的想法', '他正認真面對著束縛到自己的苦惱和問題。不過他還是不能前進一步。'),
(107, '對方的想法', '他的心現在明確的向著他喜歡的人。如果兩人是情侶的話，那他對你的心意也很有信心。'),
(109, '對方的想法', '他正在慢慢思索著兩人之間的關係。他並不著急得出答案，而是想從容的認真考慮。'),
(111, '對方的想法', '他感到事態正朝著自己的預想發展，而且，那也正是他想要的。現在他正眺向遠方。'),
(113, '對方的想法', '現在的他非常的不安。讓他不安的，是對方心意以及未來的不可揣摩。'),
(115, '對方的想法', '現在的他對和你在一起感到充實。他應該期待著這一狀態持續。'),
(117, '對方的想法', '現在的他擁有朝向目標前進的強烈意志，非常值得依賴。'),
(119, '對方的想法', '現在的他，相比起兩人的關係更關注自己。他想探尋自己今後到底想何去何從。'),
(121, '對方的想法', '現在的他只是被動的接受兩人的現狀。心情上處於痛苦的時候，不過他可以挺過去。'),
(123, '對方的想法', '現在的他期望的是謹慎有序的交往。對於自己的心要被誰奪去，或許他有一些恐懼。'),
(125, '對方的想法', '他覺得他和你的心意對等平衡，現在的他應該沒有任何不滿。'),
(127, '對方的想法', '他正冷靜的守望著兩人的關係。似乎他感覺到快是要出結果的時候。'),
(129, '對方的想法', '他的心意正在發生重大的變化。似乎他意識到已不能維持以往的想法。'),
(131, '對方的想法', '現在，他享受著和你相處的每一個當下。他沒有隱藏自己的心意，而是向你坦呈出來。'),

-- 逆位的對方的想法解釋
(90, '對方的想法', '他似乎覺得你沒有理解到他的想法，並為此感到不滿。'),
(92, '對方的想法', '他似乎一直對不可能實現的事情抱有夢想。或者相反，也許他是不想做徒勞無謂的事。'),
(94, '對方的想法', '他的想法已經較之前發生了改變，自己卻沒有意識到這一點。'),
(96, '對方的想法', '他把自己封到殼裡了。對於不能如願發展的現實，可能他感到了厭倦。'),
(98, '對方的想法', '他的情緒越來越任性起來。這張牌顯示了他想被女性寵愛的依賴心。'),
(100, '對方的想法', '現在的他，似乎覺得能享受當下每一刻的快樂就好。他不想被任何人束縛。'),
(102, '對方的想法', '他不能完全接受兩人的現狀。對於進展的不如人願，他不能掩飾焦灼。'),
(104, '對方的想法', '他有一點點焦灼。想儘量扭轉不能改變的現狀。'),
(106, '對方的想法', '他正處在被動的狀態。他不打算主動行動，只是靜靜的等待答案。'),
(108, '對方的想法', '他正在搖擺中。或許他急著想快些得出本不應著急的結論。'),
(110, '對方的想法', '現在的他並不打算搞清楚自己的情緒。他的心意就是含混曖昧的。'),
(112, '對方的想法', '他對於事態未能按照自己的意願進行，懷有一些遺憾。'),
(114, '對方的想法', '他的心情很陷入僵局。被自己心中想這樣做的想法拘泥所束縛。'),
(116, '對方的想法', '現在的他自說自話，不打算好好捕捉對方的情緒。'),
(118, '對方的想法', '他沒有辦法接受自己造成的失敗以及自信的失去。'),
(120, '對方的想法', '現在的他覺得表達自己的心意還為時過早。這也是源於他的缺乏自信。'),
(122, '對方的想法', '現在的他好像不想背負責任。可能他不想面對現實。'),
(124, '對方的想法', '現在的他不可倚賴。他不能自行思考發起行動。'),
(126, '對方的想法', '他隱藏自己的心意。或許他害怕說出真相。'),
(128, '對方的想法', '現在的他有一點任性，也許他打算把自己的意願強加給你。'),
(130, '對方的想法', '現在的他走進了死胡同，他被自己內心固執的想法束縛著。'),
(132, '對方的想法', '他對自己的構思無法如願似乎有些留戀。也許會不甘放棄。');
