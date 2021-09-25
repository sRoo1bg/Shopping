package cn.edu.jxut.service.impl;

import cn.edu.jxut.dao.AdminMapper;
import cn.edu.jxut.pojo.Admin;
import cn.edu.jxut.pojo.AdminExample;
import cn.edu.jxut.service.AdminService;
import cn.edu.jxut.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Program FivePart
 * @Description service实现类
 * @Author Rorschach
 * @Date 2021/9/23 8:20
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper mapper;

    @Override
    public Admin login(String aName, String aPass) {
        AdminExample example=new AdminExample();
        example.createCriteria().andANameEqualTo(aName);
        List<Admin> list=mapper.selectByExample(example);
        if(list.size()>0){
            //成功
            Admin admin= list.get(0);
            String pwd= Md5Util.getMd5(aPass);
            if(pwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
