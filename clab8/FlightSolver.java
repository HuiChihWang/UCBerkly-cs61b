import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Comparator;
/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are <= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private PriorityQueue<Integer> maximumPassengerQueue;
    private PriorityQueue<Flight> earlyFlights;
    private ArrayList<Flight> flights;

    public FlightSolver(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public int solve() {
        Comparator<Flight> flightComparator = Comparator.comparingInt(Flight::startTime);
        earlyFlights = new PriorityQueue<>(flightComparator);
        maximumPassengerQueue = new PriorityQueue<>(Collections.reverseOrder());

        SortFlights();

        while(!earlyFlights.isEmpty()){
            Flight firstFlight = earlyFlights.remove();
            if(earlyFlights.isEmpty()){
                maximumPassengerQueue.add(firstFlight.passengers);
                break;
            }
            Flight secondFlight = earlyFlights.remove();

            mergeFlights(firstFlight, secondFlight);
        }

        return maximumPassengerQueue.peek();
    }

    private void mergeFlights(Flight flightFirst, Flight flightSecond){
        int mergedStartTime = flightSecond.startTime;
        int mergedEndTime = Math.min(flightFirst.endTime, flightSecond.endTime);
        int mergedPassengers = flightFirst.passengers + flightSecond.passengers;

        if(mergedStartTime < mergedEndTime){
            Flight merged = new Flight(mergedStartTime, mergedEndTime, mergedPassengers);
            earlyFlights.add(merged);

            int residualStartTime = mergedEndTime;
            int residualEndTime = Math.max(flightFirst.endTime, flightSecond.endTime);
            int residualPassengers = flightFirst.endTime > flightSecond.endTime ? flightFirst.passengers:flightSecond.passengers;
            if(residualStartTime < residualEndTime){
                Flight residual = new Flight(residualStartTime,residualEndTime, residualPassengers);
                earlyFlights.add(residual);
            }
        }
        else{
            maximumPassengerQueue.add(flightFirst.passengers);
            earlyFlights.add(flightSecond);
        }
    }

    private void SortFlights(){
        for(Flight flight: flights){
            earlyFlights.add(flight);
        }
    }

    private static ArrayList<Flight> makeFlights(int[] startTimes, int[] endTimes, int[] passengerCounts) {
        ArrayList<Flight> flights = new ArrayList<>();
        for (int i = 0; i < startTimes.length; i++) {
            flights.add(new Flight(startTimes[i], endTimes[i], passengerCounts[i]));
        }
        return flights;
    }

    public static void main(String[] Args){
        int[] startTimes = {0, 7, 2, 3};
        int[] endTimes = {10, 12, 4, 8};
        int[] passengerCounts = {5, 4, 2, 7};
        FlightSolver solver = new FlightSolver(makeFlights(startTimes, endTimes, passengerCounts));


    }

}
