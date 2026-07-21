package com.itechro.cas.config;

import com.itechro.cas.exception.impl.ExceptionMessageFinder;
import com.itechro.cas.security.JWTAuthenticationFilter;
import com.itechro.cas.security.JWTLoginFilter;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.service.master.SystemParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.PrivateKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);

    private final String PUBLIC_KEY_ENDPOINT = "public_key";

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private PrivateKey privateKey;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private WebAuditService webAuditService;

    @Autowired
    private ExceptionMessageFinder exceptionMessageFinder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String publicKeyUrlPattern = "/" + casProperties.getApiPrefix() + "/" + PUBLIC_KEY_ENDPOINT + "/**";
        String[] ignoredAntPatterns = {casProperties.getStaticResourcesPath()};
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry securityConfig = http
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/web-login").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/external/updateForgetPassword").permitAll()
                .antMatchers(HttpMethod.GET, "/api/home/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/external/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/security/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/security/**").permitAll()
                .antMatchers(HttpMethod.GET, "/mobile-api/master/getSystemParameters").permitAll()
                .antMatchers(HttpMethod.POST, "/mobile-api/error_log").permitAll()
                .antMatchers(HttpMethod.POST, "/api/crm/crmLogin").permitAll()
                .antMatchers(HttpMethod.GET, "/api/crm/addLeadDetailsToCrmRequest/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/crm/mockAddLeadToCrm/**").permitAll()
                .antMatchers(casProperties.getStaticResourcesPath()).permitAll()
                .antMatchers(publicKeyUrlPattern).permitAll();

        if (casProperties.getSecurityEnabled()) {
            securityConfig.anyRequest().authenticated();
        } else {
            securityConfig.anyRequest().permitAll();
        }


        securityConfig
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(securityService, systemParameterService, ignoredAntPatterns),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(),
                                securityService, systemParameterService, privateKey, webAuditService, exceptionMessageFinder),
                        UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws
            Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


}
