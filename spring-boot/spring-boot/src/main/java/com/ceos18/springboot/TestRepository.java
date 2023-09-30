package com.ceos18.springboot;

import com.ceos18.springboot.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {}