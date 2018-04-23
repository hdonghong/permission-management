package pers.hdh.springsecurity;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DemoController class<br/>
 *
 * @author hdonghong
 * @date 2018/04/22
 */
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "hello hdonghong~";
    }

    @GetMapping("/test")
    public String test() {
        return "test authentication";
    }

    // ROLE_是Spring Security要求的权限前缀，参考源码：private String defaultRolePrefix = "ROLE_";
    // 这里@PreAuthorize注解发生在方法执行前，意思是要求执行此方法要有ADMIN权限
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/test2")
    public String test2() {
        return "admin auth";
    }

    // 支持表达式
    // 方法执行前
    @PreAuthorize("#id < 10 and principal.username.equals(#username) and #user.username.equals('abc')")
    // 方法执行后，returnObject代表返回值对象
    @PostAuthorize("returnObject % 2 == 0")
    @GetMapping("/test3")
    public Integer test3(Integer id, String username, User user) {
        return id;
    }

    @PreFilter("filterObject % 2 == 0")
    @PostFilter("filterObject % 4 == 0")
    @GetMapping("/test4")
    public List<Integer> test4(List<Integer> idList) {
        return idList;
    }
}
