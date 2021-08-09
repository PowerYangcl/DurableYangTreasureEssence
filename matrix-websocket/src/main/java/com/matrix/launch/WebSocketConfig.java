package com.matrix.launch;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.matrix.system.HttpHandShakeIntecepter;
import com.matrix.system.SocketChannelIntecepter;

/**
 * @description: 
 * 		@EnableWebSocketMessageBroker：在 WebSocket 上启用STOMP
 * 
 * 		参考：https://www.jianshu.com/p/9103c9c7e128
 * 
 * @author Yangcl
 * @date 2021-7-30 18:41:26
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.launch.WebSocketConfig.java
 * @version 1.6.0.6-websocket
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	/**
	 * @description: 配置基站。添加Endpoint，在网页中就可以通过websocket连接上服务，
	 * 		也就是我们配置websocket的服务地址，并且可以指定是否使用socketjs
	 *
	 * @author Yangcl
	 * @date 2021-7-30 18:17:50
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpoint-websocket").addInterceptors(new HttpHandShakeIntecepter()).setAllowedOrigins("*").withSockJS();
    }

    /**
     * @description: 配置消息代理，哪种路径的消息会进行代理处理
     *
     * @author Yangcl
     * @date 2021-7-30 18:17:06
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/chat");				// 服务端推送给客户端的路径前缀
        registry.setApplicationDestinationPrefixes("/app");		// 客户端发送数据给服务器端的一个前缀
    }


    /**
     * @description: 设置输入消息通道的线程数。默认线程为1，可以自己自定义线程数，最大线程数和线程存活时间。
     *
     * @author Yangcl
     * @date 2021-7-30 18:48:02
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());
    }

    /**
     * @description: 设置输出消息通道的线程数。默认线程为1，可以自己自定义线程数，最大线程数和线程存活时间。
     *
     * @author Yangcl
     * @date 2021-7-30 18:48:39
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());
    }
    
    /**
     * @description: 添加自定义的消息转换器。spring 提供多种默认的消息转换器，
     * 		return false，不会添加消息转换器；
     * 		return true，会添加默认的消息转换器；
     * 		也可以把自己写的消息转换器添加到转换链中。
     *
     * @author Yangcl
     * @date 2021-7-30 18:50:09
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		return true;
	}
}













