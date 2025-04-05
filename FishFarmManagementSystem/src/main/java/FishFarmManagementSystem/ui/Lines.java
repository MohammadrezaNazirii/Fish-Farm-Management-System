package FishFarmManagementSystem.ui;

public class Lines {
    private Integer rowNumberColumn;
    private Integer fishCountColumn;
    private String seasonColumn;
    private Integer idColumn;

    public Lines(Integer rowNumberColumn, Integer fishCountColumn, String seasonColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.fishCountColumn = fishCountColumn;
        this.seasonColumn = seasonColumn;
        this.idColumn = idColumn;
    }

    public Integer getRowNumberColumn() {
        return rowNumberColumn;
    }

    public Integer getFishCountColumn() {
        return fishCountColumn;
    }

    public String getSeasonColumn() {
        return seasonColumn;
    }

    public Integer getIdColumn() {
        return idColumn;
    }
}
