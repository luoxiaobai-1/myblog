package my.blog.domain.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class Rolevo {
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

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
    private Date updateTime;
    private String delFlag;
    private Date createTime;

}
