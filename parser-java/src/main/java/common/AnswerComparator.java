package common;

import java.util.Comparator;

public class AnswerComparator implements Comparator<Post> {

    @Override
    public int compare(Post a1,Post a2) {
        if (a1.getScore() < a2.getScore()) return 1;
        if (a1.getScore() > a2.getScore()) return -1;
        return Long.valueOf(a2.getId()).compareTo(a1.getId());
    }
}