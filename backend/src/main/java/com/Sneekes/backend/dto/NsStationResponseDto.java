package com.Sneekes.backend.dto;

import java.util.List;

public class NsStationResponseDto {
    private List<NsStationDto> payload;

    public List<NsStationDto> getPayload() {
        return payload;
    }

    public void setPayload(List<NsStationDto> payload) {
        this.payload = payload;
    }
}
