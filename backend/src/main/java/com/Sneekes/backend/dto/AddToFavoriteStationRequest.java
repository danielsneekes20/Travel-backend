package com.Sneekes.backend.dto;

public record AddToFavoriteStationRequest(
        Long stationId,
        Long userId
) {
}
