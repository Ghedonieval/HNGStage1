package com.example.stage1HNG;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class helloService {

    private ipGelocoationService ipGelocoationService;

    @Autowired
    private RestTemplate restTemplate;

    private final String APIIP_URL = "https://apiip.net/api/ip";

    public helloResponse hello(String visitorName, HttpServletRequest request) {
        String clientIP = request.getRemoteAddr();
//        String clientIP = "127.0.0.1";
        String location = "New York";


        int temperatureCelsius = 11;
        String greeting = String.format("Hello, %s! The temperature is %d degrees Celsius in %s", visitorName, temperatureCelsius, location);

        return new helloResponse(clientIP, location, greeting);
    }

    public helloResponse hello2(String visitorName, HttpServletRequest request) {
        String clientIP = request.getRemoteAddr();
//        String clientIP = "127.0.0.1";
//       String location = "New York";
            String location = getLocation(clientIP);

        int temperatureCelsius = 11;
        String greeting = String.format("Hello, %s! The temperature is %d degrees Celsius in %s", visitorName, temperatureCelsius, location);

        return new helloResponse(clientIP, location, greeting);
    }

    private String getLocation(String clientIp) {
        RestTemplate restTemplate = new RestTemplate();
        String url = APIIP_URL + "/" + clientIp;

        // Make HTTP GET request to apiip service
        ApiIpResponse response = restTemplate.getForObject(url, ApiIpResponse.class);

        if (response != null && response.getCity() != null) {
            return response.getCity();
        } else {
            return "Unknown"; // Default location if API call fails or location not found
        }
    }

    public helloResponse hello3(String visitorName, HttpServletRequest request) {
        String clientIP = request.getRemoteAddr();
//        String clientIP = "127.0.0.1";
 //      String location = "New York";
        String location = getCityByIp(clientIP);


        int temperatureCelsius = 11;
        String greeting = String.format("Hello, %s! The temperature is %d degrees Celsius in %s", visitorName, temperatureCelsius, location);

        return new helloResponse(clientIP, location, greeting);
    }
    public String getCityByIp(String ipAddress) {
        String apiKey = "2561d856-8645-4ecb-bf89-6b808f65eeb6"; // Replace with your actual apiip API key
        String url = "https://api.apiip.com/v1/ip/" + ipAddress + "?api_key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonResponse = new JSONObject(response.getBody());
                return jsonResponse.getString("city"); // Assuming city is a field in the response
            }
        } catch (RestClientResponseException e) {
            // Handle potential exceptions during the API call
            e.printStackTrace();
        }

        return null; // Handle cases where API call fails
    }
}
