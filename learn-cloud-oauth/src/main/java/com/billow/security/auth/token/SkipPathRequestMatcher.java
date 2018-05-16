package com.billow.security.auth.token;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 跳过请求匹配路径
 */
public class SkipPathRequestMatcher implements RequestMatcher {
    private OrRequestMatcher matcher;
    private List<RequestMatcher> processingMatchers;

    /**
     * @param paths 拦截的路径
     */
    public SkipPathRequestMatcher(List<String> paths) {
        Assert.notNull(paths, "路径不能为空");
        matcher = new OrRequestMatcher(paths.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList()));
    }

    /**
     * 暂不支持
     *
     * @param pathsToSkip 不拦截路径
     * @param paths       拦截路径
     */
    @Deprecated
    public SkipPathRequestMatcher(List<String> pathsToSkip, List<String> paths) {
        Assert.notNull(pathsToSkip, "路径不能为空");
        List<RequestMatcher> m = pathsToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        matcher = new OrRequestMatcher(m);
        if (!CollectionUtils.isEmpty(paths)) {
            processingMatchers = new ArrayList<>();
            paths.forEach(req -> processingMatchers.add(new AntPathRequestMatcher(req)));
        }

    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return matcher.matches(request);
    }
}

