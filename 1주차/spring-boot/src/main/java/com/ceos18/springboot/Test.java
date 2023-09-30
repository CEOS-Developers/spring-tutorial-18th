package com.ceos18.springboot;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
public class Test {

	@Id
	private Long id;
	private String name;
}
