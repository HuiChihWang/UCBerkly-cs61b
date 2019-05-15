import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bed> sortedBeds;
    private List<Bear> bears;
    private List<Bed> beds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        this.bears = bears;
        this.beds = beds;
        sortedBeds = new ArrayList<>();

        SolveByQuickSort();

    }

    private void SolveByQuickSort(){
        ArrayList<Bed> smaller = new ArrayList<>();
        ArrayList<Bed> greater = new ArrayList<>();
        HashMap<Bed, Boolean> mapState = new HashMap<>();

        Bear pivot = bears.get(0);
        partitionInitial(pivot, mapState);

        for(int i = 1; i < bears.size(); ++i){
            pivot = bears.get(i);

            if(sortedBeds.get(sortedBeds.size()-1).compareTo(pivot) > 0){
                partitionLeft(pivot, mapState);
            }
            else if(sortedBeds.get(sortedBeds.size()-1).compareTo(pivot) < 0){
                partitionRight(pivot, smaller, greater);
            }


        }
    }

    private void partitionRight(Bear pivot, HashMap<Bed, Boolean>){
        for(int i = 0; i < greater.size(); ++i){
            if(pivot.compareTo(greater.get(i)) >= 0){
                Bed bedRm = greater.remove(i);

                if(pivot.compareTo(bedRm) == 0)
                    sortedBeds.add(bedRm);
                else {
                    smaller.add(bedRm);
                }
            }
        }
    }

    private void partitionLeft(Bear pivot, ArrayList<Bed> smaller, ArrayList<Bed> greater){
        for(int i = 0; i < smaller.size(); ++i){
            if(pivot.compareTo(smaller.get(i)) <= 0){
                Bed bedRm = smaller.remove(i);

                if(pivot.compareTo(bedRm) == 0)
                    sortedBeds.add(bedRm);
                else {
                    smaller.add(bedRm);
                }
            }
        }
    }

    private void partitionInitial(Bear pivot, HashMap<Bed, Boolean> mapState){
        for(Bed bed: beds){
            if(bed.compareTo(pivot) < 0) {
                mapState.put(bed, true);
            }
            else if(bed.compareTo(pivot) > 0){
                mapState.put(bed, false);
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
        return bears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return sortedBeds;
    }
}
