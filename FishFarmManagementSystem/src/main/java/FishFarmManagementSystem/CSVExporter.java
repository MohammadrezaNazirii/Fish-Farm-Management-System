package FishFarmManagementSystem;

import FishFarmManagementSystem.ui.Report;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

    public static <T> void exportToCSV(TableView<T> tableView, String fileName) throws IOException {
        ObservableList<T> rows = tableView.getItems();
        StringBuilder sb = new StringBuilder();

        // Extract column headers
        for (TableColumn<T, ?> column : tableView.getColumns()) {
            sb.append(column.getText()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1); // Remove last comma
        sb.append("\n");

        // Extract data for each row
        for (T row : rows) {
            for (TableColumn<T, ?> column : tableView.getColumns()) {
                Object cellData = column.getCellData(row);
                sb.append(cellData == null ? "null" : cellData.toString()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1); // Remove last comma
            sb.append("\n");
        }

        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)))) {
            writer.write(sb.toString());
        }
    }
}
