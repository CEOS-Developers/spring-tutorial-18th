package com.ceos18.springboot.controller;

import com.ceos18.springboot.entity.Test;
import com.ceos18.springboot.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tests")
public class TestController {

    private final TestService testService;

    @GetMapping
    public List<Test> getAllTest() {
        return testService.fetchAllTests();
    }

}