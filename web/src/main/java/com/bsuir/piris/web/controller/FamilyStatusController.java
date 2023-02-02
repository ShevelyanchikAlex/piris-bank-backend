package com.bsuir.piris.web.controller;

import com.bsuir.piris.model.dto.FamilyStatusDto;
import com.bsuir.piris.service.FamilyStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/family-statuses")
public class FamilyStatusController {
    private final FamilyStatusService familyStatusService;

    @PostMapping
    public FamilyStatusDto save(@RequestBody FamilyStatusDto familyStatusDto) {
        return familyStatusService.save(familyStatusDto);
    }

    @GetMapping
    public List<FamilyStatusDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<FamilyStatusDto> familyStatusPage = familyStatusService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(familyStatusPage.getContent());
    }

    @GetMapping("/{id}")
    public FamilyStatusDto findById(@PathVariable Long id) {
        return familyStatusService.findById(id);
    }

    @GetMapping("/count")
    public Long getDisabilitiesCount() {
        return familyStatusService.getFamilyStatusesCount();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        familyStatusService.deleteById(id);
    }
}
