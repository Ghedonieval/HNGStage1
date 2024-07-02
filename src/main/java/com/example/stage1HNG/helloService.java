package com.example.stage1HNG;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class helloService {

    public helloResponse hello(String visitorName, HttpServletRequest request) {
//        String clientIP = request.getRemoteAddr();
        String clientIP = "127.0.0.1";
        String location = "New York";
        int temperatureCelsius = 11;
        String greeting = String.format("Hello, %s! The temperature is %d degrees Celsius in %s", visitorName, temperatureCelsius, location);

        return new helloResponse(clientIP, location, greeting);
    }
}
