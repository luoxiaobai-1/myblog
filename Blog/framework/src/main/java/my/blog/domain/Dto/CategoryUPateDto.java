package my.blog.domain.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryUPateDto {
    @NotEmpty
    private  String name;
    @NotEmpty
    private  String status;
    @NotEmpty
    private  String description;
    @NotNull
    private  long id;
}
