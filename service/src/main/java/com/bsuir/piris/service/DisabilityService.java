package com.bsuir.piris.service;

import com.bsuir.piris.model.dto.DisabilityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DisabilityService {
    DisabilityDto save(DisabilityDto disabilityDto);

    DisabilityDto findById(Long id);

    Page<DisabilityDto> findAll(Pageable pageable);

    Long getDisabilitiesCount();

    void deleteById(Long id);
}
