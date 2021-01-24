--- 库存key
local seckillStockKey = KEYS[1];
--- 秒杀key
local seckillLockKey = KEYS[2];
--- 获取秒杀商品信息
local successKillInfo = ARGV[1];
--- 获取秒杀商品超时时间
local expire = ARGV[2];

--- 获取指定的库存
local resultStock = redis.call("get", seckillStockKey);
--- 是否库存存在
if not resultStock then
    return -6;
end

--- 库存为空或者小于等0时返回
if resultStock == nil or tonumber(resultStock) <= 0 then
    return -4;
end

--- 已经秒杀过
if redis.call("get", seckillLockKey) then
    return -1;
end

--- 递减库存
redis.call("decr", seckillStockKey);
--- 保存订单信息
redis.call("set", seckillLockKey, successKillInfo);
--- 转换分钟为秒
if tonumber(expire) > 0 then
    local paymentExp = tonumber(expire) * 60;
    --- 设置过期时间
    redis.call("expire", seckillLockKey, paymentExp);
end
return 1;

