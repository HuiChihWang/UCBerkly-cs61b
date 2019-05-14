import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> sortedBears;
    private List<Bed> sortedBeds;
    private List<Bear> bears;
    private List<Bed> beds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        this.bears = bears;
        this.beds = beds;
        sortedBears = new ArrayList<>();
        sortedBeds = new ArrayList<>();

    }

    private void SolveByQuickSort(){
        ArrayList<Bed> smaller = new ArrayList<>();
        ArrayList<Bed> greater = new ArrayList<>();


        Bear pivot = bears.get(0);
        partitionInitial(pivot, smaller, greater);

        for(int i = 1; i < bears.size(); ++i){

            if(sortedBeds.get(sortedBeds.size()-1).compareTo(pivot) > 0){
                partition(pivot, smaller, greater);
            }
            else if(sortedBeds.get(sortedBeds.size()-1).compareTo(pivot) < 0){

            }


        }
    }

    private void partition(ArrayList<Bed> input, int pivotIdx){


    }

    private void partitionInitial(Bear pivot, ArrayList<Bed> smaller, ArrayList<Bed> greater){
        for(Bed bed: beds){
            if(bed.compareTo(pivot) < 0) {
                smaller.add(bed);
            }
            else if(bed.compareTo(pivot) > 0){
                greater.add(bed);
            }
            else{
                sortedBeds.add(bed);
            }
        }
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        return null;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return null;
    }
}
