package my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Dto.LinkDto;
import my.blog.domain.Dto.LinkListDto;
import my.blog.domain.Dto.LinkStatusDto;
import my.blog.domain.Vo.Linkvo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Tagvo;
import my.blog.domain.entity.Link;

import java.util.List;

/**
* @author 14574
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-04-26 20:24:13
*/
public interface LinkService extends IService<Link> {


    List<Linkvo> GetallLink();


    Pagevo getLinks(LinkListDto linkListDto);

    boolean Addlink(LinkDto linkDto);

    Linkvo GetLink(long id);

    boolean updateLink(Linkvo linkvo);

    boolean changeStatus(LinkStatusDto statusDto);

    boolean Deleteuser(long[] id);
}
