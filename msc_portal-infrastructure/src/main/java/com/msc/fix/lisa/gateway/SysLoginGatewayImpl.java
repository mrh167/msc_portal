package com.msc.fix.lisa.gateway;

import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.entity.system.SysUserRole;
import com.msc.fix.lisa.domain.entity.system.User;
import com.msc.fix.lisa.domain.gateway.system.SysLoginGateway;
import com.msc.fix.lisa.repository.db.mapper.SysRoleMapper;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 16:43
 * Description: No Description
 */
@Component
public class SysLoginGatewayImpl implements SysLoginGateway {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public User getAdminByUserName(String username) {
        return sysUserMapper.selectUser(username);
    }

    @Override
    public List<SysUserRole> getRoles(String account) {
        return sysRoleMapper.getRoles(account);
    }
}
