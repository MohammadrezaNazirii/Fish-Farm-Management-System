package FishFarmManagementSystem.ui;

import java.time.LocalDate;

public class Report {
    private Integer rowNumberColumn;
    private String dateColumn;
    private String descriptionColumn;
    private Double temperatureColumn;
    private Double phColumn;
    private Integer idColumn;

    public Report(Integer rowNumberColumn, String dateColumn, String descriptionColumn, Double temperatureColumn, Double phColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.dateColumn = dateColumn;
        this.descriptionColumn = descriptionColumn;
        this.temperatureColumn = temperatureColumn;
        this.phColumn = phColumn;
        this.idColumn = idColumn;
    }

    public Report(Integer rowNumberColumn, String dateColumn, String descriptionColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.dateColumn = dateColumn;
        this.descriptionColumn = descriptionColumn;
        this.idColumn = idColumn;
    }

    public Integer getRowNumberColumn() {
        return rowNumberColumn;
    }

    public void setRowNumberColumn(Integer rowNumberColumn) {
        this.rowNumberColumn = rowNumberColumn;
    }

    public String getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(String dateColumn) {
        this.dateColumn = dateColumn;
    }

    public String getDescriptionColumn() {
        return descriptionColumn;
    }

    public void setDescriptionColumn(String descriptionColumn) {
        this.descriptionColumn = descriptionColumn;
    }

    public Double getTemperatureColumn() {
        return temperatureColumn;
    }

    public void setTemperatureColumn(Double temperatureColumn) {
        this.temperatureColumn = temperatureColumn;
    }

    public Double getPhColumn() {
        return phColumn;
    }

    public void setPhColumn(Double phColumn) {
        this.phColumn = phColumn;
    }

    public Integer getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(Integer idColumn) {
        this.idColumn = idColumn;
    }
}
