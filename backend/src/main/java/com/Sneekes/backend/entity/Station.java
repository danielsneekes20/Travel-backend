package com.Sneekes.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Gebruik camelCase voor velden in Java
    @Column(name = "name_long")
    private String nameLong;

    @Column(name = "uic_code")
    private String uicCode;

    @Column(name = "uic_cd_code")
    private String uicCdCode;

    @Column(name = "code")
    private String code;

    @Column(name = "name_medium")
    private String nameMedium;

    @Column(name = "name_short")
    private String nameShort;

    @Column(name = "location")
    private String location;

    @Column(name = "station_type")
    private String stationType;

    // Constructors
    public Station() {}

    // Getters en setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getUicCode() {
        return uicCode;
    }

    public void setUicCode(String uicCode) {
        this.uicCode = uicCode;
    }

    public String getUicCdCode() {
        return uicCdCode;
    }

    public void setUicCdCode(String uicCdCode) {
        this.uicCdCode = uicCdCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameMedium() {
        return nameMedium;
    }

    public void setNameMedium(String nameMedium) {
        this.nameMedium = nameMedium;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }
}
