package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.FamilyStatus;
import com.bsuir.piris.model.dto.FamilyStatusDto;
import com.bsuir.piris.model.mapper.FamilyStatusMapper;
import com.bsuir.piris.persistence.FamilyStatusRepository;
import com.bsuir.piris.service.FamilyStatusService;
import com.bsuir.piris.service.exception.ServiceException;
import com.bsuir.piris.service.validator.impl.IdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyStatusServiceImpl implements FamilyStatusService {
    private static final String FAMILY_STATUS_NOT_FOUND_ERROR = "family_status.not.found";

    private final FamilyStatusRepository familyStatusRepository;
    private final FamilyStatusMapper familyStatusMapper;
    private final IdValidator idValidator;

    @Transactional
    @Override
    public FamilyStatusDto save(FamilyStatusDto familyStatusDto) {
        FamilyStatus entity = familyStatusMapper.toEntity(familyStatusDto);
        FamilyStatus savedFamilyStatus = familyStatusRepository.save(entity);
        return familyStatusMapper.toDto(savedFamilyStatus);
    }

    @Override
    public FamilyStatusDto findById(Long id) {
        idValidator.validate(id);
        return familyStatusRepository.findById(id)
                .map(familyStatusMapper::toDto)
                .orElseThrow(() -> new ServiceException(FAMILY_STATUS_NOT_FOUND_ERROR));
    }

    @Override
    public Page<FamilyStatusDto> findAll(Pageable pageable) {
        List<FamilyStatusDto> familyStatusDtoList = familyStatusRepository.findAll()
                .stream()
                .map(familyStatusMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(familyStatusDtoList, pageable, familyStatusRepository.count());
    }

    @Override
    public Long getFamilyStatusesCount() {
        return familyStatusRepository.count();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        idValidator.validate(id);
        if (!familyStatusRepository.existsById(id)) {
            throw new ServiceException(FAMILY_STATUS_NOT_FOUND_ERROR);
        }
        familyStatusRepository.deleteById(id);
    }
}
