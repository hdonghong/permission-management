package pers.hdh.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

/**
 * SpringSecurityConfig class<br/>
 *
 * @author hdonghong
 * @date 2018/04/22
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService myUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 注意：使用spring security5，需要加上{noop}指定使用NoOpPasswordEncoder给DelegatingPasswordEncoder去校验密码
        // ，同时需要知道NoOpPasswordEncoder已经过时了，这里仅是为了方便
        // auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
        // auth.inMemoryAuthentication().withUser("scott").password("{noop}scott").roles("USER");

        // 上面的可以注释了
        // 使用自己的密码校验类
        auth.userDetailsService(myUserService).passwordEncoder(new MyPasswordEncoder());
        // 数据库管理方面支持的默认处理
        auth.jdbcAuthentication()
                .usersByUsernameQuery("")
                .authoritiesByUsernameQuery("")
                .passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许访问项目主路径 / 的请求
        // 其它请求都要经过拦截验证
        // 同时也允许注销请求
        // 支持表单验证登录
        // 取消掉默认的csrf认证
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin();
        http.csrf().disable();
    }

    /**
     * 放行静态资源，本次项目中用不到
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不拦截静态资源
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }
}
