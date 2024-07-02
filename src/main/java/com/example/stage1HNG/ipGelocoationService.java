package com.example.stage1HNG;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class ipGelocoationService {

    @Value("${apiip.apikey}")
    private String apikey;

    private final RestTemplate restTemplate;



    public ipGelocoationService( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getLocation(String ipAddress) {
        String apiUrl = "https://api.ipgeolocationapi.com/geolocate/" + ipAddress;
        String authorizationHeader = "Bearer " + apikey;

        try {
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);

            // Prepare HTTP entity with headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make HTTP GET request to API
            ResponseEntity<geoLocation> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, geoLocation.class);
           // ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody().toString();
            } else {
                // Handle error responses
                return null;
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
