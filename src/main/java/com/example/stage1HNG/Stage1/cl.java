package com.example.stage1HNG.Stage1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/v1")
public class cl {
    @GetMapping("/api/hello")
    public ResponseEntity<Object> getGreeting(@RequestParam(required = false) String visitorName) throws  IOException {
        if (visitorName == null || visitorName.trim().isEmpty()) {
            return new ResponseEntity<>(
                    "{\"status\": \"error\", \"message\": \"Please enter a visitor name\"}",
                    HttpStatus.BAD_REQUEST);
        }

        String clientIp = getClientIp();
        String weatherUrl = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s", env.getProperty("WEATHER_API_KEY"), clientIp);
        HttpResponse<String> weatherResponse = Unirest.get(weatherUrl).asString();

        if (weatherResponse.getStatus() != 200) {
            return new ResponseEntity<>(
                    "{\"status\": \"error\", \"message\": \"Failed to get location or the weather information\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weatherData = mapper.readValue(weatherResponse.getBody(), WeatherResponse.class);

        return new ResponseEntity<>(
                String.format("{\"client_ip\": \"%s\", \"location\": \"%s\", \"greeting\": \"Hello, %s!, the temperature is %s degrees Celsius in %s\"}",
                        clientIp, weatherData.getLocation().getName(), visitorName, weatherData.getCurrent().getTempC(), weatherData.getLocation().getName()),
                HttpStatus.OK);
    }

    private String getClientIp() {
        // Implement logic to get client IP address (e.g., using a library)
        // Replace with your preferred IP retrieval method
        return "127.0.0.1"; // Replace with actual IP retrieval logic
    }

    // Class representing the response from the weather API
    public static class WeatherResponse {
        private Location location;
        private Current current;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Current getCurrent() {
            return current;
        }

        public void setCurrent(Current current) {
            this.current = current;
        }

        // Inner class representing location data
        public static class Location {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        // Inner class representing current weather data
        public static class Current {
            private double temp_c;

            public double getTempC() {
                return temp_c;
            }

            public void setTempC(double temp_c) {
                this.temp_c = temp_c;
            }
        }
    }
}
