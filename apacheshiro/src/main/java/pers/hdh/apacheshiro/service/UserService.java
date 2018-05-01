package pers.hdh.apacheshiro.service;

import pers.hdh.apacheshiro.po.User;

/**
 * UserService interface<br/>
 *
 * @author hdonghong
 * @date 2018/04/30
 */
public interface UserService {

    User findByUsername(String username);
}
