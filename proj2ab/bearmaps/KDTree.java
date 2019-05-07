package bearmaps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;

public class KDTree {
    public static int COMPARE_X = 0;
    public static int COMPARE_Y = 1;

    private KDTreeNode KDTreeRoot;
    private ArrayList<Comparator<Point>> compareArray;
    private HashSet<Point> pointInTree;

    public KDTree(List<Point> pointList){
        pointInTree = new HashSet<>();
        CreateComparator();
        CreateKDTree(pointList);
    }

    public Point nearest(double x, double y){
        return null;
    }

    public void printKDTree(){
        printKDTreePreOrder(KDTreeRoot);
    }

    private void printKDTreePreOrder(KDTreeNode root){
        if(root == null)
            return;
        System.out.println(root);
        printKDTreePreOrder(root.leftNode);
        printKDTreePreOrder(root.rightNode);
    }


    private void CreateComparator(){
        compareArray  = new ArrayList<>();
        compareArray.add(Comparator.comparingDouble(Point::getX));
        compareArray.add(Comparator.comparingDouble(Point::getY));
    }

    private void CreateKDTree(List<Point> pointList){
        Iterator<Point> itPointList = pointList.iterator();

        KDTreeRoot = new KDTreeNode(itPointList.next(), COMPARE_X);

        while(itPointList.hasNext()){
            Point p = itPointList.next();
            if(!contains(p)){
                addPointToTree(p);
            }
        }
    }

    private void addPointToTree(Point p){
        KDTreeNode start = KDTreeRoot;
        KDTreeNode prev = null;
        boolean fromLeft = false;

        while(start != null){
            prev = start;

            if(start.smaller(p)){
                fromLeft = false;
                start = start.rightNode;
            }
            else{
                fromLeft = true;
                start = start.leftNode;
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

    private class KDTreeNode{


        public Point point;
        public KDTreeNode leftNode;
        public KDTreeNode rightNode;
        public int compareChooseIdx;

        public KDTreeNode(Point point, int comparator){
            this.point = point;
            leftNode = null;
            rightNode = null;
            compareChooseIdx = comparator;
        }

        public boolean smaller(Point point){
            return compareArray.get(compareChooseIdx).compare(this.point, point) < 0;

        }

        @Override
        public String toString() {
            String compareOption = compareChooseIdx == COMPARE_X ? "COMPARE_X" : "COMPARE_Y";
            return String.format("(%.2f, %.2f, %s)",point.getX(), point.getY(), compareOption);
        }
    }
}
