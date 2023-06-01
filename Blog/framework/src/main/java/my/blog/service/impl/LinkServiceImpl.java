package my.blog.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import my.blog.Constants.SystemConstants;
import my.blog.domain.Dto.LinkDto;
import my.blog.domain.Dto.LinkListDto;
import my.blog.domain.Dto.LinkStatusDto;
import my.blog.domain.Vo.Categoryvo;
import my.blog.domain.Vo.Linkvo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Tagvo;
import my.blog.domain.entity.Category;
import my.blog.domain.entity.Link;
import my.blog.domain.entity.Role;
import my.blog.mapper.LinkMapper;
import my.blog.service.LinkService;
import my.blog.utils.BeanCopy;
import my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
* @author 14574
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2023-04-26 20:24:13
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService {
@Autowired
LinkMapper linkMapper;
    @Override
    public List<Linkvo>GetallLink() {
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);
       List<Link>links= list(queryWrapper);

       return BeanCopy.copyBeanList(links, Linkvo.class);

    }

    @Override
    public Pagevo getLinks(LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkListDto.getName()), Link::getName, linkListDto.getName());
        queryWrapper.like(StringUtils.hasText(linkListDto.getStatus()), Link::getStatus, linkListDto.getStatus());
        Page<Link> page = new Page<>();
        page.setCurrent(linkListDto.getPageNum());
        page.setSize(linkListDto.getPageSize());
        page(page, queryWrapper);
        List<Linkvo> linkvos = BeanCopy.copyBeanList(page.getRecords(), Linkvo.class);
        return new Pagevo(linkvos, page.getTotal());
    }

    @Override
    public boolean Addlink(LinkDto linkDto) {
        Link link = BeanCopy.copyBean(linkDto, Link.class);
        link.setCreateBy(SecurityUtils.getUserId());
        link.setCreateTime(new Date());
        return  save(link);
    }

    @Override
    public Linkvo GetLink(long id) {
        Link byId = getById(id);
        return  BeanCopy.copyBean(byId, Linkvo.class);
    }

    @Override
    public boolean updateLink(Linkvo linkvo) {
       Link link = BeanCopy.copyBean(linkvo, Link.class);
       link.setUpdateBy(SecurityUtils.getUserId());
       link.setUpdateTime(new Date());
      return   updateById(link);
    }

    @Override
    public boolean changeStatus(LinkStatusDto statusDto) {
        LambdaUpdateWrapper<Link> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Link::getId,statusDto.getId()).
                set(Link::getStatus,statusDto.getStatus()).
                set(Link::getUpdateBy, SecurityUtils.getUserId()).
                set(Link::getUpdateTime,new Date());

        return   update(updateWrapper);

    }

    @Override
    public boolean Deleteuser(long[] id) {
        return linkMapper.deleteByIds(id,new Date(),SecurityUtils.getUserId());
    }


}




