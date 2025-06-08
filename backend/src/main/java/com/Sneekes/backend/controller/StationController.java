package com.Sneekes.backend.controller;

import com.Sneekes.backend.dto.AddToFavoriteStationRequest;
import com.Sneekes.backend.dto.StationDto;
import com.Sneekes.backend.service.StationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Set;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping("/favorite-stations")
    public ResponseEntity<Set<StationDto>> addFavoriteStation(@RequestBody AddToFavoriteStationRequest request, Authentication authentication) {
        Set<StationDto> favoriteStations = stationService.addToFavoriteStations(request.stationId(), authentication);
        return ResponseEntity.ok(favoriteStations);
    }

    @GetMapping("/favorite-stations")
    public ResponseEntity<Set<StationDto>> getFavoriteStations(Authentication authentication) {
        Set<StationDto> favoriteStations = stationService.getFavoriteStations(authentication);
        return ResponseEntity.ok(favoriteStations);
    }

    @GetMapping
    public ResponseEntity<Set<StationDto>> getAllStations() {
        Set<StationDto> Stations = stationService.getStations();
        return ResponseEntity.ok(Stations);
    }
}



