package com.joeypine.accounting.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    private static final String DEFAULT_PATH_SEPARATOR = "/";

    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }

        String currentPath = getPathWithinApplication(request);

        if (currentPath != null && !DEFAULT_PATH_SEPARATOR.equals(currentPath)
                && currentPath.endsWith(DEFAULT_PATH_SEPARATOR)) {
            currentPath = currentPath.substring(0, currentPath.length() - 1);
        }

        String finalCurrentPath = currentPath;
        return filterChainManager.getChainNames()
                .stream()
                .filter(chain -> isHttpRequestMached(chain, finalCurrentPath, request))
                .findAny()
                .map(chain -> filterChainManager.proxy(originalChain, chain))
                .orElse(null);

    }

    /***
     * compare whether the http request mached
     * 1.Compare request url.
     * 2.If has http method mark,compare http method.
     * @param chain the specific chain
     * @param currentPath the current Path
     * @param request the http request
     * @return the flag indicates whether http request mached.
     */
    private boolean isHttpRequestMached(String chain, String currentPath, ServletRequest request) {
        val array = chain.split("::");
        val url = array[0];
        boolean isHttpMethodMached = false;
        if (array.length > 1) {
            val methodInRequest = ((HttpServletRequest) request).getMethod().toUpperCase();
            val method = array[1];
            isHttpMethodMached = method.equals(methodInRequest);
        }
        return pathMatches(url, currentPath) && isHttpMethodMached;
    }
}

