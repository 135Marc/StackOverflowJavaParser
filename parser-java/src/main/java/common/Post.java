package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Post {
    private long id,ownerid;
    private int typeid,score,commentcount;
    private LocalDate date;

    public Post () {
        this.id=this.ownerid=0;
        this.typeid=this.score=this.commentcount=0;
        this.date=null;
    }

    public Post(long id, long ownerid, long parentid, int typeid, int score, int answercount, int commentcount, String title, LocalDate date, String tags) {
        this.id = id;
        this.ownerid = ownerid;
        this.typeid = typeid;
        this.score = score;
        this.commentcount = commentcount;
        this.date = date;
    }

    public Post (Post p) {
        this.id = p.getId();
        this.ownerid = p.getOwnerid();
        this.typeid = p.getTypeid();
        this.score= p.getScore();
        this.commentcount = p.getCommentcount();
        this.date = p.getDate();

    }

    public long getId () {
        return id;
    }

    public long getOwnerid() {
        return ownerid;
    }

    public int getTypeid() {
        return typeid;
    }

    public int getScore() {
        return score;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public void setDate(String date) {
        String s = sort_date(date);
        this.date = create_date(s);
    }

    private String sort_date(String raw) {
        String s;
        int i = raw.indexOf("T");
        s=raw.substring(0,i);
        return s;
    }

    private LocalDate create_date (String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    public boolean isQuestion () {
        return (this.getTypeid()==1);
    }

    public boolean isAnswer() {
        return (this.getTypeid()==2);
    }

    public boolean betweenDate(LocalDate begin,LocalDate end) {
        return (this.getDate().isAfter(begin) && this.getDate().isBefore(end)) || (begin.isEqual(this.getDate()) || end.isEqual(this.getDate()));
    }

    public Post clone () {
        return new Post(this);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ")
                .append(getId())
                .append(" OwnerID: ")
                .append(getOwnerid())
                .append(" Type_ID: ")
                .append(getTypeid())
                .append(" Score: ")
                .append(getScore())
                .append(" Date: ")
                .append(getDate())
                .append(" Comment_Count: ")
                .append(getCommentcount());
        return sb.toString();
    }
}
