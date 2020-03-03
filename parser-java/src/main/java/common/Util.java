package common;

import java.util.*;
import java.util.stream.Collectors;

public class Util {

    public List<String> divide_tags(String tags) {
        List <String> ls = new ArrayList<>();
        int j=0;
        for (int i=0; i< tags.length();i++) {
            char c = tags.charAt(i);
            if (c=='>') {
                String res = tags.substring(j,i);
                ls.add(res);
            }
            else if (c=='<') j=i+1;
        }
        return ls;
    }

    public void increment_counter(List<String> ls,Map<Long,Tag> maptag,Map<Long,Tag> aux) {
        for (String s : ls) {
            List<Tag> lt = maptag.values().stream().filter(t -> s.equalsIgnoreCase(t.getName())).map(Tag::clone).collect(Collectors.toList());
            lt.forEach(t -> t.addCounter());
            lt.forEach(t-> aux.put(t.getId(),t.clone()));
        }
    }

    public boolean has_answered (List <Post> hp,long id,long pid) {
        List<Post> lp = hp.stream().map(Post::clone).collect(Collectors.toList());
        List<Post> answers = lp.stream().filter(p -> p.isAnswer() && ((Answer)p).getParentid()==pid && p.getOwnerid()==id).collect(Collectors.toList());
        return (!answers.isEmpty());
    }

    public boolean both_answered_question (List<Post> hp,long id,long id1,long pid) {
        List<Post> lp = hp.stream().map(Post::clone).collect(Collectors.toList());
        List<Post> answers = lp.stream().filter(p -> p.isAnswer() && ((Answer)p).getParentid()==pid).collect(Collectors.toList());
        List<Post> filtered = answers.stream().filter(p -> (p.getOwnerid()==id) || (p.getOwnerid()==id1)).collect(Collectors.toList());
        int c =0;
        for(Post p : filtered) {
            if (c==2) break;
            if (c==0) {
                if (p.getOwnerid()==id) c++;
            }
            else if (c==1) {
                if (p.getOwnerid()==id1) c++;
            }
        }
        return (c==2);
    }

}
