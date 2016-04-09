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
 *
 * ApplicationSecurityConfig provides configuration for Spring Security which enables
 * role based authentiation and authorisation on a url basis. This class specifies a
 * admin role only url and defines a controller and view to use for login purposes/
 *
 * Certain parts of the code are based on this stackoverflow question:
 * http://stackoverflow.com/questions/23174275/configure-actuator-enpoints-security
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
@EnableWebMvcSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * Values defined in application.properties file for the default admin credentials
     */
    @Value("${nppc.security.admin.name}")
    private String adminUser;
    @Value("${nppc.security.admin.password}")
    private String adminPassword;

    /**
     * Configures the authenticationManager to recognise the default admin credentials
     * @param auth the AuthenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(adminUser).password(adminPassword).roles("ADMIN");
    }

    /**
     * Configures the security manager to require authorisation on requests made to any url with the route '/admin/
     * @param http HttpSecurity the web based security manager
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN").and().formLogin().
                loginPage("/login").failureUrl("/login?error").and().logout()
                .logoutUrl("/admin/logout").logoutSuccessUrl("/");
    }





}
