package com.bsuir.piris.web.controller;

import com.bsuir.piris.model.dto.UserDto;
import com.bsuir.piris.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<UserDto> userPage = userService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(userPage.getContent());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/count")
    public Long getUsersCount() {
        return userService.getUsersCount();
    }
}