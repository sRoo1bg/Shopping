package cn.edu.jxut.service;

import cn.edu.jxut.pojo.ProductInfo;
import cn.edu.jxut.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author BrokenArrow
 */
public interface ProductInfoService {
    /**
     * 查询全部商品不分页
     * @return 返回list集合
     */
    List<ProductInfo> getAll();

    /**
     * 分页查询商品信息
     * @param pageNum 当前页
     * @param pageSize 每页几条
     * @return 返回PageInfo
     */
    PageInfo<ProductInfo> splitPage(int pageNum,int pageSize);

    /**
     * 新增商品
     * @param info 商品信息对象
     * @return 返回int
     */
    int save(ProductInfo info);

    /**
     * 通过主键查询信息
     * @param id 主键id
     * @return 返回对象
     */
    ProductInfo getById(int id);

    /**
     * 更新商品
     * @param info 对象信息
     * @return 返回int
     *
     */
    int update(ProductInfo info);

    /**
     * 通过主键id删除商品
     * @param pid 主键id
     * @return 返回int类型
     */
    int delete(int pid);

    /**
     * 批量删除id
     * @param ids id数组
     * @return 返回int
     */
    int deleteBatch(String [] ids);

    /**
     * 查询商品信息
     * @param vo 商品条件封装对象
     * @return 返回list集合
     */
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    /**
     * 带条件分页查询
     * @param vo vo对象参数
     * @param pageSize 页码
     * @return 返回PageInfo
     */
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo,int pageSize);
}
