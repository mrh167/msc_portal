package com.msc.fix.lisa.executor.system;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.base.YnValueEnum;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.common.CommonUtil;
import com.msc.fix.lisa.domain.entity.system.SysDictType;
import com.msc.fix.lisa.domain.gateway.system.SysDictTypeGateway;
import com.msc.fix.lisa.dto.DictTypeKey;
import com.msc.fix.lisa.dto.system.SysDictTypeDeleteCmd;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 14:42
 * Description: No Description
 */
@Command
public class SysDictTypeDeleteCmdExe implements CommandExecutorI<SingleResponse, SysDictTypeDeleteCmd> {

    @Autowired
    private SysDictTypeGateway sysDictTypeGateway;

    @Override
    public SingleResponse execute(SysDictTypeDeleteCmd deleteIdsCmd) {
        Map<Long, String> dictType = deleteIdsCmd.getKeyList().stream().collect(Collectors.toMap(DictTypeKey::getId, DictTypeKey::getCode, (k, k2) -> k2));
        if (dictType.keySet().isEmpty()){
            throw new BusinessException("id参数为空");
        }
        List<SysDictType> idsDicType = (List<SysDictType>) sysDictTypeGateway.listByIds(dictType.keySet());

        //如果数据为空
        if (ObjectUtil.isNull(idsDicType)) {
            throw new BusinessException("数据为空!!");
        }
        Map<Long, Integer> idsAndYn = idsDicType.stream().collect(Collectors.toMap(SysDictType::getId, SysDictType::getYn, (k, k2) -> k2));
        idsAndYn.entrySet().forEach(key ->{
            if (!ObjectUtil.equal(idsAndYn.get(key.getKey()),key.getValue())) {
                throw new BusinessException("字典类型的数据未找到!!!!");
            }
        });

        ArrayList<SysDictType> list = new ArrayList<>();
        Date now = new Date();
        idsDicType.forEach(ids -> {
            SysDictType type = new SysDictType();
            type.setId(ids.getId());
            type.setYn(YnValueEnum.getNoCode());
            CommonUtil.fillByDelete(now,deleteIdsCmd.getPin(),type);
            list.add(type);
        });

        sysDictTypeGateway.updateBatchById(list);

        return SingleResponse.buildSuccess();
    }
}
