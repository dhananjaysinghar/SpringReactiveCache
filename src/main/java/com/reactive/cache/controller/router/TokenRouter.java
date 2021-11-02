package com.reactive.cache.controller.router;

import com.reactive.cache.controller.TokenController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class TokenRouter {

    @Bean
    public RouterFunction<ServerResponse> routeCalls(TokenController controller){
        return RouterFunctions.route(RequestPredicates.GET("/token"), controller::getToken);
    }
}
