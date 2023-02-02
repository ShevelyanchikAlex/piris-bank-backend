package com.bsuir.piris.web.controller;

import com.bsuir.piris.model.dto.CityDto;
import com.bsuir.piris.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping
    public CityDto save(@RequestBody CityDto cityDto) {
        return cityService.save(cityDto);
    }

    @GetMapping
    public List<CityDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<CityDto> cityPage = cityService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(cityPage.getContent());
    }

    @GetMapping("/{id}")
    public CityDto findById(@PathVariable Long id) {
        return cityService.findById(id);
    }

    @GetMapping("/count")
    public Long getCitiesCount() {
        return cityService.getCitiesCount();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        cityService.deleteById(id);
    }
}
