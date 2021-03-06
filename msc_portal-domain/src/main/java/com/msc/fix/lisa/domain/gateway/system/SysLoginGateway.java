package com.msc.fix.lisa.domain.gateway.system;

import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.entity.system.SysUserRole;
import com.msc.fix.lisa.domain.entity.system.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 16:14
 * Description: No Description
 */
public interface SysLoginGateway {

    User getAdminByUserName(String username);

    List<SysUserRole> getRoles(String account);
}
