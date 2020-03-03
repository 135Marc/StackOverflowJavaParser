package common;

public class Tag {
    private long id;
    private String name;
    private int counter;

    public Tag (long id,String name) {
        this.id=id;
        this.name=name;
        this.counter=0;
    }

    public Tag () {
        this.id=0;
        this.name=null;
        this.counter=0;
    }

    public Tag (Tag t) {
        this.id = t.getId();
        this.name = t.getName();
        this.counter=t.getCounter();
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCounter() {
        return counter;
    }

    public void addCounter () {
        this.counter+=1;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID : ")
                .append(getId() + "\n")
                .append(" Name : ")
                .append(getName()+"\n")
                .append(" Counter: ")
                .append(getCounter() + "\n");
        return sb.toString();
    }

    public Tag clone() {
        return new Tag(this);
    }
}
