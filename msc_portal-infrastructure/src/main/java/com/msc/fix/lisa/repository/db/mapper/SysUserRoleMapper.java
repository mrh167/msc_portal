package com.msc.fix.lisa.repository.db.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.domain.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author 马瑞华
 * @since 2021-08-31
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<SysUserRole> selectListByUser(@Param("account") String account);
}
