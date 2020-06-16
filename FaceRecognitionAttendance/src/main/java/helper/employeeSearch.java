package helper;

/**
 * Created by 鹿若 on 2018/3/4.
 */

public class employeeSearch {
    private String EMPLOYEE_ID;
    private String Name;
    private String Password;
    private String Department;
    private String Position;

    public employeeSearch(String EMPLOYEE_ID, String name, String password, String department, String position, String faceToken) {
        this.EMPLOYEE_ID = EMPLOYEE_ID;
        Name = name;
        Password = password;
        Department = department;
        Position = position;
    }


    @Override
    public String toString() {
            return "ID: "+EMPLOYEE_ID+"\nName: "+Name+"\nPassword: "+Password+
                "\nDepartment: "+Department+"\nPosition: "+Position;

    }

    public String Noretrun(){
        return "The employee is not exists.";
    }


    public String getEMPLOYEE_ID() {
        return EMPLOYEE_ID;
    }

    public void setEMPLOYEE_ID(String EMPLOYEE_ID) {
        this.EMPLOYEE_ID= EMPLOYEE_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getPosition() {return Position;}

    public void setPosition(String position) {Position = position;}
}
