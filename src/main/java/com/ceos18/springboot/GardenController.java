package com.ceos18.springboot;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GardenController {

  private final GardenService gardenService;

  @GetMapping("/")
  public String index() {
    return "Welcome to My Garden in Spring!";
  }

  @GetMapping("/garden")
  public List<Plant> getAllPlants() {
    return gardenService.fetchAllPlants();
  }
}
