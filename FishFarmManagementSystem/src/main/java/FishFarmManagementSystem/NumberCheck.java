package FishFarmManagementSystem;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;

public class NumberCheck {

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);  // Try to parse as integer
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String str) {
        try {
            Double.parseDouble(str);  // Try to parse as float (double)
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumber(String str) {
        return isInteger(str) || isFloat(str);
    }

    public static void error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطا");
        alert.setHeaderText("وضعیت فیلد های ورودی");
        alert.setContentText("خطا در فیلد های ورودی، لطفاً دوباره تلاش کنید.");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
