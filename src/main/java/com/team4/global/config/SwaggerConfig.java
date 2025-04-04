package com.team4.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile({"dev", "default"})
public class SwaggerConfig {

    @Bean
    @Profile("default")
    public Server defaultServer() {
        return null;
    }

    @Bean
    @Profile("dev")
    public Server devServer() {
        Server devServer = new Server();
        devServer.setDescription("dev");
        devServer.setUrl("http://35.226.74.220:8080");
        return devServer;
    }

    @Bean
    public OpenAPI openAPI(Server server) {
        // Security 설정: JWT 기반 인증
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP) // HTTP 기반 인증
                .scheme("bearer") // bearer 토큰 방식
                .bearerFormat("JWT") // JWT 형식으로 설정
                .in(SecurityScheme.In.HEADER) // Authorization 헤더에서 토큰을 받음
                .name("Authorization"); // 헤더 이름 설정

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        OpenAPI openAPI = new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement))
                .info(apiInfo()) // API 정보 설정
                .openapi("3.0.0"); // OpenAPI 버전 설정

        if (server != null) {
            openAPI.servers(List.of(server));
        }

        return openAPI;
    }

    private Info apiInfo() {
        return new Info()
                .title("Ruty API Docs")
                .description("Swagger에서는 간편하게 API 테스트를 수행할 수 있도록 구성할 생각이며, API의 특이사항은 이곳에 작성해두겠습니다." +
                        "<br>모든 요청이 토큰을 기반으로 동작하기에 테스트하실때 꼭 토큰을 추가해주세요." +
                        "<br>(소셜 로그인은 Swagger에 따로 표시되지 않으니 이곳을 참고해주시면 감사하겠습니다.)")
                .version("1.0.0");
    }
}
