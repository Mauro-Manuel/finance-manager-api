package com.masprog.services;

import com.masprog.config.OmbalaConfig;
import com.masprog.dto.OmbalaSmsRequestDTO;
import com.masprog.dto.OmbalaSmsResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class OmbalaSmsClient {

    private final WebClient ombalaWebClient;

    @Value("${ombala.api.sender}")
    private String sender;

    public OmbalaSmsClient(WebClient ombalaWebClient) {
        this.ombalaWebClient = ombalaWebClient;
    }

    public Mono<OmbalaSmsResponseDTO> sendSms(OmbalaSmsRequestDTO request) {

        if (request.getFrom() == null || request.getFrom().isEmpty()) {
            request.setFrom(sender);
        }

        return ombalaWebClient.post()
                .uri("/v1/messages")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.err.println("Erro Ombala: " + errorBody);
                                    return Mono.error(new RuntimeException("Erro Ombala: " + errorBody));
                                }))
                .bodyToMono(OmbalaSmsResponseDTO.class);
    }
}
