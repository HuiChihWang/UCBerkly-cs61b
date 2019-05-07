package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> pointList;

    public NaivePointSet(List<Point> pointList){
        if(pointList == null || pointList.isEmpty())
            throw new IllegalArgumentException();

        this.pointList = pointList;
    }

    @Override
    public Point nearest(double x, double y) {
        Point input = new Point(x,y);

        Point near = pointList.get(0);
        double minDist = Point.distance(input, near);

        for(Point pt: pointList){
            double distFromInput = Point.distance(pt, input);
            if(minDist > distFromInput){
                minDist = distFromInput;
                near = pt;
            }
        }

        return new Point(near.getX(), near.getY());
    }
}
