package my.controller;

import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.menulistvo;
import my.blog.domain.entity.Menu;
import my.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class Menuweb {

    @Autowired
    MenuService menuService;
    @GetMapping("/list")
    public ResponseResult Menulist(String menuName ,String status)

    {
      List<menulistvo>  menulistvos=menuService.GetMenuList(menuName,status);
      return ResponseResult.success(menulistvos, HttpCodeEnum.SUCCESS);

    }

    @PostMapping
    public ResponseResult AddMenu(@RequestBody Menu menu)
    {

      boolean flag=  menuService.addmenu(menu);
      return flag?ResponseResult.success():ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseResult menu( @PathVariable long id)
    {
        menulistvo menu = menuService.Getmenu(id);
return ResponseResult.success(menu,HttpCodeEnum.SUCCESS);

    }
@PutMapping
  public ResponseResult updatemenu(@RequestBody Menu menu)
{
    menuService.updatemenu(menu);
    return ResponseResult.success();
}
@DeleteMapping("{menuid}")
    public  ResponseResult Deletememu(@PathVariable long menuid)
{

    menuService.DeleteMenu(menuid);
    return ResponseResult.success();
}
    @GetMapping("/treeselect")
    public ResponseResult treeselect() {


return ResponseResult.success(menuService.getmenutree(),HttpCodeEnum.SUCCESS);
    }
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {

        return ResponseResult.success( menuService.getMenuTreeSelect(roleId),HttpCodeEnum.SUCCESS);
    }
}
