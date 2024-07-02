package com.example.stage1HNG;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class helloService {

    public helloResponse hello(String visitorName, HttpServletRequest request) {
        String clientIP = request.getRemoteAddr();
        // For demonstration purposes, assuming location based on IP
        String location = "New York"; // You can use a service or API to get actual location

        // Hardcoded temperature for demonstration
        int temperatureCelsius = 11;
        String greeting = String.format("Hello, %s! The temperature is %d degrees Celsius in %s", visitorName, temperatureCelsius, location);

        return new helloResponse(clientIP, location, greeting);
    }
}
