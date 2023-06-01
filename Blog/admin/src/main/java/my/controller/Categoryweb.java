package my.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import lombok.experimental.Accessors;
import my.blog.annotation.SystemLog;
import my.blog.domain.Dto.CategoryDto;
import my.blog.domain.Dto.CategoryListDto;
import my.blog.domain.Dto.CategoryUPateDto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Categoryvo;
import my.blog.domain.Vo.ExcelCategoryVo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.entity.Category;
import my.blog.service.CategoryService;
import my.blog.utils.BeanCopy;
import my.blog.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class Categoryweb {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @SystemLog(buinessname = "查询所有分类")
    public ResponseResult getallcategory() {
        List<Categoryvo> categoryvos = categoryService.listallcategory();
        return ResponseResult.success(categoryvos, HttpCodeEnum.SUCCESS);

    }

    @GetMapping("/export")

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopy.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

    }


    @GetMapping("/list")
    @SystemLog(buinessname = "查询指定分类")
    public  ResponseResult CategoryList(CategoryListDto categoryListDto)
    {

        Pagevo pagevo =categoryService.getCategorys(categoryListDto);
        return  ResponseResult.success(pagevo,HttpCodeEnum.SUCCESS);
    }
    @PostMapping
    public ResponseResult AddCategory(@RequestBody @Validated CategoryDto categoryDto)
    {

        boolean flag=categoryService.AddCategory(categoryDto);
        return flag ? ResponseResult.success() : ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }
    @GetMapping("/{id}")
    public  ResponseResult GetSelecetdCategory(@PathVariable long id)
    {
        Categoryvo categoryvo=categoryService.getCategory(id);
        return ResponseResult.success(categoryvo,HttpCodeEnum.SUCCESS);
    }
    @PutMapping

    public  ResponseResult UpdateCategory(@RequestBody @Validated CategoryUPateDto uPateDto)
    {

     boolean flag=   categoryService.updateCategory(uPateDto);
        return flag ? ResponseResult.success() : ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }
    @DeleteMapping("/{id}")
    public ResponseResult Delcategory(@PathVariable long [] id )
    {
        boolean flag= categoryService.Delcategory(id);

        return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);


    }
}