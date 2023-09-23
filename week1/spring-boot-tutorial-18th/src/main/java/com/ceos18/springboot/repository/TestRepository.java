package com.ceos18.springboot.repository;

import com.ceos18.springboot.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {}