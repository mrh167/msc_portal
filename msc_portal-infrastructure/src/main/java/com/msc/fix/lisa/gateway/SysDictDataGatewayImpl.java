package com.msc.fix.lisa.gateway;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.YnValueEnum;
import com.msc.fix.lisa.domain.entity.system.SysDictData;
import com.msc.fix.lisa.domain.gateway.system.SysDictDataGateway;
import com.msc.fix.lisa.dto.system.SysDictDataQry;
import com.msc.fix.lisa.repository.db.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/29
 * Time: 16:32
 * Description: No Description
 */
@Component
public class SysDictDataGatewayImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataGateway {

    @Autowired
    private SysDictDataMapper sysDictDataMapper;


    @Override
    public PageInfo<SysDictData> pageList(SysDictDataQry pageQry) {

        // 构造条件
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(pageQry)) {
            // 根据字典类型查询
            if (pageQry.getTypeId() != null) {
                queryWrapper.eq(SysDictData::getTypeId,pageQry.getTypeId());
            }
            // 根据字典值得编码模糊查询
            if (pageQry.getCode() != null){
                queryWrapper.like(SysDictData::getCode, pageQry.getCode());
            }
            // 根据字典值的内容模糊查询
            if (pageQry.getValue() != null) {
                queryWrapper.like(SysDictData::getValue,pageQry.getValue());
            }
        }
        queryWrapper.eq(SysDictData::getTypeId,pageQry.getId());
        // 查询yn =1的数据
        queryWrapper.eq(SysDictData::getYn, YnValueEnum.getYesCode());
        // 升序排序
        queryWrapper.orderByAsc(SysDictData::getSort);

        return PageHelper.startPage(pageQry.getPageNum(), pageQry.getPageSize(),true)
                .doSelectPageInfo(() -> this.list(queryWrapper));
    }
}
