local key = KEYS[1];
local num = tonumber(ARGV[1]);
local mode = tonumber(ARGV[2]);

if (redis.call('exists', key) == 0) then
    redis.call('set', key, 0);
end ;

local store = tonumber(redis.call('get', key));
local count = tonumber(store - num);

if (num <= 0 or store <= 0) then
    return 0;
elseif (count < 0) then
    if (mode == 0) then
        return 0;
    elseif (mode == 1) then
        redis.call('incrby', key, -store);
        return store;
    end ;
elseif (count >= 0) then
    redis.call('incrby', key, -num);
    return num;
end ;
return -99;
