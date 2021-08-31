--- 库存key
local seckillStockKey = KEYS[1];
--- 秒杀key
local seckillCountKey = KEYS[2];
--- 每个用户秒杀商品数量上限
local seckillLimit = ARGV[1];

--- 是否库存存在
if (redis.call('exists', seckillStockKey) == 0) then
    return -6;
end ;
--- 获取指定的库存
local resultStock = redis.call("get", seckillStockKey);

--- 库存为空或者小于等0时返回
if resultStock == nil or tonumber(resultStock) <= 0 then
    return -4;
end ;

--- 已经秒杀过
if (redis.call('exists', seckillCountKey) == 1 and redis.call('get', seckillCountKey) >= tonumber(seckillLimit)) then
    return -1;
end ;

--- 递减库存
redis.call("decr", seckillStockKey);
--- 秒杀成功，数据加1
redis.call("incr", seckillCountKey);

return 1;

