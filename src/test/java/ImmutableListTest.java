import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    private Integer[] values = new Integer[]{1, 2, 3};

    @Test
    public void sizeMustBeEqualToNrOfConstructorInputs() {
        final List<Integer> immutable = new ImmutableList<>(values);
        assertEquals(3, immutable.size());
    }

    @Test
    public void insertedValuesMatchesListState() {
        final ImmutableList<Integer> l = new ImmutableList<>(1, 2, 3);
        for (int i = 0; i < values.length; ++i) {
            assertEquals(values[i], l.get(i));
        }
    }

    @Test
    public void insertsCreatesANewList() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values);
        final ImmutableList<Integer> l2 = l1.insert(4);
        assertFalse(l1 == l2);
    }

    @Test
    public void insertElementDoesNotModifyOriginalList() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(1);
        final ImmutableList<Integer> l2 = l1.insert(4);
        assertFalse(l1.size() == l2.size());
    }

    @Test
    public void insertedElementsArePlacedLast() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values);
        final ImmutableList<Integer> l2 = l1.insert(4);
        assertEquals((Integer) 4, l2.get(l2.size() - 1));
    }

    @Test
    public void integersAreNotImplicitlyConvertedToDouble() {
        final ImmutableList<Integer> l1 = new ImmutableList<>(values).insert(4);
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

    @Test
    public void editingReturnedValueDoesNotModifyListValue() {
        List<Integer> list = new ArrayList<>(values.length);
        list.addAll(Arrays.asList(values));
        final ImmutableList<List<Integer>> l1 = new ImmutableList<>(list);
        List<Integer> tmp = l1.get(0);
        tmp.add(0);
        assertTrue(l1.get(0).size() == values.length);
    }

    @Test
    public void sortResultsInASortedList() {
        ImmutableList<Integer> l = new ImmutableList<>(values);
        ImmutableList<Integer> l2 = l.sorted((x, y) -> y - x); // Reverse
        assertEquals(l2.get(0), values[values.length - 1]);
        assertEquals(l2.get(1), values[values.length - 2]);
        assertEquals(l2.get(2), values[values.length - 3]);
    }

    @Test
    public void sortDoesNotModifySourceList() {
        ImmutableList<Integer> l = new ImmutableList<>(values);
        l.sorted((x, y) -> y - x); // Reverse
        assertEquals(l.get(0), values[0]);
        assertEquals(l.get(1), values[1]);
        assertEquals(l.get(2), values[2]);
    }

    @Test
    public void sortedStreamWorksAsOnRegularLists() {
        ImmutableList<Integer> l = new ImmutableList<>(3,2,1);
        List<Integer> l2 = l
                .stream()
                .sorted()
                .collect(Collectors.toList());
        assertEquals((Integer) 1, l2.get(0));
        assertEquals((Integer) 2, l2.get(1));
        assertEquals((Integer) 3, l2.get(2));
    }

    @Test
    public void mapAndFilterWorksAsOnRegularLists() {
        ImmutableList<Integer> l = new ImmutableList<>(values);
        List<Integer> l2 = l
                            .stream()
                            .map(x -> x * x)
                            .filter(x -> x < 8)
                            .collect(Collectors.toList());
        assertEquals(2, l2.size());
        assertEquals((Integer)1, l2.get(0));
        assertEquals((Integer)4, l2.get(1));
    }

    @Test
    public void ofReturnsANewImmutableAsConstructor() {
        ImmutableList<Integer> l = ImmutableList.of(values);
    }
}