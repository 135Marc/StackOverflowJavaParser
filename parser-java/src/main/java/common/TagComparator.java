package common;

import java.util.Comparator;

public class TagComparator implements Comparator<Tag> {
    public int compare(Tag t1,Tag t2) {
        if(t1.getCounter() < t2.getCounter()) return 1;
        if (t1.getCounter() > t2.getCounter()) return -1;
        return Long.valueOf(t1.getId()).compareTo(t2.getId());
    }
}
