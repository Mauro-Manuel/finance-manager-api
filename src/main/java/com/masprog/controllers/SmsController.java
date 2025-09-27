package com.masprog.controllers;

import com.masprog.dto.OmbalaSmsRequestDTO;
import com.masprog.dto.OmbalaSmsResponseDTO;
import com.masprog.services.OmbalaSmsClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final OmbalaSmsClient ombalaSmsClient;

    public SmsController(OmbalaSmsClient ombalaSmsClient) {
        this.ombalaSmsClient = ombalaSmsClient;
    }

    @PostMapping("/send")
    public Mono<ResponseEntity<OmbalaSmsResponseDTO>> sendSms(@RequestBody OmbalaSmsRequestDTO request) {
        return ombalaSmsClient.sendSms(request)
                .map(ResponseEntity::ok);
    }
}
