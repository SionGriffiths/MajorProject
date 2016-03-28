package com.siongriffiths.nppcdatavisualiser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Created on 17/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

//http://stackoverflow.com/questions/23174275/configure-actuator-enpoints-security
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
@EnableWebMvcSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${nppc.security.admin.name}")
    private String adminUser;
    @Value("${nppc.security.admin.password}")
    private String adminPassword;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(adminUser).password(adminPassword).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN").and().formLogin()
        .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");
    }



}
