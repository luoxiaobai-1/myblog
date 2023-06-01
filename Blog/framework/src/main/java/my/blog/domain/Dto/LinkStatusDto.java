package my.blog.domain.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LinkStatusDto {
    @NotNull
    private long id;
   @NotEmpty
    private String status;
}
