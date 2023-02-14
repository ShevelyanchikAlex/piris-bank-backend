package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.Nationality;
import com.bsuir.piris.model.dto.NationalityDto;
import com.bsuir.piris.model.mapper.NationalityMapper;
import com.bsuir.piris.persistence.NationalityRepository;
import com.bsuir.piris.service.NationalityService;
import com.bsuir.piris.service.exception.ServiceException;
import com.bsuir.piris.service.validator.impl.IdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NationalityServiceImpl implements NationalityService {
    private static final String NATIONALITY_NOT_FOUND_ERROR = "nationality.not.found";

    private final NationalityRepository nationalityRepository;
    private final NationalityMapper nationalityMapper;
    private final IdValidator idValidator;

    @Override
    public NationalityDto save(NationalityDto nationalityDto) {
        Nationality entity = nationalityMapper.toEntity(nationalityDto);
        Nationality savedNationality = nationalityRepository.save(entity);
        return nationalityMapper.toDto(savedNationality);
    }

    @Override
    public NationalityDto findById(Long id) {
        idValidator.validate(id);
        return nationalityRepository.findById(id)
                .map(nationalityMapper::toDto)
                .orElseThrow(() -> new ServiceException(NATIONALITY_NOT_FOUND_ERROR));
    }

    @Override
    public Page<NationalityDto> findAll(Pageable pageable) {
        List<NationalityDto> nationalityDtoList = nationalityRepository.findAll()
                .stream()
                .map(nationalityMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(nationalityDtoList, pageable, nationalityRepository.count());
    }

    @Override
    public Long getNationalitiesCount() {
        return nationalityRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        idValidator.validate(id);
        if (!nationalityRepository.existsById(id)) {
            throw new ServiceException(NATIONALITY_NOT_FOUND_ERROR);
        }
        nationalityRepository.deleteById(id);
    }
}
