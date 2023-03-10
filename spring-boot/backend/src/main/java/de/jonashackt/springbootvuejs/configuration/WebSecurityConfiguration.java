package de.jonashackt.springbootvuejs.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // No session will be created or used by spring security
//        .and()
//            .httpBasic()
        .and()
            .authorizeRequests()
                //.antMatchers("/api/*").hasIpAddress(",").
                //.antMatchers("/api/**").access("hasIpAddress('0:0:0:0:0:0:0:1') or hasIpAddress('104.177.0.0/16') or hasIpAddress('76.190.0.0/16') or hasIpAddress('70.92.0.0/16') or hasIpAddress('192.168.0.0/16')")
                //.antMatchers("/api/*/*").access("hasIpAddress('104.177.0.0/16') or hasIpAddress('76.190.0.0/16') or hasIpAddress('70.92.0.0/16') or hasIpAddress('192.168.0.0/16')")
//                .antMatchers("/api/*").access("hasIpAddress('104.177.0.0/16') or hasIpAddress('0:0:0:0:0:0:0:1') or hasIpAddress('76.190.0.0/16') or hasIpAddress('70.92.0.0/16') or hasIpAddress('192.168.0.0/16')")
//                .antMatchers("/api/hello").permitAll()
//                .antMatchers("/api/user/**").permitAll() // allow every URI, that begins with '/api/user/'
//                .antMatchers("/api/secured").authenticated()
//                //.anyRequest().authenticated() // protect all other requests
//                .antMatchers("/*").permitAll()
                .anyRequest().permitAll()
        .and()
            .csrf().disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!
    }

    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.inMemoryAuthentication()
    //            .withUser("foo").password("{noop}bar").roles("USER");
    //}
}
