package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.Feeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeederRepository extends JpaRepository<Feeder, Integer> {
    List<Feeder> findAllByStatus(String status);
    List<Feeder> findAllByLineId(Integer lineId);
}
