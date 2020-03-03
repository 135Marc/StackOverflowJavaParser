package engine;

import common.Post;
import common.Answer;
import common.Question;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PostHandler extends DefaultHandler {

    private Map<Long,Post> hashPost;

    public PostHandler () {
        this.hashPost = new HashMap<>();
    }

    public Map<Long, Post> getHashPost() {
        return this.hashPost.values().stream().collect(Collectors.toMap(Post::getId,Post::clone));
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs)
            throws SAXException {
        if (qName.equals("row")) {
            long id = Long.valueOf((attrs.getValue("Id")));
            int typeid = Integer.valueOf((attrs.getValue("PostTypeId")));
            long ownerid = Long.valueOf((attrs.getValue("OwnerUserId")));
            int score = Integer.valueOf((attrs.getValue("Score")));
            int commentcount = Integer.valueOf((attrs.getValue("CommentCount")));
            String date = attrs.getValue("CreationDate");
            if (typeid == 1) {
                Question p = new Question();
                p.setId(id);
                p.setOwnerid(ownerid);
                p.setTypeid(typeid);
                p.setScore(score);
                p.setCommentcount(commentcount);
                p.setDate(date);
                int answercount = Integer.valueOf(attrs.getValue("AnswerCount"));
                p.setAnswercount(answercount);
                String title = attrs.getValue("Title");
                p.setTitle(title);
                String tags = attrs.getValue("Tags");
                p.setTags(tags);
                this.hashPost.put(id, p);
            }
            if (typeid == 2){
                Answer p1 = new Answer();
                p1.setId(id);
                p1.setOwnerid(ownerid);
                p1.setTypeid(typeid);
                p1.setScore(score);
                p1.setCommentcount(commentcount);
                p1.setDate(date);
                int parentid = Integer.valueOf(attrs.getValue("ParentId"));
                p1.setParentid(parentid);
                this.hashPost.put(id, p1);
            }
        }
    }
}
