package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.*;

public class Clorus extends Creature {

    /* Color of Clorus */
    private int r = 34;
    private int g = 0;
    private int b = 231;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        energy = e;
        energy = Math.max(0, energy);
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r,g,b);
    }

    /**
     * Clorus should lose 0.03 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        // TODO
        energy  -= 0.03;
        // energy = Math.max(0, energy);

    }

    /**
     * Clorus lose 0.01 energy when staying.
     */
    public void stay() {
        // TODO
        energy -= 0.01;
        // energy = Math.max(0, energy);

    }

    /**
     * Clorus and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Clorus.
     */
    public Clorus replicate() {
        energy *= 0.5;
        return new Clorus(energy);
    }

    /**
     * Clorus attacks some creature and then gain all energy from attacked creature
     * @param c:creature attacked by this Clorus
     */

    public void attack(Creature c){
        energy += c.energy();
    }

    /**
     * Action Rule
     * 1. If there are no empty squares, the Clorus will STAY
     *   (even if there are Plips nearby they could attack since plip squares do not count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     *    it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     * @param neighbors: neighbor of this Clorus
     * @return Action: Action type reacted by this Clorus
     */

    public Action chooseAction(Map<Direction, Occupant> neighbors){
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> NeighborPlips = new ArrayDeque<>();
        boolean anyPlip = false;

        for (Direction d: neighbors.keySet()){
            String neighborName = neighbors.get(d).name();
            if(neighborName == "empty")
                emptyNeighbors.add(d);
            else if(neighborName == "plip")
                NeighborPlips.add(d);
        }

        // Rule 1
        if(emptyNeighbors.isEmpty())
            return new Action(Action.ActionType.STAY);

        // Rule 2
        if(!NeighborPlips.isEmpty())
            return new Action(Action.ActionType.ATTACK, choose_random_direction(NeighborPlips));

        //Rule 3
        if(energy >= 1)
            return new Action(Action.ActionType.REPLICATE, choose_random_direction(emptyNeighbors));

        //Rule 4
        return new Action(Action.ActionType.MOVE, choose_random_direction(emptyNeighbors));
    }


    /* helper function */
    private Direction choose_random_direction(Deque<Direction> Neighbors){
        int chosen_idx = new Random().nextInt(Neighbors.toArray().length);
        Direction chosen_dir = (Direction) Neighbors.toArray()[chosen_idx];
        return chosen_dir;
    }
}
