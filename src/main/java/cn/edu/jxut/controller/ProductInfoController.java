package cn.edu.jxut.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.edu.jxut.pojo.ProductInfo;
import cn.edu.jxut.pojo.vo.ProductInfoVo;
import cn.edu.jxut.service.ProductInfoService;
import cn.edu.jxut.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Program FivePart
 * @Description 商品控制层
 * @Author Rorschach
 * @Date 2021/9/23 10:02
 **/
@Controller
@RequestMapping("/prod")
public class ProductInfoController {
    public static final int PAGE_SIZE = 5;
    //异步上传文件名称
    String saveFileName = "";
    @Autowired
    ProductInfoService service;

    /**
     * 查询所有商品信息不分页
     */
    @RequestMapping("/getAll.action")
    public String getAll(HttpServletRequest request) {
        List<ProductInfo> list = service.getAll();
        request.setAttribute("list", list);
        return "product";
    }

    /**
     * 查询所有商品信息分页处理
     */
    @RequestMapping("/splitPage.action")
    public String split(HttpServletRequest request) {
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("prodVo");
        if(vo!=null){
            info=service.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else {
            //得到第一页数据
            info = service.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info", info);
        return "product";
    }

    /**
     * ajax分页传递数据
     */
    @RequestMapping("/ajaxSplit.action")
    @ResponseBody
    public void ajaxSplit(ProductInfoVo vo, HttpSession session) {
        //获取当前page页面参数的数据
        PageInfo info = service.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("info", info);
    }

    /**
     * 多条件查询
     */
    @RequestMapping("/search.action")
    @ResponseBody
    public void search(ProductInfoVo vo, HttpSession session) {
        List<ProductInfo> list = service.selectCondition(vo);
        session.setAttribute("list", list);
    }

    /**
     * 异步ajax文件上传处理
     */
    @RequestMapping("/ajaxImg.action")
    @ResponseBody
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        //MultipartFile用于文件上传
        //提取生成文件名
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端JSON对象,封装图片的路径,为了在页面回显
        JSONObject obj = new JSONObject();
        obj.put("imgurl", saveFileName);
        return obj.toString();
    }

    /**
     * 添加商品
     */
    @RequestMapping("/save.action")
    public String save(ProductInfo info, HttpServletRequest request) {
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        int num = -1;
        try {
            num = service.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "商品增加成功");
        } else {
            request.setAttribute("msg", "商品增加失败");
        }
        //清空
        saveFileName = "";
        //增加成功/失败需要重新访问数据库,跳转到分页显示的action上
        return "forward:/prod/splitPage.action";
    }

    /**
     * 回显商品信息
     */
    @RequestMapping("/showInfo.action")
    public String showInfo(int pid,ProductInfoVo vo, Model model,HttpSession session) {
        ProductInfo info = service.getById(pid);
        model.addAttribute("prod", info);
        //将多条件及页码放入session更新处理结束读取进行处理
        session.setAttribute("prodVo",vo);
        return "update";
    }

    /**
     * 更新商品
     */
    @RequestMapping("/update.action")
    public String update(ProductInfo info, HttpServletRequest request) {
        //如果有用过ajax异步上传图片则能获取图片名称,如果没有则saveFileName=""; 实体类info使用隐藏表单域提供上来的pimage原始图片名称
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        int num = -1;
        try {
            num = service.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "更新成功");
        } else {
            request.setAttribute("msg", "更新失败");
        }
        //处理下一次更新这个变量会被作为依据报错,必须清空
        saveFileName = "";
        return "forward:/prod/splitPage.action";
    }

    /**
     * 单个删除
     */
    @RequestMapping("/deleteAjax.action")
    public String deleteAjax(int pid,ProductInfoVo vo, HttpServletRequest request) {
        int num = -1;
        try {
            num = service.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "删除成功");
            request.getSession().setAttribute("deleteProdVo",vo);
        } else {
            request.setAttribute("msg", "删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    /**
     * 删除返回地址
     */
    @RequestMapping(value = "/deleteAjaxSplit.action", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public Object deleteAjaxSplit(HttpServletRequest request) {
        PageInfo info = null;
        Object vo=request.getSession().getAttribute("deleteProdVo");
        if(vo!=null){
            info=service.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        }else {
            info=service.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deleteBatch.action")
    public String deleteBatch(String pids, HttpServletRequest request) {
        //将上传的字符串分割
        String[] ps = pids.split(",");
        try {
            int num = service.deleteBatch(ps);
            if (num > 0) {
                request.setAttribute("msg", "批量删除成功");
            } else {
                request.setAttribute("msg", "批量删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg", "该商品不可被删除");
            e.printStackTrace();
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
}
