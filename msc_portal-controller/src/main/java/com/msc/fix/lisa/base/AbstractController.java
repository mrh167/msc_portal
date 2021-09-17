/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.base;


import com.msc.fix.lisa.domain.entity.system.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
public abstract class AbstractController {

    public String getPin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser principal =(SysUser) authentication.getPrincipal();

        log.info("用户信息为 => {}", principal.getUsername());
        return principal.getUsername();
    }
}
