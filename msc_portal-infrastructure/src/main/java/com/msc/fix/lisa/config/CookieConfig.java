package com.msc.fix.lisa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/13
 * Time: 19:50
 * Description: No Description
 */
@Configuration
public class CookieConfig {
    @Bean
    public CookieSerializer httpSessionIdResolver(){
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("token");
        cookieSerializer.setUseHttpOnlyCookie(false);
        cookieSerializer.setSameSite(null);
        return cookieSerializer;
    }
}
