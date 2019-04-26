package com.itaem.dazhi.websocketdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Dazhi
 * @date 2019/4/25 20:40
 */
@Component
public class WebSocketConfig {


    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
