import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: ImmutableList
 * Created: 2017-09-08
 * Description:
 */
@SuppressWarnings("unused")
public class ImmutableList<T> extends AbstractList<T> implements List<T> {
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    private final List<T> mValues;

    public ImmutableList() {
        mValues = new ArrayList<>();
    }

    @SafeVarargs
    public ImmutableList(T... items) {
        mValues = new ArrayList<>(Arrays.asList(items));
    }

    @SafeVarargs
    private ImmutableList(Collection<T> items, T... additional) {
        ArrayList<T> tmp = new ArrayList<>(items);
        tmp.addAll(Arrays.asList(additional));
        mValues = new ArrayList<>(tmp);
    }

    @Override
    public T get(int index) {
        ArrayList<T> tmp = clone(mValues);
        return tmp.get(index);
    }


    /**
     * Due to the inner workings of this implementation, Integer values will
     * implicitly be converted to doubles. Use this method to acquire values
     * from the list if they happen to be integers.
     * @param index
     * @return
     */
    public Integer getInteger(int index) {
        ArrayList<T> tmp = clone(mValues);
        return ((Double)tmp.get(index)).intValue();
    }

    @Override
    public int size() {
        return mValues.size();
    }

    /**
     * Returns a new ImmutableList of size
     * @param e
     * @param classInstance
     * @return
     */
    public ImmutableList<T> insert(T e, Class<T> classInstance) {
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
        ArrayList<T> tmp = new ArrayList<>(mValues);
        try {
            tmp.remove(index);
        } catch (ArrayIndexOutOfBoundsException ignore) {}
        return new ImmutableList<>(tmp);
    }

    private T clone(T e, Class<T> classInstance) {
        return gson.fromJson(gson.toJson(e), classInstance);
    }

    private ArrayList clone(List<T> elements) {
        return gson.fromJson(gson.toJson(elements), ArrayList.class);
    }
}
