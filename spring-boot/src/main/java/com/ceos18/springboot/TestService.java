package com.ceos18.springboot;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
