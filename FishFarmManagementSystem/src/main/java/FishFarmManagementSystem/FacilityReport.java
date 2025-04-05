package FishFarmManagementSystem;

import FishFarmManagementSystem.repository.FacilityReportRepository;
import FishFarmManagementSystem.service.FacilityReportService;
import FishFarmManagementSystem.service.FeedingReportService;
import FishFarmManagementSystem.ui.MainUI;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class FacilityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facilityReportID")
    @SequenceGenerator(name = "facilityReportID", sequenceName = "facilityReportID", allocationSize = 1)
    private int id;

    private LocalDate date;
    private String description;
    String image_address;
    String username; //kasi ke ino misaze


    public FacilityReport(LocalDate date, String Description, String image_addr, String username){
        this.date = date;
        this.description = Description;
        this.image_address = image_addr;
        this.username = username;
    }

    public static Integer addReport(LocalDate date, String description, String image_addr, String username){
        FacilityReportService reportService = MainUI.context.getBean(FacilityReportService.class);

        FacilityReport rep = new FacilityReport(date, description, image_addr, username);

        reportService.submitReport(rep);

        return 1;
    }
//
    public static Integer updateReport(int id, LocalDate date, String description, String image_addr, String username){
        FacilityReportService reportService = MainUI.context.getBean(FacilityReportService.class);

        FacilityReport rep = new FacilityReport(date, description, image_addr, username);

        reportService.updateReport(id, rep);

        return 1;
    }
//
    public static Integer deleteReport(int id) throws ChangeSetPersister.NotFoundException {
        FacilityReportService reportService = MainUI.context.getBean(FacilityReportService.class);

        reportService.deleteReport(id);

        return 1;
    }
//
//    public static String showReports() {
//        FacilityReportService reportService = MainUI.context.getBean(FacilityReportService.class);
//
//        List<FacilityReport> reppp = reportService.getAllFacilityReports();
//
//        StringBuilder temp = new StringBuilder();
//
//        for (FacilityReport report : reppp) {
//            temp.append(report.toString());
//            temp.append("\n");
//        }
//        return temp.toString();
//    }

    // Constructor, Getters, and Setters
}
