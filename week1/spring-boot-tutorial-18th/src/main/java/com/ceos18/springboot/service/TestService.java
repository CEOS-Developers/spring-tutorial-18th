package com.ceos18.springboot.service;

import com.ceos18.springboot.repository.TestRepository;
import com.ceos18.springboot.entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    /* Read All*/
    @Transactional(readOnly = true)
    public List<Test> fetchAllTests() {
        return testRepository.findAll();
    }
}