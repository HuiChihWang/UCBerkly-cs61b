package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.HashMap;

public class KDTreeTest {
    @Test
    public void TestNaivePointSet(){
        TestCase testCaseBasic = TestCase.GetTestCaseBasic();

        NaivePointSet nn = new NaivePointSet(testCaseBasic.pointList);

        for(Point p: testCaseBasic.mapInputAns.keySet()){
            assertEquals(testCaseBasic.mapInputAns.get(p), nn.nearest(p.getX(), p.getY()));
        }

        TestCase testCaseMedium = TestCase.GetTestCaseMedium();

        nn = new NaivePointSet(testCaseMedium.pointList);

        for(Point p: testCaseMedium.mapInputAns.keySet()){
            assertEquals(testCaseMedium.mapInputAns.get(p), nn.nearest(p.getX(), p.getY()));
        }
    }

    @Test
    public void TestKDTreeConstruction(){
        TestCase testCaseMedium = TestCase.GetTestCaseMedium();
        KDTree  kdtree = new KDTree(testCaseMedium.pointList);
        kdtree.printKDTree();
    }

    @Test
    public void TestNearestPointBasic(){
        TestCase testCaseBasic = TestCase.GetTestCaseBasic();
        KDTree kdtree = new KDTree(testCaseBasic.pointList);

        for(Point p: testCaseBasic.mapInputAns.keySet()){
            assertEquals(testCaseBasic.mapInputAns.get(p), kdtree.nearest(p.getX(), p.getY()));
        }
    }

    @Test
    public void TestNearestPointMedium(){
        TestCase testCaseMedium = TestCase.GetTestCaseMedium();
        KDTree kdtree = new KDTree(testCaseMedium.pointList);

        for(Point p: testCaseMedium.mapInputAns.keySet()){
            assertEquals(testCaseMedium.mapInputAns.get(p), kdtree.nearest(p.getX(), p.getY()));
        }
    }

    @Test
    public void TestCaseGeneration(){
        int pointNum = 1000;
        int testNum = 100;
        TestCase testCaseRandom = TestCase.GetRandomCase(pointNum,testNum,10);


        assertEquals(pointNum, testCaseRandom.pointList.size());
        assertEquals(testNum, testCaseRandom.mapInputAns.size());
    }

    @Test
    public void TestNearestRandomCase(){
        TestCase testCaseRandom = TestCase.GetRandomCase(10000,1000,10);
        KDTree kdtree = new KDTree(testCaseRandom.pointList);
        for(Point p: testCaseRandom.mapInputAns.keySet()){
            assertEquals(testCaseRandom.mapInputAns.get(p), kdtree.nearest(p.getX(), p.getY()));
        }
    }
}
