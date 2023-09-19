package com.ceos18.springboot;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GardenService {

  private final PlantRepository plantRepository;

  /* 모든 식물 목록을 조회한다 */
  @Transactional
  public List<Plant> fetchAllPlants(){
    return plantRepository.findAll();
  }
}
