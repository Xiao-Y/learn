--- 库存key
local seckillStockKey = KEYS[1];
--- 秒杀key
local seckillCountKey = KEYS[2];
--- 每个用户秒杀商品数量上限
local seckillLimit = ARGV[1];

--- 调试信息：使用 redis.log 记录 seckillStockKey 的值
--redis.log(redis.LOG_NOTICE, "seckillStockKey: " .. seckillStockKey);
--redis.log(redis.LOG_NOTICE, "seckillCountKey: " .. seckillCountKey);
--redis.log(redis.LOG_NOTICE, "seckillLimit: " .. seckillLimit);
redis.log(redis.LOG_NOTICE, "seckillStockKey: " .. seckillStockKey);

--- 是否库存存在
if (redis.call('exists', seckillStockKey) == 0) then
    return -6;
end ;

--- 获取指定的库存，库存为空或者小于等0时返回
local resultStock = redis.call("get", seckillStockKey);
if resultStock == false then
    resultStock = 0
end
redis.log(redis.LOG_NOTICE, "resultStock: " .. resultStock);
if (resultStock == nil or tonumber(resultStock) <= 0) then
    return -4;
end ;

--- 已经秒杀的次数，达到上限时返回
local seckillCount = redis.call('get', seckillCountKey)
if seckillCount == false then
    seckillCount = 0
end

redis.log(redis.LOG_NOTICE, "seckillCount: " .. tostring(seckillCount));
if (seckillCount ~= nil and tonumber(seckillCount) >= tonumber(seckillLimit)) then
    return -1;
end ;

--- 递减库存
redis.call("decr", seckillStockKey);
--- 秒杀成功，数据加1
redis.call("incr", seckillCountKey);

return 1;