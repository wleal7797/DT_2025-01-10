package co.edu.unbosque.software_electroadonai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CaptchaValidationFilter captchaValidationFilter;

    public SecurityConfig(CaptchaValidationFilter captchaValidationFilter) {
        this.captchaValidationFilter = captchaValidationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF
                .addFilterBefore(captchaValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        //Permisos:
                        //.requestMatchers(HttpMethod."METODOS PERMITIDOS", "RUTA EXCLUSIVA PARA ROL").hasRole("ROL")
                        //.requestMatchers("RUTA").hasRole("ROL")
                        //.requestMatchers("/main/").hasAuthority("ADMIN")
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/denied").permitAll()

                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/main").hasRole("ADMIN")
                        .requestMatchers("/main/**").hasRole("ADMIN")
                        .requestMatchers("/mainVendedor").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/mainVendedor/**").hasAnyRole("VENDEDOR", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                        //.accessDeniedPage("/denied?denied")
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
