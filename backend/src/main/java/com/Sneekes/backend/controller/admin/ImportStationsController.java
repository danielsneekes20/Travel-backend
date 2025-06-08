package com.Sneekes.backend.controller.admin;

import com.Sneekes.backend.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/stations")
public class ImportStationsController {
    private final StationService stationService;

    public ImportStationsController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping("/update-stations")
    public ResponseEntity<Void> updateStations(){
        stationService.fetchAndSaveStationsFromApi();
//        stationService.logRawResponseFromNsApi();
        return ResponseEntity.ok().build();    }
}

