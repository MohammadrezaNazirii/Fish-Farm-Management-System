package FishFarmManagementSystem.service;

import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.repository.FeedingReportRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedingReportService {
    private final FeedingReportRepository repository;

    public FeedingReportService(FeedingReportRepository repository) {
        this.repository = repository;
    }

    public List<FeedingReport> getAllFeedingReports() {
        return repository.findAll();
    }

    public FeedingReport getFeedingReportById(Integer id) throws ChangeSetPersister.NotFoundException {
        return repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<FeedingReport> getAllFeedingReportsDate(LocalDate date) {
        return repository.findAllByDate(date);
    }

    public List<FeedingReport> getAllFeedingReportsBetweenDates(LocalDate date1, LocalDate date2){
        return repository.findAllByDateBetween(date1, date2);
    }

    public List<FeedingReport> getAllFeedingReportsByUsername(String username){
        return repository.findAllByUsername(username);
    }

    public FeedingReport submitReport(FeedingReport report) {
        return repository.save(report);
    }
//    public FeedingReport updateReport(Integer id, FeedingReport report) throws ChangeSetPersister.NotFoundException {
//        FeedingReport foundReport = repository.findById(id)
//                .orElseThrow(ChangeSetPersister.NotFoundException::new);
//        if (foundReport != null) {
//            if (foundReport.getDescription() != null) {
//                foundReport.setDescription(report.getDescription());
//            }
//            if (foundReport.getParameter() != null){
//                foundReport.setParameter(report.getParameter());
//            }
//            return repository.save(foundReport);
//        }
//        return null;
//    }
    public FeedingReport updateReport(Integer id, FeedingReport report){
        Optional<FeedingReport> feedingReportOptional = repository.findById(id);
        if(feedingReportOptional.isPresent()){
            FeedingReport feedingReport = feedingReportOptional.get();
            feedingReport.setDate(report.getDate());
            feedingReport.setDescription(report.getDescription());
            feedingReport.setPh(report.getPh());
            feedingReport.setTemperature(report.getTemperature());
            feedingReport.setImage_address(report.getImage_address());
            return repository.save(feedingReport);
        }
        return null;
    }

    public void deleteReport(Integer id) throws ChangeSetPersister.NotFoundException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
        else
            throw new ChangeSetPersister.NotFoundException();
    }
}
