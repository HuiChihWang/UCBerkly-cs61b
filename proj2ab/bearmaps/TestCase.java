package bearmaps;

import java.util.HashMap;
import java.util.List;

public class TestCase{
    List<Point> pointList;
    HashMap<Point, Point> mapInputAns;

    TestCase(List<Point> pointList, List<Point> inputList, List<Point> ansList){
        if(inputList.size() != ansList.size()){
            throw new IllegalArgumentException(" Ans List Size Not Equal To Input List Size");
        }

        this.pointList = pointList;
        mapInputAns = new HashMap<>();
        for(int i = 0; i < inputList.size(); ++i)
            mapInputAns.put(inputList.get(i), ansList.get(i));
    }

    public static TestCase GetTestCaseBasic(){
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

    public static TestCase GetTestCaseMedium(){
        Point p1 = new Point(2., 3.);
        Point p2 = new Point(4., 2.);
        Point p3 = new Point(4., 5.);
        Point p4 = new Point(3., 3.);
        Point p5 = new Point(1., 5.);
        Point p6 = new Point(4., 4.);
        List<Point> pointList = List.of(p1, p2, p3, p4, p5, p6);

        p1 = new Point(0.0, 7.0);
        List<Point> inputList = List.of(p1);

        p1 = new Point(1.,5.);
        List<Point> ansList = List.of(p1);

        return new TestCase(pointList, inputList, ansList);
    }

}


