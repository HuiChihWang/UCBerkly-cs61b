package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.HashMap;

public class KDTreeTest {

    private class TestCase{
        public List<Point> pointList;
        public HashMap<Point, Point> mapInputAns;

        public TestCase(List<Point> pointList, List<Point> inputList, List<Point> ansList){
            if(inputList.size() != ansList.size()){
                throw new IllegalArgumentException(" Ans List Size Not Equal To Input List Size");
            }

            this.pointList = pointList;
            mapInputAns = new HashMap<>();
            for(int i = 0; i < inputList.size(); ++i)
                mapInputAns.put(inputList.get(i), ansList.get(i));
        }
    }


    private TestCase GetTestCaseBasic(){
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        List<Point> pointList = List.of(p1, p2, p3);

        p1 = new Point(3.0, 4.0);
        p2 = new Point(-0.3, 1.);
        p3 = new Point(-5., -4.);
        List<Point> inputList = List.of(p1, p2, p3);


        p1 = new Point(3.3, 4.4);
        p2 = new Point(1.1, 2.2);
        p3 = new Point(-2.9, 4.2);
        List<Point> ansList = List.of(p1, p2, p3);

        return new TestCase(pointList, inputList, ansList);
    }

    @Test
    public void TestNaivePointSet(){
        TestCase testCaseBasic = GetTestCaseBasic();

        NaivePointSet nn = new NaivePointSet(testCaseBasic.pointList);

        for(Point p: testCaseBasic.mapInputAns.keySet()){
            assertEquals(testCaseBasic.mapInputAns.get(p), nn.nearest(p.getX(), p.getY()));
        }
    }

    @Test
    public void TestKDTreeConstruction(){
        TestCase testCaseBasic = GetTestCaseBasic();
        KDTree  kdtree = new KDTree(testCaseBasic.pointList);
        kdtree.printKDTree();
    }
}
