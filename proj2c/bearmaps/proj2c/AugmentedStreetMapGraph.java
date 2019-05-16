package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import edu.princeton.cs.algs4.TrieSET;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private KDTree kdtreeWholeMap;
    private HashMap<Point, Node> mapPointNode;
    private HashMap<String, String> mapCleanOrigin;
    private TrieSET locationSet;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodesOnMap= this.getNodes();
        createKDTreeFromNodeSet(nodesOnMap);
        createLocationSet(nodesOnMap);
    }

    private void createKDTreeFromNodeSet(List<Node> nodesOnMap){
        List<Point> pointSetOnMap = new ArrayList<>();
        mapPointNode = new HashMap<>();

        for(Node node: nodesOnMap){
            if(!neighbors(node.id()).isEmpty()) {
                Point p = new Point(node.lon(), node.lat());
                pointSetOnMap.add(p);
                mapPointNode.put(p, node);
            }
        }
        kdtreeWholeMap = new KDTree(pointSetOnMap);
    }

    private void createLocationSet(List<Node> nodesOnMap){
        locationSet = new TrieSET();
        mapCleanOrigin = new HashMap<>();

        for(Node node: nodesOnMap){
            if(node.name() != null) {
                String cleanName = cleanString(node.name());
                locationSet.add(cleanName);
                mapCleanOrigin.put(cleanName, node.name());
            }
        }
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point near = kdtreeWholeMap.nearest(lon, lat);
        return mapPointNode.get(near).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanPrefix = cleanString(prefix);
        List<String> matchCandidates = new LinkedList<>();

        for(String matchResult: locationSet.keysWithPrefix(cleanPrefix)){
            matchCandidates.add(mapCleanOrigin.get(matchResult));
        }

        return matchCandidates;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
