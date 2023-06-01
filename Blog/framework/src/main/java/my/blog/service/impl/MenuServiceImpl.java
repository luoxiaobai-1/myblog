package my.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.blog.Constants.SystemConstants;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.*;
import my.blog.domain.entity.Menu;
import my.blog.domain.entity.Tag;
import my.blog.exception.SystemException;
import my.blog.mapper.MenuMapper;
import my.blog.service.MenuService;
import my.blog.utils.BeanCopy;
import my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 14574
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-05-01 22:06:21
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {
@Autowired
MenuMapper menuMapper;
    @Override
    public List<String> getperms(long id) {
       if (id==1)
       {
           LambdaQueryWrapper<Menu> lambdaQueryWrapper=new LambdaQueryWrapper<>();
           lambdaQueryWrapper.in(Menu::getMenuType, SystemConstants.Menu,SystemConstants.BUTTON);
           lambdaQueryWrapper.eq(Menu::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
           List<Menu> list = list(lambdaQueryWrapper);
           List<String> Perms = list.stream().map(Menu::getPerms).collect(Collectors.toList());
           return Perms;
       }

return menuMapper.selectPermsbyuserid(id);
    }


    @Override
    public routersvo GetrouterTree(Long id) {

        List <Menuvo> menuvos;
if (SecurityUtils.isAdmin())
{
 menuvos= BeanCopy.copyBeanList(menuMapper.selectallrouters(), Menuvo.class);
 menuvos=buliderenutree(menuvos,0);

}
else {

    menuvos= BeanCopy.copyBeanList(menuMapper.selectallroutersbyid(id), Menuvo.class);
    menuvos=buliderenutree(menuvos,0);
}

routersvo routersvo=new routersvo();
routersvo.setMenus(menuvos);

        return routersvo;
    }

    @Override
    public List<menulistvo> GetMenuList(String menuName, String status) {
        LambdaQueryWrapper <Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        queryWrapper.like(StringUtils.hasText(status),Menu::getStatus,status);
        queryWrapper.orderByAsc(Menu::getOrderNum).orderByAsc(Menu::getParentId);
        List<menulistvo> menulistvos = BeanCopy.copyBeanList(list(queryWrapper), menulistvo.class);

return menulistvos;
    }

    @Override
    public boolean addmenu(Menu menu) {
        menu.setCreateBy(SecurityUtils.getUserId());
        menu.setCreateTime(new Date());
        return  save(menu);
    }

    @Override
    public menulistvo Getmenu(long id) {
        LambdaQueryWrapper <Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId,id);
        Menu one = getOne(queryWrapper);
        return BeanCopy.copyBean(one, menulistvo.class);
    }

    @Override
    public void updatemenu(Menu menu) {
        if (Objects.equals(menu.getId(), menu.getParentId()))
        {
            throw  new SystemException(HttpCodeEnum.PARENT_MENU_ERROR);

        }
        menu.setUpdateBy(SecurityUtils.getUserId());
        menu.setUpdateTime(new Date());
       updateById(menu);

    }

    @Override
    public void DeleteMenu(long menuid) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuid);
        long count = count(queryWrapper);

        if (count!=0){
            throw new SystemException(HttpCodeEnum.HAS_CHILD_MENU);
        }
        LambdaUpdateWrapper<Menu> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Menu::getId, menuid).set(Menu::getDelFlag, SystemConstants.LOGIC_DELETE_VALUE).set(Menu::getUpdateBy, SecurityUtils.getUserId())
                .set(Menu::getUpdateTime, new Date());
        update(null, lambdaUpdateWrapper);

    }

    @Override
    public  List<Menutreevo> getmenutree() {
        LambdaQueryWrapper <Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByAsc(Menu::getOrderNum).orderByAsc(Menu::getParentId);
        List<Menu> list = list( queryWrapper);
        return buildMenuSelectTree(list);

    }

    @Override
    public RoleMenuTreeSelectVo getMenuTreeSelect(long id) {
        LambdaQueryWrapper <Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByAsc(Menu::getOrderNum).orderByAsc(Menu::getParentId);
        List<Menu> list = list( queryWrapper);
        List<Long> checkedKeys = selectMenuListByRoleId(id);
        List<Menutreevo> menutreevos=buildMenuSelectTree(list);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menutreevos);
        return vo;


    }


    private  List<Menuvo> buliderenutree(List<Menuvo> menuvos,long parentid) {

        List<Menuvo> collect = menuvos.stream().
                filter(menuvo -> menuvo.getParentId().equals(parentid)).
                map(menuvo -> menuvo.setChildren(getchidren(menuvo, menuvos))).
                collect(Collectors.toList());

        return collect;
    }

    private List<Menuvo> getchidren(Menuvo menuvo,List<Menuvo> menuvos)
    {
        List<Menuvo> collect = menuvos.
                stream().
                filter(m -> m.getParentId().equals(menuvo.getId())).
                map(m -> m.setChildren(getchidren(m,menuvos))).
                collect(Collectors.toList());

        return collect;
    }
    public static List<Menutreevo> buildMenuSelectTree(List<Menu> menus) {

        List<Menutreevo> MenuTreeVos = menus.stream()
                .map(m ->
                  new Menutreevo(m.getId(), m.getMenuName(), m.getParentId(), null))
                .collect(Collectors.toList());
        List<Menutreevo> options = MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .map(o -> o.setChildren(getChildList(MenuTreeVos, o)))
                .collect(Collectors.toList());


        return options;
    }
    private static List<Menutreevo> getChildList(List<Menutreevo> list, Menutreevo option) {
        List<Menutreevo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(),option.getId()))
                .map(o -> o.setChildren(getChildList(list,o)))
                .collect(Collectors.toList());

        return options;

    }
    public List<Long> selectMenuListByRoleId(Long roleId) {

        return getBaseMapper().selectMenuListByRoleId(roleId);
    }
}




