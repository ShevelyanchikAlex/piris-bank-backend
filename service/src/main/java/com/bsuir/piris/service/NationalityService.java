package com.bsuir.piris.service;

import com.bsuir.piris.model.dto.NationalityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NationalityService {
    NationalityDto save(NationalityDto nationalityDto);

    NationalityDto findById(Long id);

    Page<NationalityDto> findAll(Pageable pageable);

    Long getNationalitiesCount();

    void deleteById(Long id);
}
