package com.team4.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
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
                .title("GDG_Project")
                .description("""
                        ## Swagger 이용방법
                        
                        - 해당 페이지는 gdg 사이드 프로젝트 swagger페이지입니다. 
                        - 에러나 문제가 발생한다면 곧바로 노션이나 카톡으로 에러이슈를 말씀해주세요.
                        - uri는 꼭 `/api/v1`으로 시작하며, Restful api를 지향합니다.
                        - `/api/v1/auth/**` 외에는 모두 인증/인가를 수행합니다.
                        
                        ### 인증인가 방법
                        - Swagger에서는 오른쪽에 보이는 Authorize에 토큰값만 넣어주면 됩니다.
                        - 실제 토큰검증은 요청 헤더에 `Authorization: Bearer {token}` 형식으로 요청해주시면 됩니다.
                        """)
                .version("1.0.0");
    }
}
