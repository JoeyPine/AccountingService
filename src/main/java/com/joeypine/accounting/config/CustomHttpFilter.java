package com.joeypine.accounting.config;

import lombok.val;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class CustomHttpFilter extends PermissionsAuthorizationFilter {
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String currentPath = getPathWithinApplication(request);

        val array = path.split("::");
        val url = array[0];
        boolean isHttpMethodMached = false;
        if (array.length > 1) {
            val methodInRequest = ((HttpServletRequest) request).getMethod().toUpperCase();
            val method = array[1];
            isHttpMethodMached = method.equals(methodInRequest);
        }
        return pathsMatch(url, currentPath) && isHttpMethodMached;
    }
}
