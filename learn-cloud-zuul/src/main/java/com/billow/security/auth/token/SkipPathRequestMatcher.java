package com.billow.security.auth.token;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 需要 拦截的路径
 */
public class SkipPathRequestMatcher implements RequestMatcher {
    private OrRequestMatcher matcher;

    /**
     * @param paths 拦截的路径
     */
    public SkipPathRequestMatcher(List<String> paths) {
        Assert.notNull(paths, "路径不能为空");
        matcher = new OrRequestMatcher(paths.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList()));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return matcher.matches(request);
    }
}

