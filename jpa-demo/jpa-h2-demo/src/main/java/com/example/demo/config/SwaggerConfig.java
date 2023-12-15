package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger参数配置(业务实现)
 *
 * @author feiyong
 * @date 2017/12/31 11:08.
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name="swagger.enable",havingValue ="true")
public class SwaggerConfig {

    @Value("${app.api.url}")
    private String url;

    @Value("${app.api.version}")
    private String version;

    @Bean
    public Docket createRestApi() {

        //添加head参数start
        List<Parameter> pars = new ArrayList<Parameter>();

        ParameterBuilder userId = new ParameterBuilder();
        ParameterBuilder userName = new ParameterBuilder();
        ParameterBuilder orgId = new ParameterBuilder();

        // 应用id，added by msh 20190924
        ParameterBuilder appId = new ParameterBuilder();
        // 默认person表中的张超用户
        userId.name("userId").description("userId").modelRef(new ModelRef("int")).parameterType("header").required(true).defaultValue("51705").build();
        userName.name("userName").description("userName").modelRef(new ModelRef("string")).parameterType("header").defaultValue("System").required(true).build();
        // added by msh 20190904 header增加组织id的输入 64 对应诺基亚智慧园区
        orgId.name("orgId").description("orgId").modelRef(new ModelRef("int")).parameterType("header").required(true).defaultValue("125").build();

        // 开发环境： 11 对应Nokia智慧园区管理平台  12对应Nokia智慧园区移动端  17对应诺基亚智慧园区-企业管理平台
        appId.name("appId").description("appId").modelRef(new ModelRef("int")).parameterType("header").required(true).defaultValue("45").build();
        //添加head参数end
        //获取ip地址,解决本地开发者无法使用swagger的问题
        String os = System.getProperty("os.name");
        url = url.replaceAll("http://", "");
        if (os.toLowerCase().startsWith("win")) {
            //一般开发人员本地是windows系统，swagger地址就应配置为localhost
            url = "localhost" + ":" + url.split(":")[1];
        }
        pars.add(userId.build());
        pars.add(userName.build());
        pars.add(orgId.build());
        pars.add(appId.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .host(url)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TEM订单管理系统")
                .description("为TEM订单管理系统提供相关接口")
                .termsOfServiceUrl(url)
                .version(version)
                .build();
    }
}
