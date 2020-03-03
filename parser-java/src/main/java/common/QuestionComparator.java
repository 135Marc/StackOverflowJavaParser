package common;

import java.util.Comparator;

public class QuestionComparator implements Comparator<Post> {

    @Override
    public int compare(Post a1,Post a2) {
        if (((Question)a1).getAnswercount() < ((Question) a2).getAnswercount()) return 1;
        if (((Question)a1).getAnswercount() > ((Question) a2).getAnswercount()) return -1;
        return Long.valueOf(a2.getId()).compareTo(a1.getId());
    }
}
