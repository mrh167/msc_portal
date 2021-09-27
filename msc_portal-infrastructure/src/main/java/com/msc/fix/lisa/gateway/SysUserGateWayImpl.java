package com.msc.fix.lisa.gateway;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.YnValueEnum;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.common.CommonUtil;
import com.msc.fix.lisa.common.enums.StatusEnums;
import com.msc.fix.lisa.common.utils.SnowflakeUtil;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.AddUserCmd;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.UpdateStatusCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import com.msc.fix.lisa.repository.db.dbdo.SysUserDo;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:47
 * Description: No Description
 */
@Component
public class SysUserGateWayImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserGateWay {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public PageInfo<SysUser> pageList(SysUserQry pageQry) {
        return PageHelper.startPage(pageQry.getPageNum(), pageQry.getPageSize(), true)
                .doSelectPageInfo(() -> sysUserMapper.listPage(pageQry));
    }

    @Override
    public SingleResponse updateStatus(UpdateStatusCmd cmd) {
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>();
        SysUser user = new SysUser();
        if (cmd.getAccount().equals(cmd.getPin())){
           throw new BusinessException("操作违规,不能操作当前登录账号!!!");
        }
        user.setStatus(cmd.getStatus());
        wrapper.eq("id",cmd.getId()).eq("yn", YnValueEnum.getYesCode());
        sysUserMapper.update(user,wrapper);
        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse checkAccount(String account) {
        if (StringUtils.isEmpty(account)){
            return null;
        }
        //校验账号是否重复添加或者已存在
        String existAcc =  sysUserMapper.getAccount(account);
        if (StringUtils.isNotBlank(existAcc)){
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.of(account);
    }

    @Override
    public SingleResponse addUser(AddUserCmd cmd) {
        SysUserDo user = new SysUserDo();
        user.setAccount(cmd.getAccount());
        user.setUsername(cmd.getUsername());
        String psd = passwordEncoder.encode(cmd.getPassword());
        user.setPassword(psd);
        user.setNickName(cmd.getNickName());
        user.setEmail(cmd.getEmail());
        user.setStatus(StatusEnums.USER_OPEN.getCode());
        user.setUserCode(SnowflakeUtil.genId());
        CommonUtil.fillByCreate(new Date(),cmd.getPin(),user);
        SysUser sysUser = BeanUtils.convert(user, SysUser.class);
        this.save(sysUser);
//        sysUserMapper.insert(sysUser);
        return SingleResponse.buildSuccess();
    }

    @Override
    public List<SysUser> selectByIds(Collection<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("id为空");
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn",YnValueEnum.getYesCode())
                .in("id",ids);
        return sysUserMapper.selectList(queryWrapper);
    }

    @Override
    public SingleResponse deleteBatch(List<SysUser> idList, String pin) {
        if (StringUtils.isBlank(pin)) {
            throw new BusinessException("deleteBatch operator must not be empty!");
        }
        if (CollUtil.isEmpty(idList)) {
            throw new BusinessException("deleteBatch idList must not be empty!");
        }

        List<SysUser> updateList = new ArrayList(idList.size());
        Date now = new Date();
        idList.forEach(id -> {
            SysUserDo sysUserDo = new SysUserDo();
            sysUserDo.setYn(YnValueEnum.noCode());
            CommonUtil.fillByDelete(now, pin, sysUserDo);
            SysUser sysUser = BeanUtils.convert(sysUserDo, SysUser.class);
            updateList.add(sysUser);
        });
        UpdateWrapper<SysUser> sysUserUpdateWrapper = new UpdateWrapper<>();
        SysUser applyInfoDo = null;
        for (int i = 0; i <updateList.size(); i++) {
            applyInfoDo = updateList.get(i);
        }
        sysUserUpdateWrapper.eq("id",applyInfoDo.getId()).eq("yn",YnValueEnum.getYesCode());
        sysUserMapper.update(applyInfoDo,sysUserUpdateWrapper);
        return SingleResponse.buildSuccess();
    }

    @Override
    public SysUser selectByName(String username) {
        return sysUserMapper.selectUser(username);
    }

    @Override
    public int updateByName(SysUser sysUser) {
        SysUserDo userDo = BeanUtils.convert(sysUser, SysUserDo.class);
        CommonUtil.fillByUpdate(new Date(), sysUser.getUsername(), userDo);
        UpdateWrapper<SysUser> sysUserUpdateWrapper = new UpdateWrapper<>();
        sysUserUpdateWrapper.eq("yn",YnValueEnum.getNoCode());
        SysUser user = BeanUtils.convert(userDo, SysUser.class);
        return sysUserMapper.update(user,sysUserUpdateWrapper);
    }

}
