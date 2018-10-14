/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo;

import com.jancur.sbdemo.domain.model.ServiceContext;
import com.jancur.sbdemo.infrastructure.filter.ExtractHeadersFilter;
import com.jancur.sbdemo.infrastructure.filter.HeaderDetail;
import com.jancur.sbdemo.infrastructure.filter.HeaderDetailImpl;

import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.request.RequestContextListener;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

// Spring expects methods in this class to be publicly available (not final),
// thus need to turn checksytle off.
// CHECKSTYLE:OFF
/**
 * A configuration class that declares one or more @Bean methods and also
 * triggers auto-configuration and component scanning. This is a convenience
 * annotation that is equivalent to
 * declaring @Configuration, @EnableAutoConfiguration and @ComponentScan.
 */
@SpringBootApplication
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    @Autowired
    private ServiceContext serviceContext;

    /**
     * Entry point for the application.
     * 
     * @param args application start arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Print configuration values.
     * 
     * @param configValue my.config.value
     * @param myEnvVariable env variable set in the propeties file.
     * @param springProfilesActive name of the active profile,
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner configValuePrinter(@Value("${my.config.value:}") final String configValue,
            @Value("${MyEnvVariable:}") final String myEnvVariable,
            @Value("${SpringProfilesActive:}") final String springProfilesActive) {
        return args -> LOGGER
                .info("configValuePrinter: my.config.value= " + configValue + "; serviceContext=" + serviceContext
                        + "; myEnvVariable=" + myEnvVariable + ";springProfilesActive=" + springProfilesActive);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * Return header details object.
     * 
     * @return HeaderDetail
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public HeaderDetail headerDetail() {
        return new HeaderDetailImpl();
    }

    /**
     * Unless explicitly configured otherwise this factory will created
     * containers that listens for HTTP requests on port 8080.
     * 
     * @return TomcatEmbeddedServletContainerFactory
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(final Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
        };
    }

    /**
     * Add Servlet listener to exposes the request to the current thread.
     * 
     * @return RequestContextListener
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /**
     * Add a filter to extract the desired headers out of the request.
     * 
     * @return ExtractHeadersFilter
     */
    @Bean
    public ExtractHeadersFilter extractHeadersFilter() {
        return new ExtractHeadersFilter();
    }

    /**
     * Build the API Information.
     * 
     * @return Docket
     */
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("sbdemo")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/.*"))
                .build();
    }

    /**
     * Build the API Information.
     * 
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Demo Service")
                .description("Spring Boot Demo Service")
                .termsOfServiceUrl("")
                .contact("John Curry")
                .license("")
                .licenseUrl("")
                .version("0.0.1")
                .build();
    }
    
}
