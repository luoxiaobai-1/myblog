package my.blog.domain.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class RolemessDto {
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;
    private String status;
    private String remark;
    private Long updateBy;

    /**
     * 更新时间
     */

    private Date updateTime;

}
