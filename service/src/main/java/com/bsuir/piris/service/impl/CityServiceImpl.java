package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.City;
import com.bsuir.piris.model.dto.CityDto;
import com.bsuir.piris.model.mapper.CityMapper;
import com.bsuir.piris.persistence.CityRepository;
import com.bsuir.piris.service.CityService;
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
public class CityServiceImpl implements CityService {
    private static final String CITY_NOT_FOUND_ERROR = "city.not.found";

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final IdValidator idValidator;

    @Transactional
    @Override
    public CityDto save(CityDto cityDto) {
        City entity = cityMapper.toEntity(cityDto);
        City savedCity = cityRepository.save(entity);
        return cityMapper.toDto(savedCity);
    }

    @Override
    public CityDto findById(Long id) {
        idValidator.validate(id);
        return cityRepository.findById(id)
                .map(cityMapper::toDto)
                .orElseThrow(() -> new ServiceException(CITY_NOT_FOUND_ERROR));
    }

    @Override
    public Page<CityDto> findAll(Pageable pageable) {
        List<CityDto> cityDtoList = cityRepository.findAll()
                .stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(cityDtoList, pageable, cityRepository.count());
    }

    @Override
    public Long getCitiesCount() {
        return cityRepository.count();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        idValidator.validate(id);
        if (!cityRepository.existsById(id)) {
            throw new ServiceException(CITY_NOT_FOUND_ERROR);
        }
        cityRepository.deleteById(id);
    }
}
