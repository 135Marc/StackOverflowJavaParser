package common;

import java.util.Comparator;

public class PostCountComparator implements Comparator<User> {
    public int compare(User u1, User u2) {
        if(u1.getPostcount() < u2.getPostcount()) return 1;
        if (u1.getPostcount() > u2.getPostcount()) return -1;
        return Long.valueOf(u2.getId()).compareTo(u1.getId());
    }
}
