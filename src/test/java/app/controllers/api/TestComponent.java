package app.controllers.api;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestComponent {

    @EventListener(WebServerInitializedEvent.class)
    public void setPort(WebServerInitializedEvent event) {
        RestAssured.requestSpecification.port(event.getWebServer().getPort());
        log.info("Port set to: '{}'", event.getWebServer().getPort());
    }
}
