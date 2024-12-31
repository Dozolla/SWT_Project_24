public class Employee {
    String EmployeeID; // Employee ID
    String FirstName; // First Name
    String LastName; // Last Name
    String Position; // Position
    String Site; // Site
    Boolean fired = false; // Not Fired By Default

    public Employee(String EmployeeID, String FirstName, String LastName, String Position, String Site, Boolean fired) {
        this.EmployeeID = EmployeeID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Position = Position;
        this.Site = Site;
        this.fired = fired;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public Boolean getFired() {
        return fired;
    }

    public void setFired(Boolean fired) {
        this.fired = fired;
    }

    // First name then last name: Gui purposes
    public String toStringEmp() {
        return EmployeeID + "\t\t" + FirstName + "\t\t" + LastName + "\t\t" + Position + "\t\t" + Site + "\n";
    }

    // First name then last name
    public String toStringFL() {
        return FirstName + "\t" + LastName + "\t" + Position + "\t" + Site + "\n";
    }

    // last name then first name (print format)
    public String toPrinter() {
        return LastName + "\t" + FirstName + "\t" + Position + "\t" + Site + "\n";
    }

    public boolean equals(Employee other) {
        return this.EmployeeID.equals(other.EmployeeID);
    }

    public int compareTo(Employee other) {
        return this.EmployeeID.compareTo(other.EmployeeID);
    }

}