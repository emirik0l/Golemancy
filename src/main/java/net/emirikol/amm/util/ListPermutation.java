package net.emirikol.amm.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListPermutation<T> implements Iterator {

    int index = 0;
    int current = 0;
    List<T> list;

    public ListPermutation(List<T> e) {
        list = e;
    }

    public boolean hasNext() {
        return !(index == list.size() - 1 && current == list.size() - 1);
    }

    public List<T> next() {
        if(current == index) {
            current++;
        }
        if (current == list.size()) {
            current = 0;
            index++;
        }
        List<T> output = new LinkedList<T>();
        output.add(list.get(index));
        output.add(list.get(current));
        current++;
        return output;
    }

    public void remove() {
    }

}