package com.bsuir.piris.web.controller;

import com.bsuir.piris.model.dto.DisabilityDto;
import com.bsuir.piris.service.DisabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/disabilities")
public class DisabilityController {
    private final DisabilityService disabilityService;

    @PostMapping
    public DisabilityDto save(@RequestBody DisabilityDto disabilityDto) {
        return disabilityService.save(disabilityDto);
    }

    @GetMapping
    public List<DisabilityDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<DisabilityDto> disabilityPage = disabilityService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(disabilityPage.getContent());
    }

    @GetMapping("/{id}")
    public DisabilityDto findById(@PathVariable Long id) {
        return disabilityService.findById(id);
    }

    @GetMapping("/count")
    public Long getDisabilitiesCount() {
        return disabilityService.getDisabilitiesCount();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        disabilityService.deleteById(id);
    }
}
