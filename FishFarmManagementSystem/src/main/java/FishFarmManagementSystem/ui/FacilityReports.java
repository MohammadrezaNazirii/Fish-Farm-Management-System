package FishFarmManagementSystem.ui;

public class FacilityReports {
    private Integer rowNumberColumn;
    private String dateColumn;
    private String descriptionColumn;
    private Integer idColumn;

    public FacilityReports(Integer rowNumberColumn, String dateColumn, String descriptionColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.dateColumn = dateColumn;
        this.descriptionColumn = descriptionColumn;
        this.idColumn = idColumn;
    }

    public Integer getRowNumberColumn() {
        return rowNumberColumn;
    }

    public String getDateColumn() {
        return dateColumn;
    }

    public String getDescriptionColumn() {
        return descriptionColumn;
    }

    public Integer getIdColumn() {
        return idColumn;
    }
}
