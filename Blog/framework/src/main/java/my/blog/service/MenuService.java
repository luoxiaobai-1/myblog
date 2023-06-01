package my.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.*;
import my.blog.domain.entity.Menu;

import java.util.List;

/**
* @author 14574
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-05-01 22:06:21
*/
public interface MenuService extends IService<Menu> {

    public List<String> getperms(long id);


    routersvo GetrouterTree(Long id);

    List<menulistvo> GetMenuList(String menuName, String status);

    boolean addmenu(Menu menu);

  menulistvo Getmenu(long id);

    void updatemenu(Menu menu);

    void DeleteMenu(long menuid);
    List<Menutreevo> getmenutree();
   RoleMenuTreeSelectVo getMenuTreeSelect(long id);
}
