package FishFarmManagementSystem.service;

import FishFarmManagementSystem.Feeder;
import FishFarmManagementSystem.repository.FeederRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeederService {
    private final FeederRepository repository;

    public FeederService(FeederRepository feederRepository) {
        this.repository = feederRepository;
    }

    // Find all feeders by status
    public List<Feeder> getAllFeedersByStatus(String status) {
        return repository.findAllByStatus(status);
    }

    // Find all feeders by lineId
    public List<Feeder> getAllFeedersByLineId(Integer lineId) {
        return repository.findAllByLineId(lineId);
    }

    public List<Feeder> getAllFeeders(){
        return repository.findAll();
    }

    // Find a feeder by its id
    public Optional<Feeder> getFeederById(Integer id) {
        return repository.findById(id);
    }

    // Save a new or updated feeder
    public Feeder createFeeder(Feeder feeder) {
        return repository.save(feeder);
    }

    // Delete a feeder by its id
    public void deleteFeeder(Integer id) {
        repository.deleteById(id);
    }

    // Update an existing feeder
    public Feeder updateFeeder(Integer id, Feeder updatedFeeder) {
        Optional<Feeder> feederOptional = repository.findById(id);
        if (feederOptional.isPresent()) {
            Feeder feeder = feederOptional.get();
            feeder.setStatus(updatedFeeder.getStatus());
            feeder.setLineId(updatedFeeder.getLineId());
            return repository.save(feeder);
        }
        return null; // or throw a custom exception
    }
}
