package com.msc.fix.lisa.gateway;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.YnValueEnum;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.common.CommonUtil;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.entity.system.SysDictType;
import com.msc.fix.lisa.domain.gateway.system.SysDictTypeGateway;
import com.msc.fix.lisa.dto.system.SysDictTypeAddCmd;
import com.msc.fix.lisa.dto.system.SysDictTypeQry;
import com.msc.fix.lisa.enums.SysDictTypeStatusEnums;
import com.msc.fix.lisa.repository.db.mapper.SysDictTypeMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 11:30
 * Description: No Description
 */
@Component
public class SysDictTypeGatewayImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeGateway {



    @Override
    public PageInfo<SysDictType> pageList(SysDictTypeQry pageQry) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(pageQry)) {
            //根据字典类型名称模糊查询
            if (pageQry.getName() != null) {
                wrapper.like(SysDictType::getName,pageQry.getName());
            }
            //根据字典类型编码模糊查询
            if (pageQry.getCode() != null) {
                wrapper.like(SysDictType::getCode,pageQry.getCode());
            }
        }

        wrapper.eq(SysDictType::getYn, YnValueEnum.getYesCode());

        wrapper.orderByDesc(SysDictType::getCreateTime);

        return PageHelper.startPage(pageQry.getPageNum(), pageQry.getPageSize(),true)
                .doSelectPageInfo(() -> this.list(wrapper));
    }

    @Override
    public SingleResponse add(SysDictTypeAddCmd cmd) {
        // 校验添加参数是否正确
        checkParams(cmd,false);
        SysDictType sysDictType = BeanUtils.convert(cmd, SysDictType.class);
        sysDictType.setStatus(SysDictTypeStatusEnums.NORMAL.getCode());
        CommonUtil.fillByCreate(new Date(),cmd.getPin(),sysDictType);
        this.save(sysDictType);
        return SingleResponse.buildSuccess();
    }


    /**
     * 校验类型参数
     * @param cmd
     * @param isExcludeSelf
     */
    private void checkParams(SysDictTypeAddCmd cmd,boolean isExcludeSelf) {
        Integer id = cmd.getId();
        String name = cmd.getName();
        String code = cmd.getCode();

        //构建带name和code的查询条件
        LambdaQueryWrapper<SysDictType> queryWrapperByName = new LambdaQueryWrapper<>();
        queryWrapperByName.eq(SysDictType::getName,name)
                .ne(SysDictType::getStatus, SysDictTypeStatusEnums.DELETE.getCode());

        LambdaQueryWrapper<SysDictType> queryWrapperByCode = new LambdaQueryWrapper<>();
        queryWrapperByCode.eq(SysDictType::getCode,code)
                .ne(SysDictType::getStatus,SysDictTypeStatusEnums.DELETE.getCode());

        //如果排除自己, 则增加查询条件主键id不等于本条id
        if (isExcludeSelf) {
            queryWrapperByName.ne(SysDictType::getId,id);
            queryWrapperByCode.ne(SysDictType::getId,id);
        }

        //如果存在重复的记录,抛出异常,直接返回前端
        int countByName = this.count(queryWrapperByName);
        int countByCode = this.count(queryWrapperByCode);

        if (countByName >= 1) {
            throw new BusinessException("字典类型名称重复");
        }

        if (countByCode >= 1){
            throw new BusinessException("字典类型编码重复");
        }
    }
}
