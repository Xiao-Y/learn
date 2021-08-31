local hashKey = ARGV[1];
local num = tonumber(ARGV[2]);

if (redis.call('exists', KEYS[1]) == 0) then
    redis.call('hset', KEYS[1], hashKey, 0)
end

local store = tonumber(redis.call('hget', KEYS[1], hashKey));

if (store == nil) then
    redis.call('hset', KEYS[1], hashKey, 0);
end

if (num <= 0) then
    return tonumber(redis.call('hget', KEYS[1], hashKey));
end

return tonumber(redis.call('hincrby', KEYS[1], hashKey, num));