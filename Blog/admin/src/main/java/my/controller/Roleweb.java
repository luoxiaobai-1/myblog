package my.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import my.blog.domain.Dto.*;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Rolevo;
import my.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
@Slf4j
public class Roleweb {
    @Autowired
    RoleService roleService;


    @GetMapping("/list")
    public ResponseResult RoleList(RoleListDto roleListDto)
    {
  Pagevo  pagevo= roleService.GetRoleList(roleListDto);

return ResponseResult.success(pagevo, HttpCodeEnum.SUCCESS);
    }
    @PutMapping("/changeStatus")
    public ResponseResult Changestatus(@RequestBody RoleStatusDto statusDto){

    boolean flag=       roleService.setstuatus(statusDto.getRoleId(),statusDto.getStatus());
return flag ?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);

    }
@PostMapping
    public  ResponseResult Addrole(@RequestBody @Validated RoleDto roleDto)

{
    boolean b = roleService.AddRole(roleDto);
    return b?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);

}

@GetMapping("/{id}")
    public  ResponseResult GetRole(@PathVariable  long id)
{
  RolemessDto rolemessDto= roleService.GetRole(id);
  return ResponseResult.success(rolemessDto,HttpCodeEnum.SUCCESS);
}

@PutMapping
public  ResponseResult updateRole(@RequestBody RoleAddDto roleDto)
{
    boolean b = roleService.AlterRole(roleDto);

    return b?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
}

@DeleteMapping("/{id}")
    public ResponseResult DeleteRole(@PathVariable long [] id )
{
  roleService.DeleteRole(id);
    return ResponseResult.success();

}
 @GetMapping("/listAllRole")
    public ResponseResult Roles( )
    {
     List<Rolevo>  rolevos= roleService.roles();
        return ResponseResult.success(rolevos,HttpCodeEnum.SUCCESS);

    }

}
