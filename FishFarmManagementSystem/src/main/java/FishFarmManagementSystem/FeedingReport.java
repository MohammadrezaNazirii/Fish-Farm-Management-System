package FishFarmManagementSystem;

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
public class FeedingReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedingReportID")
    @SequenceGenerator(name = "feedingReportID", sequenceName = "feedingReportID", allocationSize = 1)
    private int id;

    private LocalDate date;
    private String description;
    private double ph;
    private double temperature;
    String image_address;
    String username; //kasi ke ino misaze


    public FeedingReport(LocalDate date, String Description, double PH, double Temperature, String image_addr, String username){
        this.date = date;
        this.description = Description;
        this.ph = PH;
        this.temperature = Temperature;
        this.image_address = image_addr;
        this.username = username;
    }

    public static Integer addReport(LocalDate date, String description, double PH, double Temperature, String image_addr, String username){
//        ApplicationContext cntxt = MainUI.context;

        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);

        FeedingReport rep = new FeedingReport(date, description, PH, Temperature, image_addr, username);

        reportService.submitReport(rep);

        return 1;
    }

    public static Integer updateReport(int id, LocalDate date, String description, double PH, double Temperature, String image_addr, String username){
//        ApplicationContext cntxt = MainUI.context;

        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);

        FeedingReport rep = new FeedingReport(date, description, PH, Temperature, image_addr, username);

        reportService.updateReport(id, rep);

        return 1;
    }

    public static Integer deleteReport(int id) throws ChangeSetPersister.NotFoundException {
        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);

        reportService.deleteReport(id);

        return 1;
    }

    public static String showReports() {
        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);

        List<FeedingReport> reppp = reportService.getAllFeedingReports();

        StringBuilder temp = new StringBuilder();

        for (FeedingReport report : reppp) {
            temp.append(report.toString());
            temp.append("\n");
        }
        return temp.toString();
    }

    @Override
    public String toString() {
        return "FeedingReport{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", parameters=" + ph + " " + temperature +
                '}';
    }
    // Constructor, Getters, and Setters
}
