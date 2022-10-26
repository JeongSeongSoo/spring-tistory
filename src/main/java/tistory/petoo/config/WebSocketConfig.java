package tistory.petoo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        /*
            2022.10.26[프뚜]:
                Spring에서 Bean은 싱글톤으로 관리되지만,
                @ServerEndpoint 클래스는 WebSocket이 생성될 때마다 인스턴스가 생성되고
                JWA에 의해 관리되기 때문에 Spring의 @Autowired가 설정된 멤버들이 초기화 되지 않습니다.
                연결해주고 초기화해주는 클래스가 필요합니다.
         */
        return new ServerEndpointExporter();
    }

}