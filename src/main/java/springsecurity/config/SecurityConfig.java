package springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("http://localhost:9000");
    }

    @Bean
    public SecurityFilterChain resourceServer(HttpSecurity http) throws Exception {

        http
                .oauth2ResourceServer((resource) -> resource.jwt(Customizer.withDefaults()));

        http
                .authorizeHttpRequests((auth) -> auth
                        // 접근 권한
                        .requestMatchers("/me").hasAuthority("SCOPE_profile")
                        // admin 접근 권한
                        .requestMatchers("/admin").hasAnyAuthority("SCOPE_admin", "ROLE_ADMIN")
                        .anyRequest().authenticated()
                );

        http
                // 무상태 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
