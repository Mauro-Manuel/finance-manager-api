package com.masprog.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiHealthCheckJob {

    private final RestTemplate restTemplate = new RestTemplate();

    // "0 */14 * * * *" → a cada 14 minutos
    @Scheduled(cron = "0 */14 * * * *")
    public void pingApi() {
        String apiUrl = System.getenv("MONEY_MANAGER_BACKEND_URL"); // pega da variável de ambiente
        try {
            var response = restTemplate.getForEntity(apiUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("GET request sent successfully");
            } else {
                System.out.println("GET request failed: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.err.println("Error while sending request: " + e.getMessage());
        }
    }

}
