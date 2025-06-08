package com.Sneekes.backend.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NsStationDto {

    @JsonProperty("UICCode")
    private String uicCode;
    private String uicCdCode;
    private String code;
    private String land;
    private Namen namen;

    public void setUicCdCode(String uicCdCode) {
        this.uicCdCode = uicCdCode;
    }

    public String getUicCdCode() {
        return uicCdCode;
    }

    // Required by Jackson
    public NsStationDto() {}

    public String getUicCode() {
        return uicCode;
    }

    public void setUicCode(String uicCode) {
        this.uicCode = uicCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Namen getNamen() {
        return namen;
    }

    public void setNamen(Namen namen) {
        this.namen = namen;
    }


    public static class Namen {
        private String lang;
        private String middel;
        private String kort;

        public Namen() {}

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getMiddel() {
            return middel;
        }

        public void setMiddel(String middel) {
            this.middel = middel;
        }

        public String getKort() {
            return kort;
        }

        public void setKort(String kort) {
            this.kort = kort;
        }
    }
}