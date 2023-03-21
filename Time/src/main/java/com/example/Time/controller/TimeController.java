package com.example.Time.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Time.model.CurrentTime;
import com.example.Time.service.TimeService;

@RestController
public class TimeController {
	@Autowired
	private TimeService timeService;
	
	@PostMapping("/time")
	public CurrentTime  getCurrentTimeByCountry(@RequestBody(required = false)Map <String, String> requestBody )
	{
		String defaultTimeZoneId = "Thailand"; // Default time zone for Thailand
        String timeZoneId = defaultTimeZoneId;

        if (requestBody != null && !requestBody.isEmpty() && requestBody.containsKey("country")) {
           String country = requestBody.get("country");
        	
            timeZoneId = country;
//            // in Case of lower case or something 
//            for (String key : requestBody.keySet()) 
//            {
//                if (key.equalsIgnoreCase("country")) 
//                {
//                    country = requestBody.get(key);
//                    break;
//                }
//            }
//            
//            switch (country) 
//            {
//            	case "United States":
//            		timeZoneId = "America/New_York";
//            		break;
//            	case "United Kingdom":
//            		timeZoneId = "Europe/London";
//            		break;
//            		// Add more countries and their corresponding time zone IDs as needed.
//            }
          

    }
		return timeService.getCurrentTimeByTimeZone(timeZoneId);
		
	}

}
