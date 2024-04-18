public class Registration {
    private String username;
    private String password;
    private String name;
    private String phone;
    Registration(String name,String username,String password,String phone){
        setName(name);
        setUsername(username);
        setPassword(password);
        setPhone(phone); 
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
