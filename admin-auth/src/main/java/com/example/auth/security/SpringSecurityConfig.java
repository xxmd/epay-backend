package com.example.auth.security;

import com.example.common.domain.annotation.Anonymous;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final UserAuthFilter userAuthFilter;
    private final ApplicationContext applicationContext;

    public SpringSecurityConfig(UserAuthFilter userAuthFilter,
                                ApplicationContext applicationContext) {
        this.userAuthFilter = userAuthFilter;
        this.applicationContext = applicationContext;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<UserAuthFilter> userAuthFilterRegistration(UserAuthFilter filter) {
        FilterRegistrationBean<UserAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                ).addFilterBefore(userAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        Set<String> paths = scanAnonymousPaths();
        paths.add("/error");
        paths.add("/files/**");
        return (web) -> web.ignoring().requestMatchers(paths.toArray(String[]::new));
    }

    private Set<String> scanAnonymousPaths() {
        Set<String> anonymousPaths = new HashSet<>();
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            Method method = entry.getValue().getMethod();
            if (AnnotatedElementUtils.hasAnnotation(method, Anonymous.class)) {
                RequestMappingInfo mappingInfo = entry.getKey();
                if (mappingInfo.getPathPatternsCondition() != null) {
                    mappingInfo.getPathPatternsCondition().getPatterns()
                            .forEach(p -> anonymousPaths.add(p.getPatternString()));
                }
            }
        }
        return anonymousPaths;
    }
}
