
import com.rits.cloning.Cloner;

import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: ImmutableList
 * Created: 2017-09-08
 * Description: Represents an immutable list. That is, a list which can not be
 * modified. All operations on this list will return a copy of the source list
 * rather than modifying it. It does also performs a deep copy on all the
 * elements that are inserted and retrieved from the array.
 */
@SuppressWarnings("unused")
public class ImmutableList<T> extends AbstractList<T> implements List<T> {
    private Cloner mCloner = new Cloner();
    private final List<T> mValues;

    public ImmutableList() {
        mValues = Collections.unmodifiableList(new ArrayList<>());
    }

    @SafeVarargs
    public ImmutableList(T... items) {
        mValues = Collections.unmodifiableList(mCloner.deepClone(Arrays.asList(items)));
    }

    /**
     * Returns a new {@link ImmutableList} from the specified items.
     * @param items The items to create the list from
     * @return An Immutable list instance
     */
    @SafeVarargs
    public static <E> ImmutableList<E> of(E... items) {
        return new ImmutableList<>(items);
    }

    @SafeVarargs
    private ImmutableList(Collection<T> items, T... additional) {
        List<T> tmp = new ArrayList<>(items.size() + additional.length);
        tmp.addAll(mCloner.deepClone(items));
        tmp.addAll(mCloner.deepClone(Arrays.asList(additional)));
        List<T> t = Collections.unmodifiableList(tmp);
        mValues = Collections.unmodifiableList(tmp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(int index) {
        return mCloner.deepClone(mValues.get(index));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return mValues.size();
    }

    /**
     * This operation is an illegal operation in this implementation.
     * Please use {@link ImmutableList#sorted(Comparator)} instead
     * @param c
     */
    @Override
    public void sort(Comparator<? super T> c) {
        throw new UnsupportedOperationException("Mutating function not " +
                "implemented. Please use sorted() instead");
    }

    /**
     * Non mutating sorting method of this list. Works like the regular
     * list sort method but returns a sorted copy rather than doing in-place
     * sorting.
     * @param c the compare function to use during sort
     * @return Sorted copy of this ImmutableList
     */
    @SuppressWarnings("unchecked")
    public ImmutableList<T> sorted(Comparator<? super T> c) {
        T[] tmp = (T[])mCloner.deepClone(mValues).toArray();
        Arrays.sort(tmp, 0, tmp.length, c);
        return new ImmutableList<>(tmp);
    }

    /**
     * Returns a new ImmutableList of size n + 1 with the specified
     * value appended at the end.
     * @param e the value to add to the list
     * @return a copy of this list with the specified value at the end
     */
    public ImmutableList<T> insert(T e) {
        return new ImmutableList<>(mValues, e);
    }

    /**
     * Returns a new Immutable list of size n - 1 with all the same items as
     * the current one except for the one at index. Trying to remove an index
     * not in the list is a no op.
     * @param index index to remove from
     * @return New ImmutableList without the specified value
     */
    public ImmutableList<T> delete(int index) {
        List<T> tmp = new ArrayList<>();
        for (int i = 0; i < mValues.size(); ++i) {
            if (i != index) {
                try {
                    tmp.add(mCloner.deepClone(mValues.get(index)));
                } catch (ArrayIndexOutOfBoundsException ignore) {}
            }
        }
        return new ImmutableList<>(tmp);
    }
}
