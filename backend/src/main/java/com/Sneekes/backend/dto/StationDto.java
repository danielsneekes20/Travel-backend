package com.Sneekes.backend.dto;

public class StationDto {
    private Long id;
    private String name;
    private String uicCode;

    public StationDto(Long id, String name,  String uicCode) {
        this.id = id;
        this.name = name;
        this.uicCode = uicCode;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getUicCode() {
        return uicCode;
    }
    // getters (en setters als nodig)
}
