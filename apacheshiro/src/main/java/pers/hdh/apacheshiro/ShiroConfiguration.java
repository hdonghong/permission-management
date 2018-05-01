package pers.hdh.apacheshiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * ShiroConfiguration class<br/>
 * 自定义Shiro验证
 * @author hdonghong
 * @date 2018/04/30
 */
@Configuration
public class ShiroConfiguration {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);

        // 登录接口
        bean.setLoginUrl("/login");
        // 验证成功接口
        bean.setSuccessUrl("/index");
        // 未验证接口
        bean.setUnauthorizedUrl("/unauthorized");

        // public enum DefaultFilter {...}
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 【authc】：是org.apache.shiro.web.filter.authc.FormAuthenticationFilter类型的实例，其用于实现基于表单的身份验证
        filterChainDefinitionMap.put("/index", "authc");

        // 【anon】：表示不需要登录即可访问
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginUser", "anon");

        // 【"/admin", "roles[admin]"】：表示只有角色为admin的用户可以访问/admin接口
        filterChainDefinitionMap.put("/admin", "roles[admin]");

        // 【"/edit", "perms[edit]"】：表示拥有edit权限才能访问/edit接口
        filterChainDefinitionMap.put("/edit", "perms[edit]");

        // **：匹配路径中的零个或多个路径，如/admin/**将匹配/admin/a或/admin/a/b。
        // 【"/druid/**", "anon"】：表示不拦截访问/druid/下的任意请求
        filterChainDefinitionMap.put("/druid/**", "anon");

        // 【user】：认证过滤器，表示必须存在用户，当登入操作时不做检查
        filterChainDefinitionMap.put("/**", "user");

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    // SecurityManager：安全管理器；即所有与安全有关的操作都会与 SecurityManager 交互；且管理着所有 Subject；
    // 它是 Shiro 的核心，负责与后边介绍的其他组件进行交互，类似于SpringMVC 中的 DispatcherServlet 前端控制器；
    @Bean("securityManager")
    public SecurityManager securityManager(AuthRealm authRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    // Realm：域，Shiro 从 Realm 获取安全数据（如用户、角色、权限），就是说 SecurityManager 要验证用户身份，
    // 那么它需要从 Realm 获取相应的用户进行比较以确定用户身份是否合法；也需要从 Realm 得到用户相应的角色 / 权限
    // 进行验证用户是否能进行操作；可以把 Realm 看成 DataSource，即安全数据源。
    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher") CredentialMatcher credentialMatcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(credentialMatcher);
        return authRealm;
    }

    // 自定义的密码校验
    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    // 用于开启Shiro Spring AOP权限注解的支持
    // 处理shiro和spring关联，让spring管理shiro时使用我们自定义的SecurityManager
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    // 处理shiro和spring关联，使用AOP代理类
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
