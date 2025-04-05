package FishFarmManagementSystem.ui;

public class Farms {
    private Integer rowNumberColumn;
    private String nameColumn;
    private String poolsColumn;
    private String feedingSpecialistColumn;
    private Integer idColumn;

    public Farms(Integer rowNumberColumn, String nameColumn, String poolsColumn, String feedingSpecialistColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.nameColumn = nameColumn;
        this.poolsColumn = poolsColumn;
        this.feedingSpecialistColumn = feedingSpecialistColumn;
        this.idColumn = idColumn;
    }

    public Integer getRowNumberColumn() {
        return rowNumberColumn;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public String getPoolsColumn() {
        return poolsColumn;
    }

    public String getFeedingSpecialistColumn() {
        return feedingSpecialistColumn;
    }

    public Integer getIdColumn() {
        return idColumn;
    }
}
