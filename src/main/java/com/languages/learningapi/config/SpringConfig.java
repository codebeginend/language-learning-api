package com.languages.learningapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@PropertySource({"classpath:application-${spring.profiles.active:local}.properties"})
@PropertySource({"classpath:application.yml"})
public class SpringConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEnconderTest();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jsonConverter.getObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        return jsonConverter;
    }
}
