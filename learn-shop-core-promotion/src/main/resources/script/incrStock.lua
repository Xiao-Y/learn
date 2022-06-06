local key = KEYS[1];
local num = tonumber(ARGV[1]);

if (redis.call('exists', key) == 0) then
    redis.call('set', key, 0)
end

local store = tonumber(redis.call('get', key));

if (num <= 0) then
    return tonumber(redis.call('get', key));
end

return tonumber(redis.call('incrby', key, num));