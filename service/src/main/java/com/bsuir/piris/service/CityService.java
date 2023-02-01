package com.bsuir.piris.service;

import com.bsuir.piris.model.dto.CityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {
    CityDto save(CityDto cityDto);

    CityDto findById(Long id);

    Page<CityDto> findAll(Pageable pageable);

    Long getCitiesCount();

    void deleteById(Long id);
}
