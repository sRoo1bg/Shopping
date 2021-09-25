package cn.edu.jxut.service;

import cn.edu.jxut.pojo.ProductType;

import java.util.List;

/**
 * @author BrokenArrow
 */
public interface ProductTypeService {
    /**
     * 查询类型
     * @return 返回list
     */
    List<ProductType> getTypeAll();
}
