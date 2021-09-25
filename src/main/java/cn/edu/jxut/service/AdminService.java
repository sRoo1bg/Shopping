package cn.edu.jxut.service;

import cn.edu.jxut.pojo.Admin;

/**
 * @author BrokenArrow
 */
public interface AdminService {
    /**
     * 用户登录方法
     * @param aName 用户名
     * @param aPass 密码
     * @return 返回Admin对象
     */
    Admin login(String aName,String aPass);
}
