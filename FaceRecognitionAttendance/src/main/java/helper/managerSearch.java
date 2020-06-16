package helper;

/**
 * Created by 鹿若 on 2018/3/5.
 */

public class managerSearch {
    private String username;
    private String password;

    public managerSearch(String username,String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username: \n"+username+" \nPassword: \n"+password;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

}
