local stock = tonumber(redis.call('get', KEYS[1]))
if (stock == nil or stock <= 0) then
    return 0
end
redis.call('decr', KEYS[1])
redis.call('set', KEYS[2], ARGV[1], 'EX', ARGV[2])
return 1
