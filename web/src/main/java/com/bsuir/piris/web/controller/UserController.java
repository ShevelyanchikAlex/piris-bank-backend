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

    @PostMapping
    public UserDto save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping
    public List<UserDto> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<UserDto> userPage = userService.findAll(PageRequest.of(page, size));
        return new ArrayList<>(userPage.getContent());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PatchMapping
    public UserDto update(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @GetMapping("/count")
    public Long getUsersCount() {
        return userService.getUsersCount();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
