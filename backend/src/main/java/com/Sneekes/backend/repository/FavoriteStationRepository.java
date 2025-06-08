package com.Sneekes.backend.repository;

import com.Sneekes.backend.entity.FavoriteStation;
import com.Sneekes.backend.entity.Station;
import com.Sneekes.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteStationRepository extends JpaRepository<FavoriteStation, Long> {
    List<FavoriteStation> findAllByUser(User user);

}