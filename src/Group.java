import java.util.ArrayList;
import java.util.List;

// LUCY GARDNER GMB18183
class Group {

    private String name;
    private String email;
    private int groupSize;
    private double height;
    private String wheelchair;
    private List<String> rideTypes = new ArrayList<>();

    Group(){

    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getEmail() {
        return email;
    }

    boolean setEmail(String email) {
        if(validateEmail(email)){
            this.email = email;
            System.out.println("Thank you, your recommendations will be emailed to " + email);
            return true;
        }
        else{
            System.out.println("Sorry " + name + " " + email + " is not a valid email address. Please try again: ");
            return false;
        }
    }

    private boolean validateEmail(String email){
        return email.contains("@");
    }

    double getHeight() {
        return height;
    }

    boolean setHeight(double height) {
        if(height >= 0.7 && height < 2.5) {
            this.height = height;
            return true;
        }
        else{
            System.out.println("Please re-enter a normal height: ");
            return false;
        }
    }

    int getGroupSize() {
        return groupSize;
    }

    boolean setGroupSize(int groupSize) {
        if(groupSize > 1000){
            System.out.println("\nSorry we have a group limit of 1000 here at the Theme Park. Please enter a smaller group number: ");
            return false;
        }
        this.groupSize = groupSize;
        return true;
    }

    String isWheelchair() {
        return wheelchair;
    }

    boolean setWheelchair(String wheelchair) {
        if(!wheelchair.equals("Y") && !wheelchair.equals("N")){
            System.out.println("Please enter either capital letter Y or N: ");
            return false;
        }
        else{
            this.wheelchair = wheelchair;
            return true;
        }
    }

    List<String> getRideTypes() {
        return rideTypes;
    }

    void addRideType(String rideType) {
        rideTypes.add(rideType);

    }

    void clearRideTypes() {
        rideTypes.clear();

    }
}
