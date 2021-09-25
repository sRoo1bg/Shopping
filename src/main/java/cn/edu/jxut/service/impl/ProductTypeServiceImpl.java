package cn.edu.jxut.service.impl;

import cn.edu.jxut.dao.ProductTypeMapper;
import cn.edu.jxut.pojo.ProductType;
import cn.edu.jxut.pojo.ProductTypeExample;
import cn.edu.jxut.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program FivePart
 * @Description
 * @Author Rorschach
 * @Date 2021/9/23 15:47
 **/
@Service("productTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper mapper;
    @Override
    public List<ProductType> getTypeAll() {
        return mapper.selectByExample(new ProductTypeExample());
    }
}
