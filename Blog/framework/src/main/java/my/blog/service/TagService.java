package my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Dto.TagListDto;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Tagvo;
import my.blog.domain.entity.Tag;

import java.util.List;

/**
* @author 14574
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2023-05-01 20:55:02
*/
public interface TagService extends IService<Tag> {
    public Pagevo GettagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    boolean Addtag(TagListDto tagListDto);

    boolean Deltag(long id []);

    Tagvo Gettag(long id);

    boolean updatetag(Tagvo tagvo);

    List<Tagvo> getalltag();
}
