package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.Farm;
import FishFarmManagementSystem.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {
    List<Farm> findAllByName(String name);
    List<Farm> findAllByFeedingSpecialistId(Integer feedingSpecialistId);
}
