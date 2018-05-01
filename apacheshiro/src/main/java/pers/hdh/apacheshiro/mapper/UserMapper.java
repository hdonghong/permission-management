package pers.hdh.apacheshiro.mapper;

import org.apache.ibatis.annotations.Param;
import pers.hdh.apacheshiro.po.User;

/**
 * UserMapper interface<br/>
 *
 * @author hdonghong
 * @date 2018/04/30
 */
public interface UserMapper {

    User findByUsername(@Param("username") String username);
}
