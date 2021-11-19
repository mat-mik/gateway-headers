package com.example.gatewayheaders;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.CacheControl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GatewayHeadersApplicationTests {

    @Autowired
    private WebTestClient webClient;

    private ClientAndServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = ClientAndServer.startClientAndServer(9999);
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }


    @Test
    @WithMockUser
    void shouldHandleLowerCase() {
        // given
        mockServer
                .when(request().withMethod("GET").withPath("/cache"))
                .respond(response().withStatusCode(200).withHeader("cache-control", "max-age=120"));

        // when
        WebTestClient.ResponseSpec responseSpec = webClient.get().uri("/cache").exchange();

        // then
        responseSpec
                .expectStatus().isOk()
                .expectHeader().cacheControl(CacheControl.maxAge(120, TimeUnit.SECONDS));
    }

}
