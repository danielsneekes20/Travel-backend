package com.Sneekes.backend.service;
import com.Sneekes.backend.dto.NsStationResponseDto;
import com.Sneekes.backend.dto.StationDto;
import com.Sneekes.backend.entity.FavoriteStation;
import com.Sneekes.backend.entity.Station;
import com.Sneekes.backend.entity.User;
import com.Sneekes.backend.repository.FavoriteStationRepository;
import com.Sneekes.backend.repository.StationRepository;
import com.Sneekes.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.Sneekes.backend.dto.NsStationDto;
import org.springframework.web.client.RestTemplate;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.Sneekes.backend.dto.AddToFavoriteStationRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationRepository stationRepository;
    private final FavoriteStationRepository favoriteStationRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;


    public StationService(StationRepository stationRepository, FavoriteStationRepository favoriteStationRepository, UserRepository userRepository) {
        this.stationRepository = stationRepository;
        this.favoriteStationRepository = favoriteStationRepository;
        this.userRepository= userRepository;
        this.restTemplate = new RestTemplate();
    }

    public Set<StationDto> getStations() {
        return stationRepository.findAll().stream()
                .map(st -> new StationDto(st.getId(), st.getNameLong(), st.getUicCode()))
                .collect(Collectors.toSet());
    }

    public Set<StationDto> addToFavoriteStations(Long stationId, Authentication authentication) {
        System.out.println("stationId = " + stationId);
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));

        String username = authentication.getName(); // This gives you the username from the token

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FavoriteStation favoriteStation = new FavoriteStation();
        favoriteStation.setUser(user);
        favoriteStation.setStation(station);

        favoriteStationRepository.save(favoriteStation);

        return favoriteStationRepository.findAllByUser(user).stream()
                .map(FavoriteStation::getStation)
                .map(st -> new StationDto(st.getId(), st.getNameLong(), station.getUicCode()))
                .collect(Collectors.toSet());
    }

    public Set <StationDto> getFavoriteStations(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return favoriteStationRepository.findAllByUser(user).stream()
                .map(FavoriteStation::getStation)
                .map(st -> new StationDto(st.getId(), st.getNameLong(), st.getUicCode()))
                .collect(Collectors.toSet());
    }

    public Set<String> fetchAndSaveStationsFromApi() {
        System.out.println("check");
        System.out.println("externalStations = " + callNsApi());
        List<NsStationDto> externalStations = callNsApi();

        for (NsStationDto dto : externalStations) {
            boolean exists = stationRepository.existsByNameLong(dto.getNamen().getLang());
            if (!exists) {
                Station station = new Station();
                station.setNameLong(dto.getNamen().getLang());
                station.setNameMedium(dto.getNamen().getMiddel());
                station.setNameShort(dto.getNamen().getKort());
                station.setUicCode(dto.getUicCode());
                stationRepository.save(station);
            }
        }

        return stationRepository.findAll().stream()
                .map(Station::getNameLong)
                .collect(Collectors.toSet());
    }
    public void logRawResponseFromNsApi() {
        String url = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2/stations";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", "7b014b8a627148bcafbc86d45b22d6f0");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Raw NS API response: " + response.getBody());
        } else {
            System.out.println("Failed to fetch stations from NS API, status code: " + response.getStatusCode());
        }
    }

    public List<NsStationDto> callNsApi() {
        String url = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2/stations";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", "7b014b8a627148bcafbc86d45b22d6f0");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<NsStationResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                NsStationResponseDto.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            List<NsStationDto> allStations = response.getBody().getPayload();

            return allStations.stream()
                    .filter(station -> "NL".equalsIgnoreCase(station.getLand()))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Failed to fetch stations from NS API");
        }
    }

}
