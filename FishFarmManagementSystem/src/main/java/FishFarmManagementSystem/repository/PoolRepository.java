package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoolRepository extends JpaRepository<Pool, Integer> {
    List<Pool> findAllByFarmId(Integer farmId);
    List<Pool> findAllByOtherEquipmentsContains(String otherEquipments);
    List<Pool> findAllBySizeBetween(double size1, double size2);
}
