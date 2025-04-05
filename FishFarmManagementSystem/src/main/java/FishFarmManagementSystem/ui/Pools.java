package FishFarmManagementSystem.ui;

public class Pools {
    private Integer rowNumberColumn;
    private Double sizeColumn;
    private String linesColumn;
    private String otherEquipmentColumn;
    private Integer idColumn;

    public Pools(Integer rowNumberColumn, Double sizeColumn, String linesColumn, String otherEquipmentColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.sizeColumn = sizeColumn;
        this.linesColumn = linesColumn;
        this.otherEquipmentColumn = otherEquipmentColumn;
        this.idColumn = idColumn;
    }

    public Integer getRowNumberColumn() {
        return rowNumberColumn;
    }

    public Double getSizeColumn() {
        return sizeColumn;
    }

    public String getLinesColumn() {
        return linesColumn;
    }

    public String getOtherEquipmentColumn() {
        return otherEquipmentColumn;
    }

    public Integer getIdColumn() {
        return idColumn;
    }
}
