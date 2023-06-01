package my.blog.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Menutreevo {


    private  long id;
    private String label;
    private Long parentId;
    private List<Menutreevo> children;



}
