package com.msc.fix.lisa;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Starter
 *
 * COLA framework initialization is configured in {@link com.msc.fix.lisa.config.ColaConfig}
 *
 * @author Frank Zhang
 */

//不使用   @SpringBootApplication 避免被宿主机的自动配置所干扰
@SpringBootApplication(scanBasePackages = {"com.msc.fix.lisa","com.alibaba.cola","com.msc.fix.lisa.mybatiscn"})
//@ComponentScan(basePackages = {"com.msc.fix.lisa.mybatiscn"})
@MapperScan("com.msc.fix.lisa.repository")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
//    private static GeneratorConfig generatorConfig;
//
//
//    public static void main(String[] args) {
//        GeneratorConfig config = GeneratorConfigruation.generatorConfig();
//        Application.run(config);
//    }
//
//    public static void run(GeneratorConfig generatorConfigs){
//        if (Strings.isNullOrEmpty(generatorConfigs.getJdbcUrl())) {
//            throw new IllegalArgumentException("jdbcUrl必须要设置");
//        }
//        Application.generatorConfig = generatorConfigs;
//        Map<String, Object> props = Maps.newHashMap();
//        props.put("spring.resources.static-locations", "classpath:/generator-ui/");
//        new SpringApplicationBuilder()
//                .properties(props)
//                .sources(Application.class)
//                .run(new String[0]);
//    }
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerConfig(GeneratorConfig config) {
//        return factory -> {
//            if (config.getPort() != null) {
//                factory.setPort(Application.generatorConfig.getPort());
//            } else {
//                factory.setPort(8068);
//            }
//            factory.setContextPath("");
//        };
//    }

//    @Bean
//    public GeneratorConfig generatorConfig() {
//        return Application.generatorConfig;
//    }

