// LUCY GARDNER GMB18183
import java.util.ArrayList;
import java.util.List;

class Ride {

    int groupSize = 2;
    int height;
    String wheelchair = "N";
    private List<String> types = new ArrayList<>();

    Ride(){
        types.add("Water");
        types.add("Adrenaline");
        types.add("Horror");
    }

    List<String> getTypes() {
        return types;
    }

}
