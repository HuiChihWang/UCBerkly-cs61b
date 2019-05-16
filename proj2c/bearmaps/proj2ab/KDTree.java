package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;

public class  KDTree {
    private static int COMPARE_X = 0;
    private static int COMPARE_Y = 1;

    private KDTreeNode KDTreeRoot;
    private ArrayList<Comparator<Point>> compareArray;
    private HashSet<Point> pointInTree;

    public KDTree(List<Point> pointList){
        pointInTree = new HashSet<>();
        CreateComparator();
        CreateKDTree(pointList);
    }

    public Point nearest(double x, double y){
        Point findNearPoint = FindNearestRecursive(KDTreeRoot, new Point(x,y), null);
        return new Point(findNearPoint.getX(), findNearPoint.getY());
    }


    private void CreateComparator(){
        compareArray  = new ArrayList<>();
        compareArray.add(Comparator.comparingDouble(Point::getX));
        compareArray.add(Comparator.comparingDouble(Point::getY));
    }

    private void CreateKDTree(List<Point> pointList){
        Iterator<Point> itPointList = pointList.iterator();

        Point pointRoot = itPointList.next();
        KDTreeRoot = new KDTreeNode(pointRoot, COMPARE_X);
        pointInTree.add(pointRoot);

        while(itPointList.hasNext()){
            Point p = itPointList.next();
            if(!contains(p)){
                addPointToTree(p);
                pointInTree.add(p);
            }
        }
    }

    private void addPointToTree(Point p){
        KDTreeNode start = KDTreeRoot;
        KDTreeNode prev = null;
        boolean fromLeft = false;

        while(start != null){
            prev = start;

            if(start.greater(p)){
                fromLeft = true;
                start = start.leftNode;
            }
            else{
                fromLeft = false;
                start = start.rightNode;
            }
        }
        CreateNewNode(p, prev, fromLeft);
    }

    private void CreateNewNode(Point p, KDTreeNode prev, boolean fromLeft){
        int comparator = prev.compareChooseIdx == COMPARE_X ? COMPARE_Y : COMPARE_X;
        if(fromLeft){
            prev.leftNode = new KDTreeNode(p, comparator);
        }
        else{
            prev.rightNode = new KDTreeNode(p, comparator);
        }
    }

    private boolean contains(Point p){
        return pointInTree.contains(p);
    }

    private Point FindNearestRecursive(KDTreeNode root, Point inputPoint, Point nearestPoint){
        if(root == null)
            return nearestPoint;

        if(nearestPoint == null || root.DistSqFrom(inputPoint) < Point.distance(inputPoint, nearestPoint)){
            nearestPoint = root.point;
        }

        KDTreeNode nextGoodSide;
        KDTreeNode nextBadSide;

        if(root.greater(inputPoint)){
            nextGoodSide = root.leftNode;
            nextBadSide = root.rightNode;
        }
        else{
            nextGoodSide = root.rightNode;
            nextBadSide = root.leftNode;
        }

        nearestPoint = FindNearestRecursive(nextGoodSide, inputPoint, nearestPoint);


        if(Point.distance(inputPoint, nearestPoint) > root.BoundaryDistSqFrom(inputPoint)){
            nearestPoint = FindNearestRecursive(nextBadSide, inputPoint, nearestPoint);
        }

        return nearestPoint;
    }

    private class KDTreeNode{
        Point point;
        KDTreeNode leftNode;
        KDTreeNode rightNode;
        int compareChooseIdx;

        KDTreeNode(Point point, int comparator){
            this.point = point;
            leftNode = null;
            rightNode = null;
            compareChooseIdx = comparator;
        }

        boolean greater(Point point){
            return compareArray.get(compareChooseIdx).compare(this.point, point) > 0;

        }

        double BoundaryDistSqFrom(Point p){
            return compareChooseIdx == COMPARE_X ? Math.pow(p.getX() - point.getX(), 2) : Math.pow(p.getY() - point.getY(), 2);
        }

        double DistSqFrom(Point p){
            return Point.distance(point, p);
        }

        @Override
        public String toString() {
            String compareOption = compareChooseIdx == COMPARE_X ? "COMPARE_X" : "COMPARE_Y";
            return String.format("(%.2f, %.2f, %s)",point.getX(), point.getY(), compareOption);
        }
    }

    public static void main(String[] Args){
        Comparator<Point> cmp = Comparator.comparingDouble(Point::getX);
        int i = cmp.compare(new Point(4.,5.), new Point(4.,4.));
    }
}
