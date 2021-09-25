package cn.edu.jxut.service.impl;

import cn.edu.jxut.dao.ProductInfoMapper;
import cn.edu.jxut.pojo.ProductInfo;
import cn.edu.jxut.pojo.ProductInfoExample;
import cn.edu.jxut.pojo.vo.ProductInfoVo;
import cn.edu.jxut.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program FivePart
 * @Description 商品接口实现
 * @Author Rorschach
 * @Date 2021/9/23 9:59
 **/
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper mapper;


    @Override
    public List<ProductInfo> getAll() {
        return mapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo<ProductInfo> splitPage(int pageNum, int pageSize) {
        //使用分页插件完成分页
        PageHelper.startPage(pageNum, pageSize);
        //进行PageInfo的数据封装
        //在进行有条件的查询时需要创建ProductInfoExample
        ProductInfoExample example=new ProductInfoExample();
        //按主键降序排序
        example.setOrderByClause("p_id desc");
        //设置完排序取集合
        List<ProductInfo> list=mapper.selectByExample(example);
        //将查询到的集合放入PageInfo进行返回
        PageInfo<ProductInfo> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return mapper.insert(info);
    }

    @Override
    public ProductInfo getById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(ProductInfo info) {
        return mapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return mapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return mapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return mapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合前设置PageHelper属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list=mapper.selectCondition(vo);
        return new PageInfo<>(list);
    }

}
