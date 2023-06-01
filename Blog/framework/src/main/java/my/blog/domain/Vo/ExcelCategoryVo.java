package my.blog.domain.Vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelCategoryVo {
    @ExcelProperty("分类名")
    private String name;
    @ExcelProperty("描述")
    private  String description;
    @ExcelProperty("状态：1启用 2禁用")
    private String status;


}
