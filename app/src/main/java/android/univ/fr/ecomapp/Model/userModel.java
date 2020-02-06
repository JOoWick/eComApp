package android.univ.fr.ecomapp.Model;

public class userModel {
    private String Name, Password, Phone;
    public userModel(){

    }

    public userModel(String name, String password, String phone) {
        Name = name;
        Password = password;
        Phone = phone;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
