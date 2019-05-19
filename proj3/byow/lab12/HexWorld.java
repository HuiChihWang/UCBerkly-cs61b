package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private TETile[][] world;
    private Random randGen;
    private int size;
    private int width;
    private int height;


    HexWorld(int size){
        this.size = size;
        randGen = new Random();
        createRandomWorld();
    }

    public void draw(){
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(world);
    }

    private void initWorld(){
        width = 11 * size - 6;
        height = 10 * size;
        world = new TETile[width][height];
        for(int i = 0; i < width; ++i){
            for(int j = 0; j < height; ++j){
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    private void createRandomWorld(){
        initWorld();
        ArrayList<Integer[]> drawPosList = getDrawingPosition();
        for(Integer[] pos: drawPosList){
            addHexagon(pos[0], pos[1], randGen.nextInt(5));
        }

    }

    private ArrayList<Integer[]> getDrawingPosition(){
        ArrayList<Integer[]> result = new ArrayList<>();

        int[] sizeRef = {1, 2, 3, 2, 3, 2, 3, 2, 1};
        int intervalX = 4*size - 2;
        int intervalY = size;

//        for(int i = 0; i < 3; ++i){
//            Integer[] pos = {startX, startY};
//            result.add(pos);
//            startX += intervalX;
//        }

        int startY = 0;
        for(int blockSize: sizeRef){
            int startX = size - 1 + intervalX / 2 * (3 - blockSize);


            for(int blockIdx = 0; blockIdx < blockSize; ++blockIdx){
                Integer[] pos = {startX, startY};
                result.add(pos);
                startX += intervalX;
            }

            startY += intervalY;
        }

        return result;
    }


    private void addHexagon(int startX, int startY, int type){
        int fillCountX = size;
        int fillStartX = startX;

        for(int yIdx = startY; yIdx < startY+size; ++yIdx){
            for(int xIdx = fillStartX; xIdx < fillStartX+fillCountX; ++xIdx){
                world[xIdx][yIdx] = SelectType(type);
            }
            fillCountX += 2;
            fillStartX -= 1;
        }

        fillCountX -= 2;
        fillStartX += 1;
        for(int yIdx = startY+size; yIdx < startY+2*size; ++yIdx){
            for(int xIdx = fillStartX; xIdx < fillStartX + fillCountX; ++xIdx){
                world[xIdx][yIdx] = SelectType(type);
            }
            fillCountX -= 2;
            fillStartX += 1;
        }
    }


    private TETile SelectType(int typeIdx){
        switch (typeIdx){
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args){
        HexWorld h = new HexWorld(4);
        h.draw();
    }



}
