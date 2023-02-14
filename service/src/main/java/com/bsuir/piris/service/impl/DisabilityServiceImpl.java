package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.Disability;
import com.bsuir.piris.model.dto.DisabilityDto;
import com.bsuir.piris.model.mapper.DisabilityMapper;
import com.bsuir.piris.persistence.DisabilityRepository;
import com.bsuir.piris.service.DisabilityService;
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
public class DisabilityServiceImpl implements DisabilityService {
    private static final String DISABILITY_NOT_FOUND_ERROR = "disability.not.found";

    private final DisabilityRepository disabilityRepository;
    private final DisabilityMapper disabilityMapper;
    private final IdValidator idValidator;

    @Transactional
    @Override
    public DisabilityDto save(DisabilityDto disabilityDto) {
        Disability entity = disabilityMapper.toEntity(disabilityDto);
        Disability savedDisability = disabilityRepository.save(entity);
        return disabilityMapper.toDto(savedDisability);
    }

    @Override
    public DisabilityDto findById(Long id) {
        idValidator.validate(id);
        return disabilityRepository.findById(id)
                .map(disabilityMapper::toDto)
                .orElseThrow(() -> new ServiceException(DISABILITY_NOT_FOUND_ERROR));
    }

    @Override
    public Page<DisabilityDto> findAll(Pageable pageable) {
        List<DisabilityDto> disabilityDtoList = disabilityRepository.findAll()
                .stream()
                .map(disabilityMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(disabilityDtoList, pageable, disabilityRepository.count());
    }

    @Override
    public Long getDisabilitiesCount() {
        return disabilityRepository.count();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        idValidator.validate(id);
        if (!disabilityRepository.existsById(id)) {
            throw new ServiceException(DISABILITY_NOT_FOUND_ERROR);
        }
        disabilityRepository.deleteById(id);
    }
}
