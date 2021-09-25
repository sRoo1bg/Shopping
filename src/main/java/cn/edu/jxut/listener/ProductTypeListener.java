package cn.edu.jxut.listener;

import cn.edu.jxut.pojo.ProductType;
import cn.edu.jxut.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @Program FivePart
 * @Description 商品类别监听器
 * @Author Rorschach
 * @Date 2021/9/23 15:29
 **/
@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        //手动从spring容器中取出接口实现类的对象
        String config="applicationContext.xml";
        ApplicationContext ac=new ClassPathXmlApplicationContext(config);
        ProductTypeService service= (ProductTypeService) ac.getBean("productTypeServiceImpl");
        List<ProductType> typeList=service.getTypeAll();
        //将数据放入全局作用域中
        sce.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
