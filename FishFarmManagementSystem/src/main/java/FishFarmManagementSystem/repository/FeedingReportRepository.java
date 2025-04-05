package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.FeedingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeedingReportRepository extends JpaRepository<FeedingReport, Integer> {
    List<FeedingReport> findAllByDate(LocalDate date);
    List<FeedingReport> findAllByDateBetween(LocalDate date1, LocalDate date2);
    List<FeedingReport> findAllByUsername(String username);
}
