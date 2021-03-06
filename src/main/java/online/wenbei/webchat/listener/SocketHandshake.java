package online.wenbei.webchat.listener;

import online.wenbei.webchat.util.JsonUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: cityuu#163.com
 * @Date: 2019-08-29 13:51
 * @version: v1.0
 * @Description:
 */

@Component
public class SocketHandshake implements HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
            // 从session中获取到当前登录的用户信息. 作为socket的账号信息. session的的WEBSOCKET_USERNAME信息,在用户打开页面的时候设置.

            System.out.println(JsonUtil.parseToJSON(servletRequest.getHeaderNames()));
            UserAgent userAgent = UserAgent.parseUserAgentString(servletRequest.getHeader("User-Agent"));
            String  device = servletRequest.getHeader("device");

            map.put("ws_user",device!=null?device:userAgent.getOperatingSystem().getName().replaceAll(" ","")+userAgent.getId());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
