package my.blog.domain.Vo;

import lombok.Data;

@Data
public class Categoryvo {
    private Long id;
    //分类名
    private String name;
    private  String description;
    private String status;

}
