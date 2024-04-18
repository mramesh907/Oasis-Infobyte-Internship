
// Importing files
import java.io.FileWriter;
import java.io.IOException;
//OR YOU CAN SIMPLY IMPORT java.io.*;
class UserInfo extends Registration{
    int balance;
    UserInfo(String name,String username,String password,String phone,String transaction,String Filepath ){

        super(name,username,password,phone);
        this.balance=0;
        try{
            FileWriter fileWriter=new FileWriter(Filepath+this.getUsername()+".txt",true);
        fileWriter.write(this.balance +"\r\n"+this.getName()+"\r\n"+this.getUsername()+"\r\n"+this.getPassword()+"\r\n"+this.getPhone()+"\r\n"+transaction+"\r\n");
        fileWriter.close();
        //content saved successfully
        }catch(IOException e){
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }//end of UserInfo constructor
    void message(){
        System.out.println("USER GENERATED SUCCESSFULLY !!");
    }
}