package FishFarmManagementSystem.ui;

public class Users {
    private Integer rowNumberColumn;
    private String nameColumn;
    private String usernameColumn;
    private String roleColumn;
    private String passColumn;
    private Integer idColumn;

    public Users(Integer rowNumberColumn, String nameColumn, String usernameColumn, String roleColumn, String passColumn, Integer idColumn) {
        this.rowNumberColumn = rowNumberColumn;
        this.nameColumn = nameColumn;
        this.usernameColumn = usernameColumn;
        this.roleColumn = roleColumn;
        this.passColumn = passColumn;
        this.idColumn = idColumn;
    }

    public Integer getRowNumberColumn() {
        return rowNumberColumn;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public String getUsernameColumn() {
        return usernameColumn;
    }

    public String getRoleColumn() {
        return roleColumn;
    }

    public String getPassColumn() {
        return passColumn;
    }

    public Integer getIdColumn() {
        return idColumn;
    }
}
