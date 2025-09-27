package com.masprog.services;

import com.masprog.dto.OmbalaSmsRequestDTO;
import com.masprog.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsSchedulerService {

    private final ProfileRepository profileRepository;
    private final OmbalaSmsClient ombalaSmsClient;

    @Scheduled(cron = "0 0 20 * * *", zone = "Africa/Luanda")
    //@Scheduled(cron = "0 * * * * *", zone = "Africa/Luanda")
    public void sendDailyExpenseReminder() {
        log.info("📅 Iniciando envio diário de lembretes de despesas...");

        var profiles = profileRepository.findAllByIsActiveTrue();

        if (profiles.isEmpty()) {
            log.info("Nenhum perfil ativo encontrado. Nada para enviar.");
            return;
        }

        String message = "Este é um lembrete amigável para adicionar as " +
                "suas receitas e despesas de hoje no Finance Manager.";

        Flux.fromIterable(profiles)
                .flatMap(profile -> {
                    OmbalaSmsRequestDTO smsRequest = new OmbalaSmsRequestDTO();
                    smsRequest.setMessage(message);
                    smsRequest.setTo(profile.getContact());
                   // smsRequest.setFrom(null);
                    log.info("Enviando SMS para {}", profile.getContact());
                    return ombalaSmsClient.sendSms(smsRequest)
                            .doOnSuccess(response -> log.info("SMS enviado para {} com id {}", profile.getContact(), response.getId()))
                            .doOnError(error -> log.error("Falha ao enviar SMS para {}: {}", profile.getContact(), error.getMessage()));
                })
                .subscribe();
    }
}
