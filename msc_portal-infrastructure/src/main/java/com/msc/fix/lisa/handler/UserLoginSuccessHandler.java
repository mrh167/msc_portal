package com.msc.fix.lisa.handler;

import com.msc.fix.lisa.common.security.JwtTokenUtil;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/15
 * Time: 15:27
 * Description: 登录成功处理器
 */
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //登录成功后获取用户信息
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(sysUser);
        response.addHeader(tokenHeader,tokenHead+" "+token);
    }
}
