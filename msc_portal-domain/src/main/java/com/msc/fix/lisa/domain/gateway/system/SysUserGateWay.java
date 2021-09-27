package com.msc.fix.lisa.domain.gateway.system;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.entity.system.User;
import com.msc.fix.lisa.dto.system.AddUserCmd;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.UpdateStatusCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:40
 * Description: No Description
 */
public interface SysUserGateWay extends IService<SysUser> {

    PageInfo<SysUser> pageList(SysUserQry pageQry);

    SingleResponse updateStatus(UpdateStatusCmd cmd);

//    SingleResponse checkAccount(String account);

    SingleResponse addUser(AddUserCmd cmd);

    List<SysUser> selectByIds(Collection<Integer> keySet);

    SingleResponse deleteBatch(List<SysUser> account, String pin);


    SysUser selectByAccount(String username);

    User selectByName(String username);

    int updateByName(SysUser sysUser);

}
