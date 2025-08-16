-- 生成测试数据
-- 设置会话字符集为utf8mb4
SET NAMES utf8mb4;

-- 创建存储过程生成测试数据
DELIMITER $$

DROP PROCEDURE IF EXISTS GenerateAllCategoriesTestData$$
CREATE PROCEDURE GenerateAllCategoriesTestData()
BEGIN
    DECLARE spu_id_start BIGINT DEFAULT 1000000;
    DECLARE sku_id_start BIGINT DEFAULT 2000000;
    DECLARE comment_id_start BIGINT DEFAULT 3000000;
    DECLARE replay_id_start BIGINT DEFAULT 4000000;
    DECLARE safeguard_id_start BIGINT DEFAULT 5000000;
    DECLARE spec_key_id_start BIGINT DEFAULT 6000000;
    DECLARE spec_value_id_start BIGINT DEFAULT 7000000;
    DECLARE brand_id_start BIGINT DEFAULT 8000000;
    DECLARE category_id_start BIGINT DEFAULT 9000000;

    DECLARE category_idx INT DEFAULT 0;
    DECLARE category_count INT DEFAULT 5;
    DECLARE i INT DEFAULT 0;
    DECLARE j INT DEFAULT 0;
    DECLARE k INT DEFAULT 0;
    DECLARE m INT DEFAULT 0;
    DECLARE n INT DEFAULT 0;

    -- 创建临时表存储品牌
    DROP TEMPORARY TABLE IF EXISTS temp_brands;
    CREATE TEMPORARY TABLE temp_brands (
        id INT PRIMARY KEY AUTO_INCREMENT,
        brand_name VARCHAR(50)
    );

    -- 通用品牌列表
INSERT INTO temp_brands (brand_name) VALUES
                                         ('Apple'), ('Samsung'), ('Huawei'), ('Xiaomi'), ('Sony'),
                                         ('LG'), ('Dell'), ('HP'), ('Lenovo'), ('Asus'),
                                         ('Acer'), ('Canon'), ('Nikon'), ('Panasonic'), ('Philips');

-- 创建临时表存储颜色
DROP TEMPORARY TABLE IF EXISTS temp_colors;
    CREATE TEMPORARY TABLE temp_colors (
        id INT PRIMARY KEY AUTO_INCREMENT,
        color_name VARCHAR(50)
    );

INSERT INTO temp_colors (color_name) VALUES
                                         ('曜石黑'), ('雪域白'), ('星空银'), ('晨曦金'), ('珊瑚粉'),
                                         ('松石绿'), ('石墨灰'), ('午夜蓝'), ('玫瑰金'), ('极光紫');

-- 创建临时表存储用户昵称
DROP TEMPORARY TABLE IF EXISTS temp_user_nicks;
    CREATE TEMPORARY TABLE temp_user_nicks (
        id INT PRIMARY KEY AUTO_INCREMENT,
        nick_name VARCHAR(50)
    );

INSERT INTO temp_user_nicks (nick_name) VALUES
                                            ('数码达人'), ('科技爱好者'), ('产品测评师'), ('网购专家'), ('电子产品控'),
                                            ('潮流先锋'), ('极客玩家'), ('性价比追求者'), ('摄影发烧友'), ('游戏玩家');

-- 创建分类
INSERT IGNORE INTO pms_goods_category (id, category_name, category_sort, parent_id, is_parent, valid_ind, update_time, updater_code, create_time, creator_code)
    VALUES
    (category_id_start + 1, '电视', 1, 0, b'1', b'1', NOW(), 'system', NOW(), 'system'),
    (category_id_start + 2, '电脑', 2, 0, b'1', b'1', NOW(), 'system', NOW(), 'system'),
    (category_id_start + 3, '手机', 3, 0, b'1', b'1', NOW(), 'system', NOW(), 'system'),
    (category_id_start + 4, '投影仪', 4, 0, b'1', b'1', NOW(), 'system', NOW(), 'system'),
    (category_id_start + 5, '洗衣机', 5, 0, b'1', b'1', NOW(), 'system', NOW(), 'system');

    -- 生成品牌数据
    SET @brand_id = brand_id_start;
INSERT INTO pms_goods_brand (id, first_letter, brand_name, brand_sort, show_status, product_count, logo, big_pic, brand_story, valid_ind, update_time, updater_code, create_time, creator_code)
SELECT
    @brand_id := @brand_id + 1,
        UPPER(LEFT(brand_name, 1)),  -- 首字母
        brand_name,
        @brand_id - brand_id_start + 1,  -- 排序
        1,  -- 显示
        100,  -- 产品数量
        CONCAT('logo_', LOWER(REPLACE(brand_name, ' ', '_')), '.png'),
        CONCAT('banner_', LOWER(REPLACE(brand_name, ' ', '_')), '.jpg'),
        CONCAT(brand_name, '是全球领先的电子产品品牌，致力于创新科技'),
        b'1',
        NOW(),
        'admin',
        NOW(),
        'admin'
FROM temp_brands;

-- 创建分类规格模板
DROP TEMPORARY TABLE IF EXISTS temp_category_specs;
    CREATE TEMPORARY TABLE temp_category_specs (
        category_id BIGINT,
        spec_name VARCHAR(50),
        spec_values TEXT
    );

    -- 电视规格
INSERT INTO temp_category_specs VALUES
                                    (category_id_start + 1, '尺寸', '55英寸,65英寸,75英寸,85英寸'),
                                    (category_id_start + 1, '分辨率', '4K,8K,Full HD'),
                                    (category_id_start + 1, '屏幕类型', 'OLED,QLED,LED,MicroLED'),
                                    (category_id_start + 1, '刷新率', '60Hz,120Hz,240Hz'),
                                    (category_id_start + 1, '智能系统', 'Android TV,Tizen,WebOS,Roku TV');

-- 电脑规格
INSERT INTO temp_category_specs VALUES
                                    (category_id_start + 2, '处理器', 'Intel i5,Intel i7,Intel i9,AMD Ryzen 5,AMD Ryzen 7'),
                                    (category_id_start + 2, '内存', '8GB,16GB,32GB,64GB'),
                                    (category_id_start + 2, '存储', '256GB SSD,512GB SSD,1TB SSD,2TB SSD,1TB HDD'),
                                    (category_id_start + 2, '显卡', '集成显卡,NVIDIA GTX 1650,NVIDIA RTX 3060,NVIDIA RTX 3080,AMD Radeon RX 6700'),
                                    (category_id_start + 2, '屏幕尺寸', '13.3英寸,14英寸,15.6英寸,16英寸,17.3英寸');

-- 手机规格
INSERT INTO temp_category_specs VALUES
                                    (category_id_start + 3, '内存', '6GB,8GB,12GB,16GB'),
                                    (category_id_start + 3, '存储', '64GB,128GB,256GB,512GB,1TB'),
                                    (category_id_start + 3, '屏幕尺寸', '6.1英寸,6.5英寸,6.7英寸,6.9英寸'),
                                    (category_id_start + 3, '电池容量', '4000mAh,4500mAh,5000mAh,5500mAh'),
                                    (category_id_start + 3, '摄像头', '双摄,三摄,四摄,五摄');

-- 投影仪规格
INSERT INTO temp_category_specs VALUES
                                    (category_id_start + 4, '亮度', '2000流明,3000流明,4000流明,5000流明'),
                                    (category_id_start + 4, '分辨率', '1080P,4K,8K'),
                                    (category_id_start + 4, '投射比', '1.2:1,1.5:1,2.0:1'),
                                    (category_id_start + 4, '光源类型', 'LED,激光,灯泡'),
                                    (category_id_start + 4, '系统', 'Android,无系统,内置系统');

-- 洗衣机规格
INSERT INTO temp_category_specs VALUES
                                    (category_id_start + 5, '类型', '滚筒,波轮,洗烘一体'),
                                    (category_id_start + 5, '容量', '7kg,8kg,9kg,10kg,12kg'),
                                    (category_id_start + 5, '能效', '一级,二级,三级'),
                                    (category_id_start + 5, '电机', '定频,变频,DD直驱'),
                                    (category_id_start + 5, '烘干方式', '热泵烘干,冷凝烘干,无烘干');

-- 生成保障服务
SET @safeguard_id = safeguard_id_start;

INSERT INTO pms_goods_safeguard (id, safeguard_name, price, valid_ind, update_time, updater_code, create_time, creator_code)
VALUES
    (@safeguard_id := @safeguard_id + 1, '延保服务', 29900, b'1', NOW(), 'admin', NOW(), 'admin'),
    (@safeguard_id := @safeguard_id + 1, '意外险', 39900, b'1', NOW(), 'admin', NOW(), 'admin'),
    (@safeguard_id := @safeguard_id + 1, '电池换新', 14900, b'1', NOW(), 'admin', NOW(), 'admin'),
    (@safeguard_id := @safeguard_id + 1, '数据恢复', 9900, b'1', NOW(), 'admin', NOW(), 'admin'),
    (@safeguard_id := @safeguard_id + 1, '上门安装', 19900, b'1', NOW(), 'admin', NOW(), 'admin');

-- 为每个分类生成规格键和规格值
SET @spec_key_id = spec_key_id_start;
    SET @spec_value_id = spec_value_id_start;

    -- 循环处理每个分类
    WHILE category_idx < category_count DO
        SET @category_id = category_id_start + category_idx + 1;
        SET @category_name = (SELECT category_name FROM pms_goods_category WHERE id = @category_id);

        -- 获取该分类的规格定义
        DROP TEMPORARY TABLE IF EXISTS temp_current_specs;
        CREATE TEMPORARY TABLE temp_current_specs AS
SELECT * FROM temp_category_specs WHERE category_id = @category_id;

-- 插入规格键
INSERT INTO pms_goods_spec_key (id, spec_no, spec_name, key_sort, category_id, valid_ind, update_time, updater_code, create_time, creator_code)
SELECT
    @spec_key_id := @spec_key_id + 1,
            UPPER(REPLACE(spec_name, ' ', '_')),
            spec_name,
            @spec_key_id - spec_key_id_start,
            @category_id,
            b'1',
            NOW(),
            'system',
            NOW(),
            'system'
FROM temp_current_specs;

-- 插入规格值
INSERT INTO pms_goods_spec_value (id, spec_key_id, spec_value, value_sort, valid_ind, update_time, updater_code, create_time, creator_code)
SELECT
    @spec_value_id := @spec_value_id + 1,
            sk.id,
            TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(cs.spec_values, ',', n.n), ',', -1)),
            n.n,
            b'1',
            NOW(),
            'system',
            NOW(),
            'system'
FROM pms_goods_spec_key sk
    JOIN temp_current_specs cs ON sk.spec_name = cs.spec_name
    JOIN (
    SELECT 1 AS n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4
    UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8
    ) n
WHERE n.n <= 1 + (LENGTH(cs.spec_values) - LENGTH(REPLACE(cs.spec_values, ',', '')))
  AND sk.category_id = @category_id;

-- 为该分类生成1000个SPU
SET i = 0;
        WHILE i < 1000 DO
            SET @spu_id = spu_id_start + (category_idx * 1000) + i;
            SET @brand_id = brand_id_start + FLOOR(1 + RAND() * 15); -- 15个品牌
            SET @brand_name = (SELECT brand_name FROM pms_goods_brand WHERE id = @brand_id);
            SET @model_number = FLOOR(1000 + RAND() * 9000); -- 4位型号
            SET @price_base = FLOOR(1000 + RAND() * 9000) * 100; -- 1000-10000元之间

            -- 根据分类设置SPU名称
            SET @spu_name = CASE @category_name
                WHEN '电视' THEN CONCAT(@brand_name, ' ', @model_number, '系列智能电视')
                WHEN '电脑' THEN CONCAT(@brand_name, ' ', @model_number, '系列笔记本电脑')
                WHEN '手机' THEN CONCAT(@brand_name, ' ', @model_number, '系列智能手机')
                WHEN '投影仪' THEN CONCAT(@brand_name, ' ', @model_number, '系列高清投影仪')
                WHEN '洗衣机' THEN CONCAT(@brand_name, ' ', @model_number, '系列智能洗衣机')
END;

            -- 插入SPU
INSERT INTO pms_goods_spu (id, spu_no, category_id, brand_id, goods_name, keywords,
                           sub_title, detail_title, pic, publish_status, new_status,
                           recommand_status, preview_status, service_ids, verify_status,
                           price, low_price, sale, stock, low_stock, album_pics,
                           description, detail_desc, detail_html, detail_mobile_html,
                           feight_template_id, spu_sort, note, valid_ind, update_time,
                           updater_code, create_time, creator_code)
VALUES (
           @spu_id,
           CONCAT('SPU', LPAD(@spu_id, 8, '0')),
           @category_id,
           @brand_id,
           @spu_name,
           CONCAT(@category_name, ',', @brand_name),
           CONCAT('新一代', @brand_name, @category_name, '，颠覆体验'),
           CONCAT(@spu_name, ' 详细规格'),
           CONCAT('spu_', @spu_id, '.jpg'),
           1,
           IF(RAND() > 0.7, 1, 0), -- 30%概率是新品
           IF(RAND() > 0.5, 1, 0), -- 50%概率推荐
           0,
           '1,2,3',
           1,
           @price_base + 50000, -- 基础价格+500元
           @price_base,
           FLOOR(100 + RAND() * 9900), -- 销量100-10000
           FLOOR(500 + RAND() * 9500), -- 库存500-10000
           100,
           CONCAT('album1_', @spu_id, '.jpg,album2_', @spu_id, '.jpg'),
           CONCAT('高性能', @brand_name, @category_name),
           CONCAT('卓越性能|先进技术|持久耐用'),
           '<html>产品详情HTML内容</html>',
           '<html>移动端产品详情HTML内容</html>',
           FLOOR(1 + RAND() * 10),
           i + 1,
           '测试数据',
           b'1',
           NOW(),
           'admin',
           NOW(),
           'admin'
       );

-- 关联SPU与规格键
INSERT INTO pms_goods_spu_spec (id, spu_id, spec_key_id, valid_ind, update_time, updater_code, create_time, creator_code)
SELECT
    @spu_id * 100 + spec_key_id,
    @spu_id,
    id,
    b'1',
    NOW(),
    'system',
    NOW(),
    'system'
FROM pms_goods_spec_key
WHERE category_id = @category_id;

-- 为每个SPU生成5个SKU
SET j = 0;
            WHILE j < 5 DO
                SET @sku_id = sku_id_start + (category_idx * 5000) + (i * 5) + j;

                -- 随机选择颜色
                SET @color_id = FLOOR(1 + RAND() * 10);
                SET @color_name = (SELECT color_name FROM temp_colors WHERE id = @color_id);

                -- 获取该分类所有规格键
                DROP TEMPORARY TABLE IF EXISTS temp_specs_for_sku;
                CREATE TEMPORARY TABLE temp_specs_for_sku AS
SELECT id, spec_name
FROM pms_goods_spec_key
WHERE category_id = @category_id;

-- 生成SKU名称和规格组合
SET @sku_name = @spu_name;
                SET @sku_spec_desc = '';

                -- 遍历每个规格键，随机选择一个规格值
                WHILE (SELECT COUNT(*) FROM temp_specs_for_sku) > 0 DO
                       -- 随机选择一个规格键
                       SET @spec_key_row = (SELECT id, spec_name FROM temp_specs_for_sku LIMIT 1);
SET @spec_key_id = @spec_key_row.id;
                    SET @spec_key_name = @spec_key_row.spec_name;

                    -- 获取该规格键的所有规格值
                    DROP TEMPORARY TABLE IF EXISTS temp_spec_values;
                    CREATE TEMPORARY TABLE temp_spec_values AS
SELECT id, spec_value
FROM pms_goods_spec_value
WHERE spec_key_id = @spec_key_id;

-- 随机选择一个规格值
SET @value_count = (SELECT COUNT(*) FROM temp_spec_values);
                    SET @rand_idx = FLOOR(1 + RAND() * @value_count);
                    SET @spec_value_row = (SELECT id, spec_value FROM temp_spec_values LIMIT @rand_idx-1, 1);
                    SET @spec_value_id_val = @spec_value_row.id;
                    SET @spec_value_val = @spec_value_row.spec_value;

                    -- 添加到SKU名称和描述
                    SET @sku_name = CONCAT(@sku_name, ' ', @spec_value_val);
                    SET @sku_spec_desc = CONCAT(@sku_spec_desc, @spec_key_name, ':', @spec_value_val, ';');

                    -- 关联SKU与规格值
INSERT INTO pms_goods_sku_spec_value (id, sku_id, spu_id, spec_key_id, spec_value_id,
                                      sku_spec_sort, valid_ind, update_time,
                                      updater_code, create_time, creator_code)
VALUES (
           @spec_value_id := @spec_value_id + 1,
           @sku_id,
           @spu_id,
           @spec_key_id,
           @spec_value_id_val,
           @spec_key_id - spec_key_id_start,
           b'1',
           NOW(),
           'system',
           NOW(),
           'system'
       );

-- 从临时表中移除已处理的规格键
DELETE FROM temp_specs_for_sku WHERE id = @spec_key_id;
END WHILE;

                -- 添加颜色到SKU名称
                SET @sku_name = CONCAT(@sku_name, ' ', @color_name);

                -- 计算SKU价格（基础价格+随机溢价）
                SET @sku_price = @price_base + FLOOR(500 + RAND() * 5000) * 100; -- 加价50-500元

                -- 插入SKU
INSERT INTO pms_goods_sku (id, sku_no, sku_name, price, stock, lock_stock,
                           low_stock, pic, sale, shop_id, spu_id, valid_ind,
                           update_time, updater_code, create_time, creator_code)
VALUES (
           @sku_id,
           CONCAT('SKU', LPAD(@sku_id, 8, '0')),
           @sku_name,
           @sku_price,
           FLOOR(100 + RAND() * 900), -- 库存100-1000
           FLOOR(5 + RAND() * 20), -- 锁定库存5-25
           50,
           CONCAT('sku_', @spu_id, '_', j, '.jpg'),
           FLOOR(10 + RAND() * 990), -- 销量10-1000
           0,
           @spu_id,
           b'1',
           NOW(),
           'admin',
           NOW(),
           'admin'
       );

-- 关联SKU与保障服务（每个SKU关联1-3个保障）
SET k = 0;
                SET @safeguard_count = FLOOR(1 + RAND() * 3); -- 1-3个保障
                WHILE k < @safeguard_count DO
                    SET @safeguard_idx = FLOOR(1 + RAND() * 5);
                    SET @safeguard_id_val = safeguard_id_start + @safeguard_idx;

INSERT INTO pms_goods_sku_safeguard (id, sku_id, safeguard_id, valid_ind,
                                     update_time, updater_code, create_time, creator_code)
VALUES (
           @sku_id * 10 + k,
           @sku_id,
           @safeguard_id_val,
           b'1',
           NOW(),
           'system',
           NOW(),
           'system'
       );

SET k = k + 1;
END WHILE;

                SET j = j + 1;
END WHILE;

            -- 为每个SPU生成10条评价
            SET m = 0;
            WHILE m < 10 DO
                SET @comment_id = comment_id_start + (category_idx * 10000) + (i * 10) + m;
                SET @user_idx = FLOOR(1 + RAND() * 10);
                SET @user_nick = (SELECT nick_name FROM temp_user_nicks WHERE id = @user_idx);
                SET @sku_idx = FLOOR(0 + RAND() * 5);
                SET @sku_id_val = sku_id_start + (category_idx * 5000) + (i * 5) + @sku_idx;
                SET @star_rating = FLOOR(3 + RAND() * 3); -- 3-5星

                -- 插入评价
INSERT INTO pms_goods_comment (id, spu_id, member_nick_name, product_name, star,
                               member_ip, show_status, sku_id, collect_couont,
                               read_count, content, pics, member_icon, replay_count,
                               valid_ind, creator_code, update_time, updater_code)
VALUES (
           @comment_id,
           @spu_id,
           @user_nick,
           @spu_name,
           @star_rating,
           CONCAT('192.168.', FLOOR(RAND()*255), '.', FLOOR(RAND()*255)),
           1,
           @sku_id_val,
           FLOOR(1 + RAND() * 50),
           FLOOR(10 + RAND() * 990),
           CASE @star_rating
               WHEN 5 THEN CONCAT('非常满意！', @category_name, '性能强大，外观漂亮，强烈推荐！')
               WHEN 4 THEN CONCAT('整体不错，有些小细节可以改进的', @category_name)
               WHEN 3 THEN CONCAT('中规中矩，符合预期的', @category_name)
               ELSE CONCAT('有待改进，有些失望的', @category_name)
               END,
           IF(RAND() > 0.3, CONCAT('comment_', @comment_id, '_1.jpg,comment_', @comment_id, '_2.jpg'), NULL),
           CONCAT('user_', @user_idx, '.jpg'),
           3,
           b'1',
           CONCAT('user_', @user_idx),
           NOW(),
           'system'
       );

-- 为每条评价生成3条回复
SET n = 0;
                WHILE n < 3 DO
                    SET @replay_id = replay_id_start + (category_idx * 30000) + (i * 30) + (m * 3) + n;
                    SET @replay_type = IF(n = 0, 1, 0); -- 第一条是官方回复

INSERT INTO pms_goods_comment_replay (id, comment_id, member_nick_name,
                                      member_icon, content, type, valid_ind,
                                      creator_code, update_time, updater_code)
VALUES (
           @replay_id,
           @comment_id,
           IF(@replay_type = 1, '官方客服', CONCAT('用户', FLOOR(10000 + RAND() * 90000))),
           IF(@replay_type = 1, 'service_icon.jpg', CONCAT('user_', FLOOR(1 + RAND() * 10), '.jpg')),
           CASE
               WHEN @replay_type = 1 THEN
                   CASE @star_rating
                       WHEN 5 THEN '感谢您的高度评价！我们将继续努力提供更好的产品和服务。'
                       WHEN 4 THEN '感谢您的反馈！我们会不断改进产品细节。'
                       ELSE '感谢您的宝贵意见！我们会认真考虑您的建议并改进产品。'
                       END
               WHEN n = 1 THEN '我也买了这款，使用体验确实不错！'
               ELSE '感谢分享，很有参考价值！'
               END,
           @replay_type,
           b'1',
           IF(@replay_type = 1, 'admin', CONCAT('user_', FLOOR(1 + RAND() * 10))),
           NOW(),
           'system'
       );

SET n = n + 1;
END WHILE;

                SET m = m + 1;
END WHILE;

            SET i = i + 1;

            -- 每100个SPU输出一次进度
            IF i % 100 = 0 THEN
SELECT CONCAT('Generated ', i, ' ', @category_name, ' SPUs') AS progress;
END IF;
END WHILE;

        -- 下一个分类
        SET category_idx = category_idx + 1;
SELECT CONCAT('Completed category: ', @category_name) AS category_complete;
END WHILE;

    -- 最终验证
SELECT 'Test data generation completed!' AS result;
SELECT
    c.category_name,
    COUNT(DISTINCT spu.id) AS spu_count,
    COUNT(DISTINCT sku.id) AS sku_count,
    COUNT(DISTINCT c2.id) AS comment_count,
    COUNT(DISTINCT r.id) AS replay_count
FROM pms_goods_category c
         LEFT JOIN pms_goods_spu spu ON c.id = spu.category_id
         LEFT JOIN pms_goods_sku sku ON spu.id = sku.spu_id
         LEFT JOIN pms_goods_comment c2 ON spu.id = c2.spu_id
         LEFT JOIN pms_goods_comment_replay r ON c2.id = r.comment_id
GROUP BY c.id;
END$$

DELIMITER ;

-- 执行存储过程生成测试数据
-- CALL GenerateAllCategoriesTestData();