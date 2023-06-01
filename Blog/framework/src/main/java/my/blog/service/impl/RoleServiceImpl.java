package my.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.blog.Constants.SystemConstants;
import my.blog.domain.Dto.RoleAddDto;
import my.blog.domain.Dto.RoleDto;
import my.blog.domain.Dto.RoleListDto;
import my.blog.domain.Dto.RolemessDto;
import my.blog.domain.Result.HttpCodeEnum;
import my.blog.domain.Result.ResponseResult;
import my.blog.domain.Vo.ArticleListvo;
import my.blog.domain.Vo.Pagevo;
import my.blog.domain.Vo.Rolevo;
import my.blog.domain.entity.Article;
import my.blog.domain.entity.Role;
import my.blog.domain.entity.RoleMenu;
import my.blog.exception.SystemException;
import my.blog.mapper.RoleMapper;
import my.blog.mapper.RoleMenuMapper;
import my.blog.service.RoleMenuService;
import my.blog.service.RoleService;
import my.blog.utils.BeanCopy;
import my.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 14574
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-05-01 22:15:04
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {
@Autowired
RoleMapper roleMapper;
@Autowired
RoleMenuService  roleMenuService;
    @Override
    public List<String> Getrolekey(long id) {
        List<String>rolekeys=new ArrayList<>();
        if (id==1L)
        {
            rolekeys.add("admin");
            return rolekeys;
        }

return roleMapper.selectRoleKeyById(id);
    }

    @Override
    public Pagevo GetRoleList(RoleListDto roleListDto) {

        LambdaQueryWrapper <Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()),Role::getRoleName,roleListDto.getRoleName());
       queryWrapper.eq(StringUtils.hasText(roleListDto.getStatus()),Role::getStatus,roleListDto.getStatus());
       queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(roleListDto.getPageNum(),roleListDto.getPageSize());
        page(page,queryWrapper);
        List<Rolevo> rolevos=  BeanCopy.copyBeanList(page.getRecords(),Rolevo.class);
        Pagevo pagevo=new Pagevo(rolevos,page.getTotal());
        return pagevo;
    }

    @Override
    public boolean setstuatus(String roleId, String status) {
        LambdaUpdateWrapper<Role> updateWrapper=new LambdaUpdateWrapper<>();
       updateWrapper.eq(Role::getId,roleId).
               set(Role::getStatus,status).
                set(Role::getUpdateBy, SecurityUtils.getUserId()).
                set(Role::getUpdateTime,new Date());

    return    roleMapper.update(null, updateWrapper) >0;

    }

    @Override
    @Transactional
    public boolean AddRole(RoleDto roleDto) {
        Role role=new Role();
        role.setRoleKey(roleDto.getRoleKey());
        role.setRemark(roleDto.getRemark());
        role.setRoleSort(roleDto.getRoleSort());
        role.setDelFlag(SystemConstants.LOGIC_NOT_DELETE_VALUE);
        role.setCreateBy(SecurityUtils.getUserId());
        role.setCreateTime(new Date());
        role.setRoleName(roleDto.getRoleName());
        role.setStatus(roleDto.getStatus());
        boolean save = save(role);
        List<RoleMenu> collect = roleDto.getMenuIds().stream().map(menuid -> new RoleMenu(role.getId(), menuid)).collect(Collectors.toList());

        boolean b = roleMenuService.saveBatch(collect);
        if (save&&b)
            return true;
        else
            return false;

    }

    @Override
    public RolemessDto GetRole(long id) {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId,id);
        Role one = getOne(queryWrapper);
     return    BeanCopy.copyBean(one, RolemessDto.class);

    }
@Transactional
    @Override
    public boolean AlterRole(RoleAddDto roleDto) {
        Role role = BeanCopy.copyBean(roleDto, Role.class);
        role.setUpdateTime(new Date());
        role.setUpdateBy(SecurityUtils.getUserId());
        LambdaQueryWrapper<RoleMenu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,roleDto.getId());
        roleMenuService.remove(queryWrapper);
       boolean save= updateById(role);
    List<RoleMenu> collect = roleDto.getMenuIds().stream().map(menuid -> new RoleMenu(role.getId(), menuid)).collect(Collectors.toList());

    boolean b = roleMenuService.saveBatch(collect);
        return  save&&b;




    }
@Transactional
    @Override
    public void DeleteRole(long[] id) {

//        boolean flag;
//        for (long l : id) {
//            LambdaUpdateWrapper<Role> updateWrapper=new LambdaUpdateWrapper<>();
//            updateWrapper.eq(Role::getId,l).set(Role::getDelFlag,SystemConstants.LOGIC_DELETE_VALUE);
//             flag=   update(null,updateWrapper);
//             if (!flag)
//             {
//                 throw  new SystemException(HttpCodeEnum.SYSTEM_ERROR);
//             }
//
//        }
roleMapper.deleteByIds(id,new Date(),SecurityUtils.getUserId());

    }

    @Override
    public List<Rolevo> roles() {
        LambdaQueryWrapper <Role> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        return BeanCopy.copyBeanList(list(lambdaQueryWrapper),Rolevo.class);
    }


}




