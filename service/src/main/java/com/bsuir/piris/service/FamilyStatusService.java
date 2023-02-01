package com.bsuir.piris.service;

import com.bsuir.piris.model.dto.FamilyStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FamilyStatusService {
    FamilyStatusDto save(FamilyStatusDto familyStatusDto);

    FamilyStatusDto findById(Long id);

    Page<FamilyStatusDto> findAll(Pageable pageable);

    Long getFamilyStatusesCount();

    void deleteById(Long id);
}
