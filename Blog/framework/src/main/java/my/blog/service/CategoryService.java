package my.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Dto.CategoryDto;
import my.blog.domain.Dto.CategoryListDto;
import my.blog.domain.Dto.CategoryUPateDto;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Categoryvo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.entity.Category;

import java.util.List;


/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2023-04-23 22:33:50
 */
public interface CategoryService extends IService<Category> {

   List<Categoryvo> getcategorylist();

   List<Categoryvo> listallcategory();

    Pagevo getCategorys(CategoryListDto categoryListDto);

    boolean AddCategory(CategoryDto categoryDto);

    Categoryvo getCategory(long id);

    boolean updateCategory(CategoryUPateDto uPateDto);

    boolean Delcategory(long[] id);
}

