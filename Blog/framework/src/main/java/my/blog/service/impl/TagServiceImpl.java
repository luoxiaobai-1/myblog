package my.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.blog.Constants.SystemConstants;
import my.blog.domain.Dto.TagListDto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Tagvo;
import my.blog.domain.entity.Tag;
import my.blog.exception.SystemException;
import my.blog.mapper.TagMapper;
import my.blog.service.TagService;
import my.blog.utils.BeanCopy;
import my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
* @author 14574
* @description 针对表【sg_tag(标签)】的数据库操作Service实现
* @createDate 2023-05-01 20:55:02
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {
@Autowired
TagMapper tagMapper;
    @Override
    public Pagevo GettagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getName, tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        List<Tagvo> tagvos = BeanCopy.copyBeanList(page.getRecords(), Tagvo.class);
        return new Pagevo(tagvos, page.getTotal());

    }

    @Override
    public boolean Addtag(TagListDto tagListDto) {
        long id = SecurityUtils.getUserId();
        Tag tag = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        return save(tag);


    }

//    @Override
//    public boolean Deltag(long id) {
//        LambdaUpdateWrapper<Tag> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        lambdaUpdateWrapper.eq(Tag::getId, id).set(Tag::getDelFlag, SystemConstants.LOGIC_DELETE_VALUE).set(Tag::getUpdateBy, SecurityUtils.getUserId())
//                .set(Tag::getUpdateTime, new Date());
//        return update(null, lambdaUpdateWrapper);
//
//
//    }

    @Override
    public Tagvo Gettag(long id) {
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId,id);
        Tag tag =null;
             tag   = getOne(queryWrapper);
             return BeanCopy.copyBean(tag,Tagvo.class);

    }

    @Override
    public boolean updatetag(Tagvo tagvo) {

        if (!StringUtils.hasText(tagvo.getName()))
        {
            throw new SystemException(HttpCodeEnum.ILLEGAL_PARAMETER);
        }
        if (!StringUtils.hasText(tagvo.getRemark()))
        {
            throw new SystemException(HttpCodeEnum.ILLEGAL_PARAMETER);
        }


        Tag tag=new Tag();
        tag.setId(tagvo.getId());
        tag.setRemark(tagvo.getRemark());
tag.setName(tagvo.getName());
tag.setUpdateBy(SecurityUtils.getUserId());
tag.setUpdateTime(new Date());
 return     updateById(tag);
    }

    @Override
    public List<Tagvo> getalltag() {
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName);

        return BeanCopy.copyBeanList(list(queryWrapper), Tagvo.class);
    }


    public boolean Deltag(long id []) {

//        for (long l : id) {
//            LambdaUpdateWrapper<Tag> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//            lambdaUpdateWrapper.eq(Tag::getId, l).set(Tag::getDelFlag, SystemConstants.LOGIC_DELETE_VALUE).set(Tag::getUpdateBy, SecurityUtils.getUserId())
//                    .set(Tag::getUpdateTime, new Date());
//        update(null, lambdaUpdateWrapper);
//         //todo 有机会修改，不能循环new 对象
//        }
        tagMapper.deleteByIds(id,new Date(),SecurityUtils.getUserId());

        return true;


    }
}



