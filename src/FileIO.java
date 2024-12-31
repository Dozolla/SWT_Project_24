import java.io.File;
import java.util.Scanner;

import javafx.stage.FileChooser;

public class FileIO {

    File fileName;

    public SetArr<Employee> readInFile() {
        SetArr<Employee> employeesArray = new SetArr<Employee>();
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(null);
            setFile(file);
            System.out.println("set the file in gui");

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] employeeInfo = line.split(" ");
                String FirstName = employeeInfo[0];
                String LastName = employeeInfo[1];
                String Position = employeeInfo[2];
                String Site = employeeInfo[3];
                String EmployeeID = Site.substring(0, 1) + "-" + FirstName.substring(0, 3).toUpperCase()
                        + LastName.substring(0, 1).toUpperCase() + "-" + "01";
                Employee employee = new Employee(EmployeeID, FirstName, LastName, Position, Site, false);
                employeesArray.add(employee);
                System.out.println("employee added");
                System.out.println(employee.toString());
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeesArray;
    }

    // set file method
    public void setFile(File fileName) {
        System.out.println("setFile was called");
        this.fileName = fileName;
    }

    // get file method
    public File getFile() {
        System.out.println("getFile was called");
        return fileName;
    }

}