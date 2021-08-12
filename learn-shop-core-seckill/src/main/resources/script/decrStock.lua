local hashKey = ARGV[1];
local num = tonumber(ARGV[2]);
local mode = tonumber(ARGV[3]);

if (redis.call('exists', KEYS[1]) == 0) then
    redis.call('hset', KEYS[1], hashKey, 0);
end ;

local store = tonumber(redis.call('hget', KEYS[1], hashKey));
local count = tonumber(store - num);

if (num <= 0 or store <= 0) then
    return 0;
elseif (count < 0) then
    if (mode == 0) then
        return 0;
    elseif (mode == 1) then
        redis.call('hincrby', KEYS[1], hashKey, -store);
        return store;
    end ;
elseif (count >= 0) then
    redis.call('hincrby', KEYS[1], hashKey, -num);
    return num;
end ;
return -99;
