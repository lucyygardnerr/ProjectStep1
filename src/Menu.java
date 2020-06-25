// LUCY GARDNER GMB18183
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Menu{

    private Scanner scanner = new Scanner(System.in);
    private String eOrP;
    private Group group = new Group();
    private List<String> reasons = new ArrayList<>();
    private Ride ride = new Ride();

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }

    void start(){
        // Ride chosen is Rex Rampage
        System.out.println("Welcome to Rex Rampage at Time Travellers theme park!");

        printMenu();

        while(true) {
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    name();
                    break;
                case 2:
                    System.out.println("Goodbye - thank you for visiting Rex Rampage!");
                    System.exit(0);
            }
        }
    }

    private void printMenu(){
        System.out.println("\nPlease Choose an option:");
        System.out.println();
        System.out.println("1. Get your recommendations.");
        System.out.println("2. Quit.");
        System.out.println();
    }

    private void name(){
        System.out.println("Please enter information on your group so we can generate your recommendations! \n\nPlease enter your first name: ");
        String name = scanner.next();
        while(!Pattern.matches("[a-zA-Z]+", name)) {
            System.out.println("\nInvalid Name - ** Please re-enter only use upper or lower case letters **");
            name = scanner.next();
        }
        group.setName(name);
        email();
    }

    private void email(){
        System.out.println("\nHi " + group.getName() + " would you prefer me to print your recommendations or email them to you? \n ** Please enter capital E for email or P for print** ");
        eOrP = scanner.next();
        if(!eOrP.equals("E") && !eOrP.equals("P")){
            System.out.println("Invalid - please enter either E or P: ");
            eOrP = scanner.next();
        }
        if(eOrP.equals("E")){
            System.out.println("You have chosen email - please enter a valid email address: ");
            boolean validated = group.setEmail(scanner.next());
            while(!validated) {
                validated = group.setEmail(scanner.next());
            }
            setGroupSize();
        }
        else if(eOrP.equals("P")) {
            System.out.println("You have chosen to print your recommendations.");
            setGroupSize();
        }
    }

    private void setGroupSize(){
        System.out.println("\nHow many people are in your party? ");
        boolean validated = group.setGroupSize(scanner.nextInt());
        while(!validated){
            validated = group.setGroupSize(scanner.nextInt());
        }
        setHeight();
    }

    private void setHeight(){
        System.out.println("Please use the height chart next to this terminal to measure the height of the SMALLEST group in your group. \n** Please enter height in meters? e.g 1.2 **");
        boolean validated = group.setHeight(scanner.nextDouble());
        while(!validated){
            validated = group.setHeight(scanner.nextDouble());
        }
        setWheelchair();
    }

    private void setWheelchair() {
        System.out.println("Is anyone in your group a wheelchair user ? \n** Please enter capital Y for yes and N for no **");
        boolean validated = group.setWheelchair(scanner.next());
        while(!validated){
            validated = group.setWheelchair(scanner.next());
        }
        getRides();
    }


    private void getRides(){

        String input;
        List<String> types = new ArrayList<>();
        types.add("Kids");
        types.add("Water");
        types.add("Horror");
        types.add("Adrenaline");

        System.out.println("Which of the following rides does your group like? ");
        System.out.println("** Please enter capital Y for yes and N for no **");
        for (int i=0; i< types.size(); i++){
            System.out.println(types.get(i) + ": ");
            input = scanner.next();

            if(!input.equals("Y") && !input.equals("N")){
                System.out.println("Please enter either capital letter Y or N! ");
                i--;
            }
            if(input.equals("Y")){
                group.addRideType((types.get(i)));
            }
        }

        System.out.println("\nGetting your recommendations... ");
        getRecommendations();
    }

    private void getRecommendations() {

        System.out.println("\nRecommendations for " + group.getName() + "'s group created on " + setDateTime() + "->");

        LinkedBinaryTree.Node<Supplier<Boolean>> groupSizeNo = new LinkedBinaryTree.Node<>(this::addGroupSize, null,null);
        LinkedBinaryTree.Node<Supplier<Boolean>> groupSizeYes = new LinkedBinaryTree.Node<>(null, null, null);
        LinkedBinaryTree.Node<Supplier<Boolean>> checkGroupSizeNode = new LinkedBinaryTree.Node<>(this::checkGroupSize, groupSizeYes, groupSizeNo);
        LinkedBinaryTree.Node<Supplier<Boolean>> groupSizeRoot = new LinkedBinaryTree.Node<>(this::groupSizeExists, checkGroupSizeNode, null);

        LinkedBinaryTree.Node<Supplier<Boolean>> heightNo = new LinkedBinaryTree.Node<>(this::addHeight, null, null);
        LinkedBinaryTree.Node<Supplier<Boolean>> heightYes = new LinkedBinaryTree.Node<>(null, null, null);
        LinkedBinaryTree.Node<Supplier<Boolean>> checkHeightNode = new LinkedBinaryTree.Node<>(this::checkHeight, heightYes, heightNo);
        LinkedBinaryTree.Node<Supplier<Boolean>> heightRoot = new LinkedBinaryTree.Node<>(this::heightExists, checkHeightNode, null);

        LinkedBinaryTree.Node<Supplier<Boolean>> wheelchairNo = new LinkedBinaryTree.Node<>(this::addWheelchair, null, null);
        LinkedBinaryTree.Node<Supplier<Boolean>> wheelchairYes = new LinkedBinaryTree.Node<>(null, null, null);
        LinkedBinaryTree.Node<Supplier<Boolean>> checkWheelchairNode = new LinkedBinaryTree.Node<>(this::checkWheelchair, wheelchairYes, wheelchairNo);
        LinkedBinaryTree.Node<Supplier<Boolean>> wheelchairRoot = new LinkedBinaryTree.Node<>(this::wheelchairExists, checkWheelchairNode, null);

        LinkedBinaryTree.Node<Supplier<Boolean>> typesNo = new LinkedBinaryTree.Node<>(this::addTypes, null, null);
        LinkedBinaryTree.Node<Supplier<Boolean>> typesYes = new LinkedBinaryTree.Node<>(null, null, null);
        LinkedBinaryTree.Node<Supplier<Boolean>> typesRoot = new LinkedBinaryTree.Node<>(this::checkTypes, typesYes, typesNo);

        preOrder(groupSizeRoot);
        preOrder(heightRoot);
        preOrder(wheelchairRoot);
        preOrder(typesRoot);

        printEndOptions();
    }

    private void preOrder(LinkedBinaryTree.Node<Supplier<Boolean>> node) {
        if (node == null || node.getElement() == null) {
            return;
        }
        if (node.getElement().get()) {
            preOrder(node.getLeft());
        } else {
            preOrder(node.getRight());
        }
    }

    private boolean checkTypes(){
        boolean types = true;
        if(group.getRideTypes().contains("Kids")){
            types = false;
        }
        if(group.getRideTypes().size() == 0){
            types = false;
        }
        return types;
    }

    private boolean addTypes(){
        if(group.getRideTypes().contains("Kids")){
            reasons.add("- This ride is not suitable for kids.");
        }else{
            reasons.add("- This ride is in the " + ride.getTypes() + " categories");
        }
        return false;
    }

    private boolean wheelchairExists(){
        return ride.wheelchair.equals("N");
    }

    private boolean checkWheelchair(){ return group.isWheelchair().equals("N");
    }

    private boolean addWheelchair(){
        reasons.add("- This ride is not suitable for wheelchair users.");
        return false;
    }

    private boolean heightExists(){
        return ride.height != 0;
    }

    private boolean checkHeight(){
        return !(group.getHeight() < ride.height);
    }

    private boolean addHeight(){
        reasons.add("- Someone in your group is not a suitable height for this ride.");
        return false;
    }

    private boolean groupSizeExists(){
        return ride.groupSize != 0;
    }

    private boolean checkGroupSize(){
        return group.getGroupSize() == ride.groupSize;
    }

    private boolean addGroupSize(){
        reasons.add("- This ride is only suitable for groups of 2 people.");
        return false;
    }

    private void printEndOptions(){
        if(reasons.size() != 0){
            System.out.println("\nBased on your inputs Rex Rampage is not suitable for your party because: ");
            for (String reason : reasons) {
                System.out.println(reason);
            }
        }
        else{
            System.out.println("Rex Rampage is suitable for your party - enjoy!");
        }

        System.out.println("\nDo you want to revise your choices or continue? \nR to revise C to continue: ");
        String input = scanner.next();
        switch (input){
            case "R":
                reasons.clear();
                group.clearRideTypes();
                email();
                break;
            case "C":
                if(eOrP.equals("P")){
                    System.out.println("Thank you " + group.getName() + " for visiting - we hope you enjoyed your time at Rex Rampage!");
                    System.exit(0);
                }
                else{
                    System.out.println("Thank you " + group.getName() + ", your recommendations have been emailed to " + group.getEmail() + ". We hope you enjoyed your time at Rex Rampage!");
                    System.exit(0);
                }
        }
    }

    private String setDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy @ HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

}
