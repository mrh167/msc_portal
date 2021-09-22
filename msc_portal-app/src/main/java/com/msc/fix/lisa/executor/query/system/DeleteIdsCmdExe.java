package com.msc.fix.lisa.executor.query.system;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.base.YnValueEnum;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.common.CommonUtil;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.DataKey;
import com.msc.fix.lisa.dto.system.DeleteIdsCmd;
import com.msc.fix.lisa.repository.db.dbdo.SysUserDo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/10
 * Time: 16:46
 * Description: No Description
 */
@Command
public class DeleteIdsCmdExe implements CommandExecutorI<SingleResponse, DeleteIdsCmd> {


    @Autowired
    private SysUserGateWay sysUserGateWay;

    @Override
    public SingleResponse execute(DeleteIdsCmd deleteIdsCmd) {
        // 当前登录人
        String idsCmdPin = deleteIdsCmd.getPin();
        Map<Integer, String> dataKeyMap = deleteIdsCmd.getKeyList().stream().collect(Collectors.toMap(DataKey::getId, DataKey::getAccount, (k, k2) -> k2));
        if (dataKeyMap.keySet().isEmpty()) {
            throw new BusinessException("id参数为空");
        }
        List<SysUser> account = sysUserGateWay.selectByIds(dataKeyMap.keySet());


        //如果数据为空
        if (account.isEmpty()) {
            throw new BusinessException("数据为空!!!");
        }
        Map<Integer, Integer> accMap = account.stream().collect(Collectors.toMap(SysUser::getId, SysUser::getYn, (k, k2) -> k2));
        accMap.entrySet().forEach(key ->{
            if (!ObjectUtil.equal(accMap.get(key.getKey()),key.getValue())) {
                throw new BusinessException("account的数据未找到!!!!");
            }
        });

        dataKeyMap.forEach((ids,acc)->{
            SysUser adminAcc = sysUserGateWay.selectByName(acc);
            if (idsCmdPin.equals(adminAcc.getAccount()) && adminAcc != null) {
                throw new BusinessException("当前登录账号不可删除!!!!");
            }
        });
        List<SysUser> updateList = new ArrayList<>();
        Date now = new Date();
        account.forEach(id -> {
            SysUserDo applyInfoDo = new SysUserDo();
            applyInfoDo.setId(id.getId());
            applyInfoDo.setYn(YnValueEnum.noCode());
            CommonUtil.fillByDelete(now, deleteIdsCmd.getPin(), applyInfoDo);
            SysUser sysUser = BeanUtils.convert(applyInfoDo, SysUser.class);
            updateList.add(sysUser);

        });
        sysUserGateWay.updateBatchById(updateList);
        return SingleResponse.buildSuccess();
    }
}
