package com.example.Time.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import com.example.Time.model.CurrentTime;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Locale;
@Service
public class TimeService {
	public CurrentTime getCurrentTimeByTimeZone(String timeZoneId) {
		
		
		ZoneId zoneId;
		try {
			zoneId = ZoneId.of(getZoneIdByCountry(timeZoneId));
		} catch (Exception e) {
				throw new RuntimeException("Invalid time zone ID:" + timeZoneId,e);
		}
		//System.out.println(timeZoneId);
//		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime zonedDateTime = LocalDateTime.now(zoneId);
//		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	     String formattedTime = zonedDateTime.format(formatter);
	     return new CurrentTime(formattedTime);
		
		
	     
	     
		
		}
	
	// Taking the Country Code and return the Current time using API 
	public String getZoneIdByCountry(String country) throws IOException {
		 String CountryCode = getCountryCodeByName(country);
		 final String API_KEY = "SU2SE9XQI4VU";
		 final String TIMEZONEDB_API_URL = "https://api.timezonedb.com/v2.1/list-time-zone?key=" + API_KEY + "&format=json&country=";
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(TIMEZONEDB_API_URL + CountryCode, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode zonesNode = rootNode.path("zones");

        if (zonesNode.isArray()) {
            for (JsonNode zoneNode : zonesNode) {
                String zoneId = zoneNode.path("zoneName").asText();
                if (!zoneId.isEmpty()) {
                    return zoneId;
                }
            }
        }

        return "ZoneId not found";
    }
	
	// Turning Full country name to 2 letter code Example: Japan => JP
	   private String getCountryCodeByName(String countryName) {
	        for (String countryCode : Locale.getISOCountries()) {
	            Locale locale = new Locale("", countryCode);
	            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
	                return countryCode;
	            }
	        }
	        return null;
	    }
}
