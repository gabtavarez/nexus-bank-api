package com.tavarez.nexusbank.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;


/**
 * Classe de configuração de segurança do Nexus Bank.
 * Define quais rotas são públicas, quais precisam de login e libera o console do H2.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define o filtro de segurança da aplicação.
     * Configura o acesso ao H2, desabilita CSRF para facilitar testes e define o login básico.
     *
     * @param http Objeto HttpSecurity para configurar a segurança web.
     * @return SecurityFilterChain configurado.
     * @throws Exception caso ocorra erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desabilita CSRF (necessário para APIs REST e para o console do H2)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configura as permissões de acesso
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso total ao console do H2 sem autenticação
                        .requestMatchers("/h2-console/**").permitAll()
                        // Por enquanto, permite acesso total aos seus controllers para facilitar os testes
                        .requestMatchers("/users/**", "/accounts/**").permitAll()
                        // Qualquer outra requisição precisará de autenticação
                        .anyRequest().authenticated()
                )

                // 3. Habilita o Basic Auth (aquele que você preenche no Postman)
                .httpBasic(Customizer.withDefaults())

                // 4. Configuração crucial para o H2 Console: permite exibir a página em frames
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServlet() {
        ServletRegistrationBean<JakartaWebServlet> bean =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        bean.addInitParameter("webAllowOthers", "true");
        return bean;
    }
}