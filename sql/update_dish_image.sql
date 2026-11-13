-- =====================================================================
-- 菜品图片批量更新（按菜品名一一对应）
-- 生成时间: 2026-11-13
-- 图片来源: docs/dish_img/ 24 张，已通过 POST /admin/common/upload 上传至 OSS
-- 目标表  : sky_take_out.dish (name 唯一索引 idx_dish_name)
-- 生效范围: 线上现有 23 条菜品；清单中 24 个菜名
-- 注意    : 线上 dish 表不存在 王老吉，其对应图片不会写入（其余不受影响）
-- 执行方式: MySQL 8.x 客户端整段执行，末尾 SELECT 用于核对结果
-- =====================================================================

UPDATE dish AS d
JOIN (
    SELECT '东坡肘子' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ff51469b-854d-4f31-846b-09333f8389b0.png' AS image_url
    UNION ALL SELECT '剁椒鱼头' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/f9c1d8d1-c922-46e6-96c0-19e4241e9288.jpg' AS image_url
    UNION ALL SELECT '北冰洋' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ba7d262a-3680-4fb0-8771-340f518ec01b.png' AS image_url
    UNION ALL SELECT '平菇豆腐汤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/efafed55-9881-4d85-9ac3-4d832fd1e580.jpg' AS image_url
    UNION ALL SELECT '梅菜扣肉' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/eba37b35-b3eb-403c-b61c-dfcd91473cc6.jpg' AS image_url
    UNION ALL SELECT '江团鱼2斤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/f0c1d8cd-44aa-49d5-a692-911ba7fe4989.jpg' AS image_url
    UNION ALL SELECT '清炒小油菜' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/23eb032f-62fd-45c3-9234-8da7d90b25ec.jpg' AS image_url
    UNION ALL SELECT '清炒西兰花' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ced2b18e-6884-4e53-b2e2-0d1096260a85.jpg' AS image_url
    UNION ALL SELECT '清蒸鲈鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ee0ed702-f1a7-4c90-9a53-b4295315cc76.jpg' AS image_url
    UNION ALL SELECT '炝炒圆白菜' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/a9b55ec6-a840-4d26-aa57-9da607643eee.png' AS image_url
    UNION ALL SELECT '王老吉' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/95505aaa-de26-4136-9eb3-7aa32c6b95db.jpg' AS image_url
    UNION ALL SELECT '米饭' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ce04f2a4-1161-44f9-95c0-bb1628a597ff.jpg' AS image_url
    UNION ALL SELECT '经典酸菜鮰鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/e7b1afa5-a150-45c2-b87f-a079276a2fe1.jpg' AS image_url
    UNION ALL SELECT '老坛酸菜鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/451e87b1-6a52-49d9-ba6a-0f6cba4131ed.jpg' AS image_url
    UNION ALL SELECT '草鱼2斤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/43a60647-54b4-4c25-af37-a6fc6cac9c5e.jpg' AS image_url
    UNION ALL SELECT '蒜蓉娃娃菜' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/e311c13c-5427-4684-88e8-954429cb18c5.png' AS image_url
    UNION ALL SELECT '蜀味水煮草鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/3f1480a7-c158-4f95-a1c6-8cc22adc8da9.jpg' AS image_url
    UNION ALL SELECT '金汤酸菜牛蛙' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/8ad410c5-c825-4441-b925-14a6826c50b8.jpg' AS image_url
    UNION ALL SELECT '雪花啤酒' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/e3684be7-91e9-48a9-8ab9-4fc39226e716.jpg' AS image_url
    UNION ALL SELECT '馋嘴牛蛙' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/2c5e3da9-05c8-4942-8da8-dbfe9d681f1d.jpg' AS image_url
    UNION ALL SELECT '馒头' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/36398eec-5f99-463c-a2d7-53fcdcb54f2c.jpg' AS image_url
    UNION ALL SELECT '香锅牛蛙' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/620f074d-139e-454a-b5bd-638baec3962f.jpg' AS image_url
    UNION ALL SELECT '鮰鱼2斤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/d5be393b-f5e3-485f-b0d1-3c0f70d9ddbe.png' AS image_url
    UNION ALL SELECT '鸡蛋汤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/a8e8c07d-9a1f-4a14-8814-0f9f144b27c9.jpg' AS image_url
) AS t ON d.name = t.dish_name
SET d.image = t.image_url;

-- 核对：逐条比对更新后的图片地址
SELECT d.id, d.name, d.image
FROM dish AS d
JOIN (
    SELECT '东坡肘子' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ff51469b-854d-4f31-846b-09333f8389b0.png' AS image_url
    UNION ALL SELECT '剁椒鱼头' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/f9c1d8d1-c922-46e6-96c0-19e4241e9288.jpg' AS image_url
    UNION ALL SELECT '北冰洋' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ba7d262a-3680-4fb0-8771-340f518ec01b.png' AS image_url
    UNION ALL SELECT '平菇豆腐汤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/efafed55-9881-4d85-9ac3-4d832fd1e580.jpg' AS image_url
    UNION ALL SELECT '梅菜扣肉' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/eba37b35-b3eb-403c-b61c-dfcd91473cc6.jpg' AS image_url
    UNION ALL SELECT '江团鱼2斤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/f0c1d8cd-44aa-49d5-a692-911ba7fe4989.jpg' AS image_url
    UNION ALL SELECT '清炒小油菜' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/23eb032f-62fd-45c3-9234-8da7d90b25ec.jpg' AS image_url
    UNION ALL SELECT '清炒西兰花' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ced2b18e-6884-4e53-b2e2-0d1096260a85.jpg' AS image_url
    UNION ALL SELECT '清蒸鲈鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ee0ed702-f1a7-4c90-9a53-b4295315cc76.jpg' AS image_url
    UNION ALL SELECT '炝炒圆白菜' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/a9b55ec6-a840-4d26-aa57-9da607643eee.png' AS image_url
    UNION ALL SELECT '王老吉' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/95505aaa-de26-4136-9eb3-7aa32c6b95db.jpg' AS image_url
    UNION ALL SELECT '米饭' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/ce04f2a4-1161-44f9-95c0-bb1628a597ff.jpg' AS image_url
    UNION ALL SELECT '经典酸菜鮰鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/e7b1afa5-a150-45c2-b87f-a079276a2fe1.jpg' AS image_url
    UNION ALL SELECT '老坛酸菜鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/451e87b1-6a52-49d9-ba6a-0f6cba4131ed.jpg' AS image_url
    UNION ALL SELECT '草鱼2斤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/43a60647-54b4-4c25-af37-a6fc6cac9c5e.jpg' AS image_url
    UNION ALL SELECT '蒜蓉娃娃菜' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/e311c13c-5427-4684-88e8-954429cb18c5.png' AS image_url
    UNION ALL SELECT '蜀味水煮草鱼' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/3f1480a7-c158-4f95-a1c6-8cc22adc8da9.jpg' AS image_url
    UNION ALL SELECT '金汤酸菜牛蛙' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/8ad410c5-c825-4441-b925-14a6826c50b8.jpg' AS image_url
    UNION ALL SELECT '雪花啤酒' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/e3684be7-91e9-48a9-8ab9-4fc39226e716.jpg' AS image_url
    UNION ALL SELECT '馋嘴牛蛙' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/2c5e3da9-05c8-4942-8da8-dbfe9d681f1d.jpg' AS image_url
    UNION ALL SELECT '馒头' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/36398eec-5f99-463c-a2d7-53fcdcb54f2c.jpg' AS image_url
    UNION ALL SELECT '香锅牛蛙' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/620f074d-139e-454a-b5bd-638baec3962f.jpg' AS image_url
    UNION ALL SELECT '鮰鱼2斤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/d5be393b-f5e3-485f-b0d1-3c0f70d9ddbe.png' AS image_url
    UNION ALL SELECT '鸡蛋汤' AS dish_name, 'https://sky-food-ordering.oss-cn-wulanchabu.aliyuncs.com/a8e8c07d-9a1f-4a14-8814-0f9f144b27c9.jpg' AS image_url
) AS t ON d.name = t.dish_name
ORDER BY d.id;
