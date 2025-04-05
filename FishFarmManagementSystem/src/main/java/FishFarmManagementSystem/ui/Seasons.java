package FishFarmManagementSystem.ui;

public class Seasons {
    private Integer rowNumberColumn;
    private String speciesColumn;
    private String startDateColumn;
    private String endDateColumn;
    private Integer idColumn;

    public Seasons(Integer rowNumberColumn, String speciesColumn, String startDateColumn, String endDateColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.speciesColumn = speciesColumn;
        this.startDateColumn = startDateColumn;
        this.endDateColumn = endDateColumn;
        this.idColumn = idColumn;
    }

    public Integer getRowNumberColumn() {
        return rowNumberColumn;
    }

    public String getSpeciesColumn() {
        return speciesColumn;
    }

    public String getStartDateColumn() {
        return startDateColumn;
    }

    public String getEndDateColumn() {
        return endDateColumn;
    }

    public Integer getIdColumn() {
        return idColumn;
    }
}
