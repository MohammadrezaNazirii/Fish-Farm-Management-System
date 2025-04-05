package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.FacilityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FacilityReportRepository extends JpaRepository<FacilityReport, Integer> {
    List<FacilityReport> findAllByDate(LocalDate date);
    List<FacilityReport> findAllByDateBetween(LocalDate date1, LocalDate date2);
    List<FacilityReport> findAllByUsername(String username);
}
