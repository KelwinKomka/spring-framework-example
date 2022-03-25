package com.servico.config.security;

import com.servico.model.entity.Role;
import com.servico.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile(value = {"prod", "test"})
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final UserDetailsService authService;
    private final TokenService tokenService;
    private final UserService userService;

    public SecurityConfigurations(UserDetailsService authService, TokenService tokenService, UserService userService) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //Configuracoes de autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configuracoes de Autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/task").permitAll()
                .antMatchers(HttpMethod.GET, "/api/task/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/task/*").hasRole(Role.ADMIN.name())

                .antMatchers("/h2-console").permitAll()
                .antMatchers("/h2-console/**").permitAll()

                .antMatchers(HttpMethod.GET, "/actuator").permitAll() //Não deve liberar em produção
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll() //Não deve liberar em produção

                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationTokenFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class);
    }

    //Configuracoes de recursos estaticos(js, css, imagens)
    @Override
    public void configure(WebSecurity web) throws Exception {
        //Liberar recursos que o Swagger usa
        web.ignoring().antMatchers("/**.html",
                "/v2/api-docs", "/v3/api-docs/**",
                "/webjars/**", "/configuration/**",
                "/swagger-resources/**", "/swagger-ui/**",
                "/h2-console/**");
    }
}
