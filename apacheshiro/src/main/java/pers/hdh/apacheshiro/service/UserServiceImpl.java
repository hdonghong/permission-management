package pers.hdh.apacheshiro.service;

import org.springframework.stereotype.Service;
import pers.hdh.apacheshiro.mapper.UserMapper;
import pers.hdh.apacheshiro.po.User;

import javax.annotation.Resource;

/**
 * UserServiceImpl class<br/>
 *
 * @author hdonghong
 * @date 2018/04/30
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
