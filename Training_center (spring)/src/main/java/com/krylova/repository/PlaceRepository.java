package com.krylova.repository;

import com.krylova.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository  extends JpaRepository<Place, Long> {
}
