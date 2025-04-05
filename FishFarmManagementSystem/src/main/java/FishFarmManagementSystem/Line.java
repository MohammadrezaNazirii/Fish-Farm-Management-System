package FishFarmManagementSystem;

import FishFarmManagementSystem.service.LineService;
import FishFarmManagementSystem.service.UserService;
import FishFarmManagementSystem.ui.MainUI;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.crossstore.ChangeSetPersister;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lineID")
    @SequenceGenerator(name = "lineID", sequenceName = "lineID", allocationSize = 1)
    private Integer id;

    private Integer fishCount;
    private Integer seasonId;
    private Integer poolId;
//    private Season activeSeason;
//    private List<Feeder> feeders;
    // id season


    public Line(Integer fishCount, Integer seasonId) {
        this.fishCount = fishCount;
        this.seasonId = seasonId;
    }

    public static Integer addLine(Integer fishCount, Integer seasonId){
        LineService lineService = MainUI.context.getBean(LineService.class);

        lineService.createLine(new Line(fishCount, seasonId));

        return 1;
    }

    public static Integer deleteLine(int id) throws ChangeSetPersister.NotFoundException {
        LineService lineService = MainUI.context.getBean(LineService.class);

        lineService.deleteLine(id);

        return 1;
    }

    public static Integer updateLine(int id, Integer fishCount, Integer seasonId){
        LineService lineService = MainUI.context.getBean(LineService.class);

        Line temp = new Line(fishCount, seasonId);

        lineService.updateLine(id, temp);

        return 1;
    }

    // Constructor, Getters, and Setters
}
