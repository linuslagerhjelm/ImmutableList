import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: ImmutableListTest
 * Created: 2017-09-08
 * Description:
 */
public class ImmutableListTest {

    private static Integer[] values = new Integer[]{1, 2, 3};

    @Test
    public void sizeMustBeEqualToNrOfConstructorInputs() {
        final List<Integer> immutable = new ImmutableList<>(values);
        assertEquals(3, immutable.size());
    }

    @Test
    public void insertsCreatesANewList() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values);
        final ImmutableList<Integer> l2 = l1.insert(4, Integer.class);
        assertFalse(l1 == l2);
    }

    @Test
    public void insertElementDoesNotModifyOriginalList() {
        final ImmutableList<Integer> l1 = new ImmutableList<>();
        final ImmutableList<Integer> l2 = l1.insert(4, Integer.class);
        assertFalse(l1.size() == l2.size());
    }

    @Test
    public void insertedElementsArePlacedLast() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values).insert(4, Integer.class);
        assertEquals((Integer) 4, l1.getInteger(l1.size() - 1));
    }

    @Test
    public void integersAreNotImplicitlyConvertedToDouble() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values).insert(4, Integer.class);
        List<Integer> l2 = l1
                            .stream()
                            .filter(Integer.class::isInstance)
                            .collect(Collectors.toList());
        assertEquals(l1.size(), l2.size());
    }

    @Test
    public void removeShrinksList() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values);
        final ImmutableList<Integer> l2 = l1.delete(0);
        assertFalse(l1.size() == l2.size());
    }

    @Test
    public void removeDoesNotModifyOriginalList() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values);
        l1.delete(0);
        assertTrue(l1.size() == values.length);
    }

    @Test
    public void removeOutsideListIsANoOp() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values);
        l1.delete(-1);
    }
}