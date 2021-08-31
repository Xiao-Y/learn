package com.billow.promotion.common.cache;

import com.billow.promotion.common.enums.LuaScriptEnum;
import com.billow.tools.utlis.SpringContextUtil;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LuaUtil {

    private static final Logger log = LoggerFactory.getLogger(LuaUtil.class);

    private static final HashMap<String, String> luaMap = new HashMap<>();

    private static final HashMap<String, String> shaMap = new HashMap<>();

    private static final RedissonClient redissonClient = SpringContextUtil.getBean(RedissonClient.class);

    static {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver(new DefaultResourceLoader()).getResources("classpath*:script/*.lua");
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String result = new BufferedReader(new InputStreamReader(resource.getInputStream()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                if (!StringUtils.isEmpty(filename) && !StringUtils.isEmpty(result)) {
                    luaMap.put(filename, result);
                }
            }
            luaMap.forEach((k, v) -> {
                String sha = redissonClient.getScript(StringCodec.INSTANCE).scriptLoad(v);
                shaMap.put(k, sha);
            });
        } catch (Throwable e) {
            log.error("解析resource异常， eMsg={}", e.getMessage(), e);
            System.exit(0);
        }
    }

    private static String getLua(String fileName) {
        return luaMap.get(fileName);
    }

    private static String getSha(String fileName) {
        return shaMap.get(fileName);
    }

    /**
     * 执行 lua 脚本
     *
     * @param fileNameEnum 脚本名称
     * @param keys         redis key
     * @param values       入参
     * @return {@link Long}
     * @author liuyongtao
     * @since 2021-8-12 9:20
     */
    public static Long execute(LuaScriptEnum fileNameEnum, List<Object> keys, Object... values) {
        log.info("lua 入参：fileNameEnum:{},keys{},values{}", fileNameEnum, keys, values);
        return redissonClient.getScript(LongCodec.INSTANCE)
                .evalSha(RScript.Mode.READ_WRITE,
                        getSha(fileNameEnum.getFileName()),
                        RScript.ReturnType.INTEGER,
                        keys,
                        values);
    }
}