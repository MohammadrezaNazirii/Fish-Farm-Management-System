package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {
    List<Season> findAllByStartDateBetween(LocalDate date1, LocalDate date2);
    List<Season> findAllByEndDateBetween(LocalDate date1, LocalDate date2);
    List<Season> findAllByTypeOfFish(String fish);
}
