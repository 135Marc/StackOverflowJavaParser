package common;

public class Answer extends Post {
    private int parentid;

    public Answer () {
        super();
        this.parentid=0;
    }

    public Answer (int parentid) {
        this.parentid = parentid;
    }

    public Answer (Answer a) {
        super(a);
        this.parentid = a.getParentid();
    }
    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public Answer clone() {
        return new Answer(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Parent_ID: ")
                .append(getParentid());
        return super.toString() + sb.toString();
    }
}
