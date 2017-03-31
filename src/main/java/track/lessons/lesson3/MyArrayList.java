package track.lessons.lesson3;

import java.util.NoSuchElementException;
import java.lang.System;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private static final int DEFAULT_SIZE = 50;
    private int[] array;

    public MyArrayList() {
        super();
        array = new int[DEFAULT_SIZE];
    }

    public MyArrayList(int capacity) {
        super();
        array = new int[capacity];
    }

    @Override
    public void add(int item) {
        if (size() == array.length) {
            int[] newarray;
            newarray = new int[(array.length + 1) * 2];
            System.arraycopy(array, 0, newarray, 0, array.length);
            array = newarray;
        }
        //System.out.println("array len " + array.length + " size " + size);
        array[size()]  = item;
        incrementSize();
    }

    @Override
    public int remove(int idx) throws NoSuchElementException {
        if ((idx < 0) || (idx >= size())) {
            throw new NoSuchElementException();
        }

        int res = array[idx];
        int it;

        for (it = idx; it <= size() - 2; it++) {
            array[it] = array[it + 1];
        }
        decrementSize();
        return res;
    }

    @Override
    public int get(int idx) throws NoSuchElementException {
        if ((idx < 0) || (idx >= size())) {
            throw new NoSuchElementException();
        }
        return array[idx];
    }
}
