package com.msc.fix.lisa.executor.query;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.common.CommonUtil;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.UpdateUserCmd;
import com.msc.fix.lisa.repository.db.dbdo.SysUserDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/27
 * Time: 11:09
 * Description: No Description
 */
@Command
public class UpdateUserCmdExe implements CommandExecutorI<SingleResponse, UpdateUserCmd> {
    @Autowired
    private SysUserGateWay sysUserGateWay;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public SingleResponse execute(UpdateUserCmd cmd) {
        if (cmd == null) {
            throw new BusinessException("参数为空!!!");
        }
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",cmd.getId());
        SysUser user = new SysUser();
        user.setAccount(cmd.getAccount());
        user.setPassword(passwordEncoder.encode(cmd.getPassword()));
        user.setUsername(cmd.getUsername());
        user.setNickName(cmd.getNickName());
        user.setEmail(cmd.getEmail());
        user.setPhone(cmd.getPhone());
        SysUserDo userDo = BeanUtils.convert(user, SysUserDo.class);
        CommonUtil.fillByUpdate(new Date(),cmd.getPin(),userDo);
        SysUser userEdit = BeanUtils.convert(userDo, SysUser.class);
        sysUserGateWay.update(userEdit,wrapper);
        return SingleResponse.buildSuccess();
    }
}
