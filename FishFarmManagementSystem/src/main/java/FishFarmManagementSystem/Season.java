package FishFarmManagementSystem;

import FishFarmManagementSystem.service.FeedingReportService;
import FishFarmManagementSystem.service.SeasonService;
import FishFarmManagementSystem.ui.MainUI;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seasonID")
    @SequenceGenerator(name = "seasonID", sequenceName = "seasonID", allocationSize = 1)
    private int id;

    private String typeOfFish;
    private LocalDate startDate;
    private LocalDate endDate;

    public Season(String typeOfFish, LocalDate startDate, LocalDate endDate) {
        this.typeOfFish = typeOfFish;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //    private List<Line> lines; /////instead Season Id in lines

    public static Integer addSeason(String typeOfFish, LocalDate startDate, LocalDate endDate){
        SeasonService seasonService = MainUI.context.getBean(SeasonService.class);

        Season season = new Season(typeOfFish, startDate, endDate);

        seasonService.createSeason(season);

        return 1;
    }

    public boolean isSeasonActive() {
        LocalDate now = LocalDate.now();
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    // Constructor, Getters, and Setters
}
