package com.viktoriia.config;

import com.viktoriia.user.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.inject.Inject;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor(usersConnectionRepository));
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/signin");
        registry.addViewController("/signout");
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

    private @Inject UsersConnectionRepository usersConnectionRepository;

}
