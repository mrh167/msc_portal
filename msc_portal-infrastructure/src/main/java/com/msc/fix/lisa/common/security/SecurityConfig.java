package com.msc.fix.lisa.common.security;


import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysLoginGateway;
import com.msc.fix.lisa.filter.CustomFilter;
import com.msc.fix.lisa.filter.CustomUrlDecisionManager;
import com.msc.fix.lisa.filter.JwtAuthenticationTokenFilter;
import com.msc.fix.lisa.handler.RestAccessDeniedHandler;
import com.msc.fix.lisa.handler.RestAuthorizationEntryPoint;
import com.msc.fix.lisa.handler.UserLoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author by cdh
 * @description:
 * @Date: Created in 18:39 on 2021/4/18
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private IAdminService adminService;


    @Autowired
    private SysLoginGateway sysLoginGateway;

    @Autowired
    private RestAuthorizationEntryPoint authorizationEntryPoint;
    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/api/login",
                "/api/css/**",
                "/api/js/**",
                "/api/index.html",
                "/api/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/api/captcha",
                "/ws/**",
                "/api/ac/**",
                "/api/db/**",
                "/api/mbp-generator/**",
                "/api/output-file-info/**",
                "/api/sql/**",
                "/api/template/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用JWT，不需要csrf
        http.authorizeRequests()
                .antMatchers("/api/login")

                .permitAll()
                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and().cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 禁用缓存
                .headers()
                .cacheControl()
        ;
        // 添加jwt登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authorizationEntryPoint);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            SysUser sysUser = sysLoginGateway.getAdminByUserName(username);
            if (null != sysUser){
                sysUser.setRoleList(sysLoginGateway.getRoles(sysUser.getId()));
                log.info("账号信息为:=> {}",sysUser.toString());
                return sysUser;
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public UserLoginSuccessHandler userLoginSuccessHandler(){
        return new UserLoginSuccessHandler();
    }
}
