package pers.hdh.apacheshiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import pers.hdh.apacheshiro.po.Permission;
import pers.hdh.apacheshiro.po.Role;
import pers.hdh.apacheshiro.po.User;
import pers.hdh.apacheshiro.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AuthRealm class<br/>
 *
 * @author hdonghong
 * @date 2018/04/30
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // Authorization：授权，即权限验证，验证某个已认证的用户是否拥有某个权限；
    // principals：身份，即主体的标识属性，可以是任何东西，如用户名、邮箱等，唯一即可。
    // 一个主体可以有多个 principals，但只有一个 Primary principals，一般是用户名 / 密码 / 手机号。
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissionList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();

        Set<Role> roleSet = user.getRoles();
        if (!CollectionUtils.isEmpty(roleSet)) {
            roleSet.forEach(role -> {
                roleList.add(role.getRname());

                Set<Permission> permissionSet = role.getPermissions();
                if (!CollectionUtils.isEmpty(permissionSet)) {
                    permissionList.addAll(permissionSet.stream()
                                                    .map(permission -> permission.getName())
                                                    .collect(Collectors.toList()));
                }
            });
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleList);
        info.addStringPermissions(permissionList);
        return info;
    }

    /**
     * Authentication：身份认证 / 登录，验证用户是不是拥有相应的身份；
     * 登录时调用subject.login(token)，Subject会委托SecuriyManager执行，SecuriyManager调用它的Realm执行会跳转到这里，
     * 本例中 /loginUser 接口创建的UsernamePasswordToken会传到这里。
     *
     * 执行流程：
     * 首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
     * 如果user找到但锁定了抛出锁定异常LockedAccountException；
     * 最后生成AuthenticationInfo信息，交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
     * 如果不匹配将抛出密码错误异常IncorrectCredentialsException；
     * 另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException；
     * 在组装SimpleAuthenticationInfo信息时，需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），
     * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUsername(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
