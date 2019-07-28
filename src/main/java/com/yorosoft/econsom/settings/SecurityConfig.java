package com.yorosoft.econsom.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
    };

    private static final String[] PUBLIC_MATCHERS = {
            "/bundles/**",
            "/css/**",
            "/fonts/**",
            "/images/**",
            "/js/**",
            "/vendor/**",
            "/",
            "/login"
    };

    private static final String[] SECURE_URLS = {
            "/dashboard/**",
            "/addUser/**",
            "/userList/**",
            "/addEmployee/**",
            "/listEmployee/**",
            "/collectiveScore/**",
            "/individualScore/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(SECURE_URLS).hasRole("ADMIN")
                .antMatchers("/register/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable()
                .cors().disable()
                .formLogin()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/dashboard")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll();
    }

}
