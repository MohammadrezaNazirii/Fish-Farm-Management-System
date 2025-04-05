package FishFarmManagementSystem.service;

import FishFarmManagementSystem.FacilityReport;
import FishFarmManagementSystem.repository.FacilityReportRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityReportService {
    private final FacilityReportRepository repository;

    public FacilityReportService(FacilityReportRepository repository) {
        this.repository = repository;
    }

    public List<FacilityReport> getAllFacilityReports() {
        return repository.findAll();
    }

    public FacilityReport getFacilityReportById(Integer id) throws ChangeSetPersister.NotFoundException {
        return repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<FacilityReport> getAllFacilityReportsDate(LocalDate date) {
        return repository.findAllByDate(date);
    }

    public List<FacilityReport> getAllFacilityReportsBetweenDates(LocalDate date1, LocalDate date2){
        return repository.findAllByDateBetween(date1, date2);
    }

    public List<FacilityReport> getAllFacilityReportsByUsername(String username){
        return repository.findAllByUsername(username);
    }

    public FacilityReport submitReport(FacilityReport report) {
        return repository.save(report);
    }

    public FacilityReport updateReport(Integer id, FacilityReport report){
        Optional<FacilityReport> facilityReportOptional = repository.findById(id);
        if(facilityReportOptional.isPresent()){
            FacilityReport facilityReport = facilityReportOptional.get();
            facilityReport.setDate(report.getDate());
            facilityReport.setDescription(report.getDescription());
            facilityReport.setImage_address(report.getImage_address());
            return repository.save(facilityReport);
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
