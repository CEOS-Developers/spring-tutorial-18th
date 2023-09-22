package com.ceos18.springboot;

import com.ceos18.springboot.Test;
import com.ceos18.springboot.TestRepository;
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