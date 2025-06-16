package com.welab.k8s_api_gateway.gateway.filter;

import com.welab.k8s_api_gateway.security.jwt.authentication.UserPrincipal;
import org.apache.catalina.Server;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

public class AuthenticationHeaderFilterFunction {
    public static Function<ServerRequest, ServerRequest> addHeader() {
        return request -> {
            ServerRequest.Builder requestBuilder = ServerRequest.from(request);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserPrincipal userPrincipal) {
                requestBuilder.header("X-Auth-UserId", userPrincipal.getUserId());

                // 다른 claims 들도 추가
            }

            // Client ID, DeviceType 등도 필요 시, 입력

            return requestBuilder.build();
        };
    }
}
