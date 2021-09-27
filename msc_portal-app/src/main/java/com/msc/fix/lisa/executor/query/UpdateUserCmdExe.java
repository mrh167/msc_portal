package com.msc.fix.lisa.executor.query;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.UpdateUserCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import com.msc.fix.lisa.repository.db.dbdo.SysUserDo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/27
 * Time: 11:09
 * Description: No Description
 */
@Command
public class UpdateUserCmdExe implements CommandExecutorI<SingleResponse<SysUserCo>, UpdateUserCmd> {
    @Autowired
    private SysUserGateWay sysUserGateWay;


    @Override
    public SingleResponse<SysUserCo> execute(UpdateUserCmd cmd) {
        Integer id = cmd.getId();
        String account = cmd.getAccount();
        if (id != null && StringUtils.isNotBlank(account)){
            throw new BusinessException("参数为空!!!");
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id)
                .eq("account",account);

        SysUser sysUser = sysUserGateWay.getOne(wrapper);
        SysUserCo sysUserCo = BeanUtils.convert(sysUser, SysUserCo.class);
        return SingleResponse.of(sysUserCo);
    }
}
