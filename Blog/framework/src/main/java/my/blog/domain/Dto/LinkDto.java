package my.blog.domain.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LinkDto {
    @NotEmpty
    private  String name;
    @NotEmpty
    private  String status;
    @NotEmpty
    private String address;

    private String logo;
    private String description;

}
