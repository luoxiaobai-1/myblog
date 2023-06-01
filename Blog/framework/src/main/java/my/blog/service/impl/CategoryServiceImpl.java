package my.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import my.blog.Constants.SystemConstants;
import my.blog.domain.Dto.CategoryDto;
import my.blog.domain.Dto.CategoryListDto;
import my.blog.domain.Dto.CategoryUPateDto;
import my.blog.domain.Vo.Categoryvo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Tagvo;
import my.blog.domain.entity.Article;
import my.blog.domain.entity.Category;


import my.blog.domain.entity.Tag;
import my.blog.mapper.CategoryDao;

import my.blog.service.ArticleService;
import my.blog.service.CategoryService;
import my.blog.utils.BeanCopy;
import my.blog.utils.SecurityUtils;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(SgCategory)表服务实现类
 *
 * @author makejava
 * @since 2023-04-23 22:33:50
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Autowired
    private ArticleService articleServiceiml;
    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Categoryvo> getcategorylist() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleServiceiml.list(wrapper);
        Set<Long> catoryid = articles.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        List<Category> categories = listByIds(catoryid).stream()
                .filter(category -> SystemConstants.Category_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        return BeanCopy.copyBeanList(categories, Categoryvo.class);

    }

    @Override
    public List<Categoryvo> listallcategory() {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Categoryvo> categoryvos = BeanCopy.copyBeanList(list(queryWrapper), Categoryvo.class);
        return categoryvos;
    }

    @Override
    public Pagevo getCategorys(CategoryListDto categoryListDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryListDto.getName()), Category::getName, categoryListDto.getName());
        queryWrapper.like(StringUtils.hasText(categoryListDto.getStatus()), Category::getStatus, categoryListDto.getStatus());
        Page<Category> page = new Page<>();
        page.setCurrent(categoryListDto.getPageNum());
        page.setSize(categoryListDto.getPageSize());
        page(page, queryWrapper);
        List<Categoryvo> categoryvos = BeanCopy.copyBeanList(page.getRecords(), Categoryvo.class);
        return new Pagevo(categoryvos, page.getTotal());
    }
@Transactional
    @Override
    public boolean AddCategory(CategoryDto categoryDto) {
        Category category = BeanCopy.copyBean(categoryDto, Category.class);
        category.setCreateBy(SecurityUtils.getUserId());
        category.setCreateTime(new Date());
      return   save(category);

    }

    @Override
    public Categoryvo getCategory(long id) {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId,id);
        Category one = getOne(queryWrapper);
        return BeanCopy.copyBean(one, Categoryvo.class);
    }
@Transactional
    @Override
    public boolean updateCategory(CategoryUPateDto uPateDto) {
        Category category = BeanCopy.copyBean(uPateDto, Category.class);
        category.setUpdateBy(SecurityUtils.getUserId());
        category.setUpdateTime(new Date());
     return    updateById(category);
    }
@Transactional
    @Override
    public boolean Delcategory(long[] id) {
     return    categoryDao.deleteByIds(id,new Date(),SecurityUtils.getUserId());
    }
}

