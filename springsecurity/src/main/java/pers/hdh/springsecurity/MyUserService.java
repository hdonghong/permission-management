package pers.hdh.springsecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * MyUserService class<br/>
 * 数据库权限管理类
 * @author hdonghong
 * @date 2018/04/23
 */
@Component
public class MyUserService implements UserDetailsService {// 4 TODO
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
