package com.bsuir.piris.web.controller;

import com.bsuir.piris.model.dto.DepositDto;
import com.bsuir.piris.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deposits")
public class DepositController {
    private final DepositService depositService;

    @PostMapping
    public DepositDto save(@RequestBody DepositDto depositDto) {
        return depositService.save(depositDto);
    }

    @GetMapping
    public List<DepositDto> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<DepositDto> depositDtoPage = depositService.findAll(PageRequest.of(page, size));
        return new ArrayList<>(depositDtoPage.getContent());
    }

    @GetMapping("/{id}")
    public DepositDto findById(@PathVariable Long id) {
        return depositService.findById(id);
    }

    @GetMapping("/count")
    public Long getDepositsCount() {
        return depositService.getDepositsCount();
    }

    @PutMapping("/close-deposit/{id}")
    public void closeDeposit(@PathVariable Long id) {
        depositService.closeDeposit(id);
    }

    @PostMapping("/close-day/{monthAmount}")
    public void closeDay(@PathVariable Integer monthAmount) {
        depositService.closeDay(monthAmount);
    }
}
