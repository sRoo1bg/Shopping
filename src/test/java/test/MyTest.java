package test;

import cn.edu.jxut.dao.ProductInfoMapper;
import cn.edu.jxut.pojo.ProductInfo;
import cn.edu.jxut.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Program FivePart
 * @Description
 * @Author Rorschach
 * @Date 2021/9/22 21:13
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest {
    @Autowired
    ProductInfoMapper mapper;
    @Test
    public void Test01(){
        ProductInfoVo vo=new ProductInfoVo();
        vo.setPname("4");
        List<ProductInfo> list=mapper.selectCondition(vo);
        for (ProductInfo info:
             list) {
            System.out.println(info);
        }
    }
}
