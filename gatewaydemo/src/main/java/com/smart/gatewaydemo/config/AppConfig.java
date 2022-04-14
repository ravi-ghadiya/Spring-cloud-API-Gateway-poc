package com.smart.gatewaydemo.config;

import com.smart.gatewaydemo.auth.SecurityContextRepository;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
configuration class that sets up authorization and authentication for the gateway.
 */
@Configuration
@EnableWebFluxSecurity
@RefreshScope
public class AppConfig {

    private final ReactiveAuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final PathSettings pathSettings;


    @Autowired
    public AppConfig(ReactiveAuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository, PathSettings pathSettings) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.pathSettings = pathSettings;
    }


    @Bean
    @RefreshScope
    public SecurityWebFilterChain configure(ServerHttpSecurity httpSecurity){
        httpSecurity.csrf().disable()
                .exceptionHandling().and()
                .httpBasic().disable()
                .formLogin().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(pathSettings.getPrivatePath()).denyAll()
                .pathMatchers(pathSettings.getAuthenticatedPath()).authenticated()
                .pathMatchers(pathSettings.getPublicPath()).permitAll();
        return httpSecurity.authorizeExchange().anyExchange().denyAll().and().build();
    }


        /*@Bean
        public Decoder feignDecoder() {

            ObjectFactory<HttpMessageConverters> messageConverters = () -> {
                HttpMessageConverters converters = new HttpMessageConverters();
                return converters;
            };
            return new SpringDecoder(messageConverters);
        }*/

//    private ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

//    @Bean
//    Encoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(messageConverters));
//    }
//
//    @Bean
//    Decoder feignFormDecoder() {
//        return new SpringDecoder(messageConverters);
//    }
}
