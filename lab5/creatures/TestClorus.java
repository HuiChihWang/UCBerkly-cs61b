package creatures;

import huglife.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;

public class TestClorus {
    @Test
    public void testClorusAttribute(){
        Clorus clorus = new Clorus(0.05);
        Clorus clorus2 = new Clorus(-1);
        assertEquals(new Color(34, 0, 231), clorus.color());
        assertEquals(0.05, clorus.energy(), 0.0001);
        assertEquals(0, clorus2.energy(), 0.0001);
    }

    @Test
    public void testClorusBasicAction(){
        Clorus clorus = new Clorus(0.05);
        Clorus clorus2 = new Clorus(-1);

        clorus.move();
        clorus2.move();
        assertEquals(0.02, clorus.energy(), 0.0001);
        assertEquals(-0.03, clorus2.energy(), 0.0001);

        clorus.stay();
        clorus2.stay();
        assertEquals(0.01, clorus.energy(), 0.0001);
        assertEquals(-0.04, clorus2.energy(), 0.0001);

        Clorus clorus3 = new Clorus(2.5);
        Clorus clorus3_child = clorus3.replicate();
        assertEquals(1.25, clorus3.energy(), 0.001);
        assertEquals(1.25, clorus3_child.energy(), 0.001);
    }

    @Test
    public void testClorusAttack(){
        Clorus clorus = new Clorus(0.03);
        Plip plip = new Plip(3);

        clorus.attack(plip);

        assertEquals(2.03, clorus.energy(), 0.001);
    }

    @Test
    public void testChoosebyClorus(){
        // No empty adjacent spaces; stay.
        Clorus clorus = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Plip());

        Action actual = clorus.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        // Any Plip is seen and has empty neighbor
        clorus = new Clorus(1.2);
        surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Empty());

        actual = clorus.chooseAction(surrounded);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
        assertEquals(expected, actual);

        // energy larger than 1 and replicate
        clorus = new Clorus(1.2);
        surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new SampleCreature());
        surrounded.put(Direction.LEFT, new SampleCreature());
        surrounded.put(Direction.RIGHT, new Impassible());

        actual = clorus.chooseAction(surrounded);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);

        // otherwise just move
        clorus = new Clorus(0.5);
        surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new SampleCreature());
        surrounded.put(Direction.LEFT, new SampleCreature());
        surrounded.put(Direction.RIGHT, new Empty());

        actual = clorus.chooseAction(surrounded);
        expected = new Action(Action.ActionType.MOVE, Direction.RIGHT);
        assertEquals(expected, actual);

    }
}
