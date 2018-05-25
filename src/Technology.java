import java.util.ArrayList;
import java.util.List;

/*
    Technology is what a civilization use for building infrastructure.
 */
class Technology {
    List<Integer[]> branches;

    Technology(ArrayList<Integer[]> branches){
        this.branches = branches;
    }

    List<Integer[]> getBranches() {
        return branches;
    }
}
