package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<Line, Integer> {
    List<Line> findAllByFishCountBetween(Integer start, Integer end);
    List<Line> findAllBySeasonId(Integer seasonId);
    List<Line> findAllByPoolId(Integer poolId);
}
