package common;

import java.util.Comparator;

public class PostComparator implements Comparator<Post> {

    @Override
    public int compare(Post p1, Post p2) {
        if(p1.getDate().isBefore(p2.getDate())) return 1;
        if (p1.getDate().isAfter(p2.getDate())) return -1;
        return Long.valueOf(p2.getId()).compareTo(p1.getId());
    }
}

