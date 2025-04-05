package FishFarmManagementSystem.service;

import FishFarmManagementSystem.Farm;
import FishFarmManagementSystem.Line;
import FishFarmManagementSystem.Pool;
import FishFarmManagementSystem.repository.FarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmService {
    private final FarmRepository repository;

    public FarmService (FarmRepository repository){
        this.repository = repository;
    }

    public List<Farm> getAllFarms() {
        return repository.findAll();
    }

    public Optional<Farm> getFarmById(Integer id) {
        return repository.findById(id);
    }

    public List<Farm> getAllFarmsByName(String name){
        return repository.findAllByName(name);
    }

    public List<Farm> getAllFarmsByFeedingSpecialistId(Integer feedingSpecialistId) {
        return repository.findAllByFeedingSpecialistId(feedingSpecialistId);
    }

    public Farm createFarm(Farm farm) {
        return repository.save(farm);
    }

    public Farm updateFarm(Integer id, Farm farm){
        Optional<Farm> farmOptional = repository.findById(id);
        if(farmOptional.isPresent()){
            Farm farm1 = farmOptional.get();
            farm1.setName(farm.getName());
            farm1.setFeedingSpecialistId(farm.getFeedingSpecialistId());
            return repository.save(farm1);
        }
        return null;
    }

    public void deleteFarm(Integer id) {
        repository.deleteById(id);
    }
}
