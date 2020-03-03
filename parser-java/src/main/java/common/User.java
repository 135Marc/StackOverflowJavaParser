package common;

public class User {
    private long id;
    private int reputation;
    private String displayname;
    private String shortbio;
    private int postcount;

    public User() {
        this.id = 0;
        this.reputation = 0;
        this.displayname = this.shortbio = null;
        this.postcount=0;
    }

    public User(long id, int reputation, String displayname, String shortbio) {
        this.id = id;
        this.reputation = reputation;
        this.displayname = displayname;
        this.shortbio = shortbio;
        this.postcount=0;

    }

    public User(User u) {
        this.id = u.getId();
        this.reputation = u.getReputation();
        this.displayname = u.getDisplayname();
        this.shortbio = u.getShortbio();
        this.postcount=u.getPostcount();
    }

    public long getId() {
        return id;
    }

    public int getReputation() {
        return reputation;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getShortbio() {
        return shortbio;
    }

    public int getPostcount() {
        return postcount;
    }

    public void add_postcount() {
        this.postcount+=1;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setShortbio(String shortbio) {
        this.shortbio = shortbio;
    }

    public User clone() {
        return new User(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ")
                .append(getId())
                .append(" Reputation: \n")
                .append(getReputation())
                .append(" Name: \n")
                .append(getDisplayname())
                .append(" About Me: \n")
                .append(getShortbio())
                .append(" PostCount : \n")
                .append(getPostcount());
        return sb.toString();
    }
}
