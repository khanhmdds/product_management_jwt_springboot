//package com.example.ajax_boot_c08.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*")); // Cho phép tất cả các origin
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE, vv.)
//        configuration.setAllowedHeaders(Arrays.asList("*")); // Cho phép tất cả các header
//        configuration.setAllowCredentials(true); // Cho phép sử dụng cookie khi truy cập từ client-side
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
//}
