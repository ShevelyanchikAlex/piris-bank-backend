package com.bsuir.piris.web.controller;

import com.bsuir.piris.model.dto.NationalityDto;
import com.bsuir.piris.service.NationalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nationalities")
public class NationalityController {
    private final NationalityService nationalityService;

    @PostMapping
    public NationalityDto save(@RequestBody NationalityDto nationalityDto) {
        return nationalityService.save(nationalityDto);
    }

    @GetMapping
    public List<NationalityDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<NationalityDto> nationalityPage = nationalityService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(nationalityPage.getContent());
    }

    @GetMapping("/{id}")
    public NationalityDto findById(@PathVariable Long id) {
        return nationalityService.findById(id);
    }

    @GetMapping("/count")
    public Long getDisabilitiesCount() {
        return nationalityService.getNationalitiesCount();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        nationalityService.deleteById(id);
    }
}
