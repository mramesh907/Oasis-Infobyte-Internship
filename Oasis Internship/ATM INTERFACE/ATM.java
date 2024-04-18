import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ATM {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String currentPath = "./";

        boolean b = true;
        while (b) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();// here it is used for clear screeen
                                                                                 // and here start() throws IOException
                                                                                 // and waitFor thows
                                                                                 // InterruptedException
            System.out.println("\n------WELLCOME TO ATM------");
            System.out.println("1.Register New User\n2.Login for existing user\n3.Exit from ATM\nEnter Your Choice : ");
            int choice1 = 3;
            if (sc.hasNextInt()) {
                choice1 = sc.nextInt();// take user choice
                // sc.nextLine();

            }
            brea: { // labelled break
                    // Register with name,username, password, phone and check if account already
                    // exist

                if (choice1 == 1) {
                    System.out.print("Enter Account Holder Name:");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.println("\nEnter Username(it must be unique): ");
                    String username = sc.nextLine();
                    // File
                    File file = new File(currentPath + username + ".txt");
                    if (file.exists()) {
                        System.out.println("Username Alredy Taken! ");
                        System.out.println("Press enter to continue");
                        try {
                            System.in.read();// take one character
                        } catch (Exception e) {
                            break brea;// go to brea
                        }
                    } // if for check file exists or not
                    Console console = System.console();
                    if (console == null) {
                        System.out.println("Could not get consle instance");
                        System.exit(0);// This indicates that the program terminated normally
                    }
                    char[] passwordarray = console
                            .readPassword("Enter Your Password(It won't show due to security purpose):\n");
                    String password = new String(passwordarray);// char---->String
                    System.out.println("Your password has " + password.length() + " characters.");
                    int ph_length = 0;
                    String number = null;
                    do {
                        System.out.println("Enter Your Phone Number(10 digit): ");
                        number = sc.nextLine();
                        ph_length = number.length();
                    } while (ph_length != 10);
                    UserInfo reg = new UserInfo(name, username, password, number, "TRANSACTION:0", currentPath);
                    reg.message();
                    System.out.println("\nPress enter to continue !!");
                    try {
                        System.in.read();
                    } catch (Exception e) {
                        // no statement
                    }

                } // if() for choice1==1

                else if (choice1 == 2) {
                    System.out.println();
                    System.out.println("Enter Username: ");
                    sc.nextLine();
                    String username = sc.nextLine();

                    Console console = System.console();
                    if (console == null) {
                        // If System.console() returns null, it typically means that the program is not
                        // running in an environment that supports direct console access.
                        System.out.println("Couldn't get console instance");
                        System.exit(0);
                    }
                    char[] passwordarray = console
                            .readPassword("Enter your secreat password(It won't show due to security reasons):\n");
                    String password = new String(passwordarray);
                    boolean bool = true;// not know
                    File file = new File(currentPath + username + ".txt");
                    // Now check file exists or not
                    if (file.exists()) {
                        try {
                            Scanner dataReader = new Scanner(file);
                            String money = dataReader.nextLine();
                            int login_money = Integer.parseInt(money);
                            System.out.println("money:" + login_money);
                            // int login_money = dataReader.nextInt();
                            String login_name = dataReader.nextLine();
                            System.out.println("ORGINAL name:" + login_name);
                            System.out.println("entered username:" + username);
                            String login_username = dataReader.nextLine();
                            System.out.println("orginal username:" + login_username);
                            System.out.println("entered password:" + password);
                            String login_password = dataReader.nextLine();
                            System.out.println("orginal password:" + login_password);
                            String login_number = dataReader.nextLine();
                            String login_transaction = dataReader.nextLine();

                            if (username.equals(login_username) && password.equals(login_password)) {
                                // ----------------------------- Yes Password and Username match
                                boolean bo = true;
                                // menu for logged in customers
                                while (bo) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Welcome " + login_name + " !");
                                    System.out.println("\n****OPERATIONS****");
                                    System.out.println(
                                            "1.Deposit Money\n2.Withdraw Money\n3.View Account Details\n4.View Transaction History\n5.Transfer to other account\n6.Logout \n");
                                    System.out.println("Enter Choice :");
                                    int choice2 = sc.nextInt();

                                    // deposit
                                    if (choice2 == 1) {
                                        System.out.println("Enter Amount to Deposit(upto 50,000): ");
                                        int amount = sc.nextInt();
                                        if (amount < 0 || amount > 50000) {
                                            System.out.println("Enter Correct Amount !");
                                            System.out.println("\nPress enter to continue");
                                            try {
                                                System.in.read();
                                            } catch (Exception e) {

                                            }
                                        } // end of check amount
                                        else {
                                            try {
                                                FileWriter fw = new FileWriter(currentPath + username + ".txt", true);
                                                String old_money = Integer.toString(login_money);
                                                login_money += amount;// deposit
                                                System.out.println("Rs. " + amount + " Deposited !");
                                                int temp = amount;
                                                String to_be_deposited = Integer.toString(login_money);
                                                modifyFile(currentPath + username + ".txt", old_money, to_be_deposited);
                                                SimpleDateFormat dateFormat = new SimpleDateFormat(
                                                        "dd/MM/yyyy HH:mm:ss");
                                                Date date = new Date();
                                                fw.write("Rs.(+" + temp + ") :: " + dateFormat.format(date)
                                                        + " :: Self Deposit " + "\n");
                                                login_transaction = "TRANSACTION:1";
                                                modifyFile(currentPath + username + ".txt", "TRANSACTION:0",
                                                        "TRANSACTION:1");
                                                System.out.println("\nPress enter to continue");
                                                try {
                                                    System.in.read();
                                                } catch (Exception e) {

                                                }
                                                fw.close();

                                            } catch (IOException e) {
                                                System.out.println("User Data Not Found! ");
                                                e.printStackTrace();
                                            }
                                        }
                                    } // end of choice2==1 //deposit end

                                    // withdraw method start
                                    else if (choice2 == 2) {
                                        System.out.println(
                                                "Enter Amount to Withdraw : (Limit : 0 to " + login_money + ")");
                                        int amount_withdraw = sc.nextInt();
                                        // Validation
                                        if (amount_withdraw < 0 || amount_withdraw > login_money) {
                                            System.out.println("Enter correct amount !");
                                            System.out.println("\nPress enter to continue");
                                            try {
                                                System.in.read();
                                            } catch (Exception e) {
                                            }
                                        } else {
                                            try {
                                                // Open file, Replace original amount with updated with time also
                                                FileWriter f0 = new FileWriter(currentPath + username + ".txt", true);
                                                String old_money = Integer.toString(login_money);
                                                login_money -= amount_withdraw;
                                                int temp1 = amount_withdraw;
                                                String to_be_withdrawed = Integer.toString(login_money);
                                                modifyFile(currentPath + username + ".txt", old_money,
                                                        to_be_withdrawed);

                                                SimpleDateFormat formatter = new SimpleDateFormat(
                                                        "dd/MM/yyyy HH:mm:ss");
                                                Date date = new Date();
                                                f0.write("Rs.(-" + temp1 + ") :: " + formatter.format(date)
                                                        + " :: Self Withdraw" + "\n");

                                                login_transaction = "TRANSACTION:1";
                                                modifyFile(currentPath + username + ".txt", "TRANSACTION:0",
                                                        "TRANSACTION:1");

                                                System.out.println("Rs. " + temp1 + " Withdrawed !");

                                                System.out.println("\nPress enter to continue");
                                                try {
                                                    System.in.read();
                                                } catch (Exception e) {
                                                }

                                                f0.close();
                                            } catch (IOException e) {
                                                System.out.println("User Data not found !");
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    // withdraw start
                                    else if (choice2 == 3) {
                                        System.out.println("\nDetails :----");
                                        System.out.println("1. Name          :" + login_name);
                                        System.out.println("2. Username      :" + login_username);
                                        System.out.println("3. Password      :" + login_password);
                                        System.out.println("4. Phone No.     :" + login_number);
                                        System.out.println("5. Balance       :" + login_money + " Rs.");
                                        System.out.println("\nPree enter to continue");
                                        try {
                                            System.in.read();
                                        } catch (Exception e) {

                                        }
                                    } // end of choice2==3

                                    // Transaction history (Show only if TRANSACTION:1 is there, Don't show if
                                    else if (choice2 == 4) {
                                        try {
                                            File f1 = new File(currentPath + username + ".txt");
                                            dataReader = new Scanner(f1);
                                            System.out.println("****TRANSACTION HISTORY****");
                                            String temp = "TRANSACTION:0";
                                            if (login_transaction.equals(temp)) {
                                                System.out.println("No Transaction History is available !");
                                                System.out.println("\nPress enter to continue");
                                                try {
                                                    System.in.read();
                                                } catch (Exception e) {

                                                }
                                            } else {
                                                for (int j = 0; j < 6; j++) {
                                                    dataReader.nextLine();
                                                }
                                                while (dataReader.hasNextLine()) {
                                                    String fileData = dataReader.nextLine();
                                                    System.out.println(fileData);
                                                }
                                                System.out.println("\nPress enter to continue");
                                                try {
                                                    System.in.read();
                                                } catch (Exception e) {

                                                }
                                            }
                                            dataReader.close();
                                        } catch (FileNotFoundException fexception) {
                                            System.out.println("Unexpected error occurred! ");
                                            fexception.printStackTrace();
                                            System.out.println("\nPress enter to continue");
                                            try {
                                                System.in.read();
                                            } catch (Exception e) {

                                            }
                                        }
                                    } // end of choice2==4

                                    // Transfer to other account with username and update amount and transaction
                                    // history in both user account
                                    else if (choice2 == 5) {
                                        System.out.println("Enter Username of receiver account: ");
                                        sc.nextLine();
                                        String username_to_transfer = sc.nextLine();
                                        File file_to_transfer = new File(currentPath + username_to_transfer + ".txt");
                                        if (file_to_transfer.exists()) {
                                            // dataReader=new Scanner(file_to_transfer);
                                            Scanner dataReader2 = new Scanner(file_to_transfer);
                                            String money_old = dataReader2.nextLine();
                                            String name_transfer = dataReader2.nextLine();
                                            int money_old_user = Integer.parseInt(money_old);
                                            System.out.println(
                                                    "Enter Amount to Transfer : (Limit : 0 to " + login_money + ")");
                                            int amount_transfer = sc.nextInt();
                                            if (amount_transfer <= 0 || amount_transfer > login_money) {
                                                System.out.println("Enter correct amount !!");
                                                System.out.println("\nPress enter to continue");
                                                try {
                                                    System.in.read();
                                                } catch (Exception e) {

                                                }
                                            } else {
                                                String to_upd1 = Integer.toString(login_money);
                                                login_money -= amount_transfer;
                                                String to_upd2 = Integer.toString(login_money);
                                                modifyFile(currentPath + username + ".txt", to_upd1, to_upd2);

                                                String to_update = Integer.toString(money_old_user);
                                                money_old_user += amount_transfer;// receiver received money
                                                String to_update2 = Integer.toString(money_old_user);
                                                modifyFile(currentPath + username_to_transfer + ".txt", to_update,
                                                        to_update2);
                                                modifyFile(currentPath + username_to_transfer + ".txt", "TRANSACTION:0",
                                                        "TRASACTION:1");
                                                try {
                                                    FileWriter fw2 = new FileWriter(
                                                            currentPath + username_to_transfer + ".txt", true);
                                                    SimpleDateFormat formatter = new SimpleDateFormat(
                                                            "dd/MM/yyyy HH:mm:ss");
                                                    Date date = new Date();
                                                    fw2.write("Rs.(+" + amount_transfer + ") :: "
                                                            + formatter.format(date) + " :: Transferred from "
                                                            + username + "(" + login_name + ")\n");
                                                    fw2.close();
                                                    FileWriter fw3 = new FileWriter(currentPath + username + ".txt",
                                                            true);
                                                    SimpleDateFormat formatter2 = new SimpleDateFormat(
                                                            "dd/MM/yyyy HH:mm:ss");
                                                    Date date2 = new Date();
                                                    fw3.write("Rs.(-" + amount_transfer + "):: "
                                                            + formatter2.format(date2) + ":: Transferred to "
                                                            + username_to_transfer + " (" + name_transfer + ")\n");
                                                    fw3.close();
                                                    System.out.println("Rs.(" + amount_transfer
                                                            + ") Transferred to " + username_to_transfer + " ( "
                                                            + name_transfer + " )");
                                                    System.out.println("\nPress enter to continue");
                                                    try {
                                                        System.in.read();
                                                    } catch (Exception e) {
                                                    }
                                                } catch (IOException e) {
                                                    System.out.println("User Data Not found!");
                                                    e.printStackTrace();
                                                }
                                            }
                                            dataReader2.close();
                                        } else {
                                            System.out.println("User Does't Exists! ");
                                            System.out.println("\nPress enter to continue");
                                            try {
                                                System.in.read();
                                            } catch (Exception e) {
                                            }
                                        }
                                    } // end of choice2==5
                                    else {
                                        break brea;
                                    }
                                } // while
                                sc.close();
                                bool = false;
                                break brea;

                            } // if username and password check
                            dataReader.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found !");
                            e.printStackTrace();
                            System.out.println("\nPress enter to continue");
                            try {
                                System.in.read();
                            } catch (Exception f) {
                            }
                        }
                    } else { // end of file exists if()
                        System.out.println("User not registered! ");
                        System.out.println("\nPress enter to continue");
                        try {
                            System.in.read();
                        } catch (Exception e) {

                        }
                    } // end of else

                    if (bool) {
                        System.out.println("Username or Password Incorrect !\nPlease Try Again");
                        System.out.println("\nPress enter to continue");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }
                        break brea;
                    }
                } // else if() for choice 2
                else if (choice1 == 3) {
                    System.out.println("\n***** Thank you for using ATM *****");
                    System.out.println("\nPress enter to continue");
                    try {
                        System.in.read();
                    } catch (Exception e) {
                    }
                    sc.close();
                    b = false;
                } else {
                    System.out.println("Enter correct number input !");
                    System.out.println("\nPress enter to continue");
                    try {
                        System.in.read();
                    } catch (Exception e) {
                    }
                }

                // else if() for choice 2

            } // end of brea
            System.out.println();
        } // end of 1st while
    }// end of main() method

    // Replacing string in file function (static)
    static void modifyFile(String filePath, String oldString, String newString) {
        // File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        // FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            // Reading all the lines of input text file into oldContent
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }
            // Replacing oldString with newString in the oldContent
            String newContent = oldContent.replaceFirst(oldString, newString);

            // Rewriting the input text file with newContent
            new FileWriter(filePath, false).close();
            FileWriter writer = new FileWriter(filePath);
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Closing the resources
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}// end of ATM class