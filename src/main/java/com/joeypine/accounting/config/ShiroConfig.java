package com.joeypine.accounting.config;

import lombok.val;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    @Bean
    public SecurityManager securityManager(Realm realm) {
        return new DefaultWebSecurityManager(realm);
    }

    /**
     * Shiro Fitler ,实现权限相关的拦截
     * <p>
     * anon: 无需 login access
     * authc: required login，and then access
     * user: remeber me -> access
     * role: role -> access
     * @param securityManager
     * @return macher
     */

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        val shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        val shiroFilterDefinitionMap = new LinkedHashMap<String, String>();

        //@Todo: consider different HTTP method need different filter.
//        shiroFilterDefinitionMap.put("/v1.0/greeting", "authc");
        shiroFilterDefinitionMap.put("/v1.0/users", "anon");
        shiroFilterDefinitionMap.put("/v1.0/session", "anon");
        shiroFilterDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterDefinitionMap);

        return shiroFilterFactoryBean;

    }

    @Bean
    public HashedCredentialsMatcher matcher() {
        val macher = new HashedCredentialsMatcher();
        macher.setHashAlgorithmName("SHA-256");
        macher.setHashIterations(1000);
        macher.setHashSalted(true);
        macher.setStoredCredentialsHexEncoded(false);
        return macher;
    }

}
