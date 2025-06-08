CREATE TABLE stations (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          uic_code VARCHAR(20),
                          uic_cd_code VARCHAR(20),
                          code VARCHAR(20),
                          name_long VARCHAR(255),
                          name_medium VARCHAR(255),
                          name_short VARCHAR(255),
                          location VARCHAR(255),
                          station_type VARCHAR(255),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite_stations (
                                   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                   user_id BIGINT NOT NULL,
                                   station_id BIGINT NOT NULL,
                                   added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   UNIQUE KEY unique_user_station (user_id, station_id),
                                   CONSTRAINT fk_fav_user FOREIGN KEY (user_id) REFERENCES users(id),
                                   CONSTRAINT fk_fav_station FOREIGN KEY (station_id) REFERENCES stations(id)
);

CREATE TABLE tracks (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        station_id BIGINT NOT NULL,
                        name VARCHAR(10) NOT NULL,
                        CONSTRAINT fk_track_station FOREIGN KEY (station_id) REFERENCES stations(id)
);