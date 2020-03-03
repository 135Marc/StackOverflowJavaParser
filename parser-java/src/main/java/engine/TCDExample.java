package engine;
import common.*;
import li3.TADCommunity;
import javax.xml.parsers.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TCDExample implements TADCommunity {

    private Map<Long, Post> hashPost;
    private Map<Long, User> hashUser;
    private Map<Long, Tag> hashTag;

    public TCDExample() {
        this.hashPost = new LinkedHashMap<>();
        this.hashUser = new LinkedHashMap<>();
        this.hashTag = new HashMap<>();
    }

    public void load(String dumpath) {
        try {
            SAXParserFactory factory2 = SAXParserFactory.newInstance();
            SAXParser parser2 = factory2.newSAXParser();
            TagHandler handler2 = new TagHandler();
            parser2.parse(dumpath + "Tags.xml", handler2);
            Map<Long, Tag> res = handler2.getHashTag();
            this.hashTag.putAll(res.values().stream().collect(Collectors.toMap(Tag::getId,Tag::clone)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            PostHandler handler = new PostHandler();
            parser.parse(dumpath + "Posts.xml", handler);
            Map<Long, Post> res = handler.getHashPost();
            List<Post> aux = res.values().stream().sorted(new PostComparator()).collect(Collectors.toList());
            aux.forEach(p -> this.hashPost.put(p.getId(),p.clone()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SAXParserFactory factory1 = SAXParserFactory.newInstance();
            SAXParser parser1 = factory1.newSAXParser();
            UserHandler handler1 = new UserHandler();
            parser1.parse(dumpath + "Users.xml", handler1);
            Map<Long, User> res = handler1.getHashUser();
            Map<Long,User> aux = new HashMap<>();
            for (Post p : this.hashPost.values()) {
                long id = p.getOwnerid();
                User u = res.get(id);
                u.add_postcount();
                aux.put(u.getId(),u.clone());
            }
            List<User> aux1 = aux.values().stream().sorted(new PostCountComparator()).collect(Collectors.toList());
            aux1.forEach(u -> this.hashUser.put(u.getId(),u.clone()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // QUERY 1
    public Pair<String,String> infoFromPost(long id) {
        Post p1 = this.hashPost.get(id);
        long id1 = p1.getOwnerid();
        User u = this.hashUser.get(id1);
        String displayname = u.getDisplayname();
        String title = null;
        if (p1.isQuestion()) {
            title = ((Question) p1).getTitle();
        }
        else if (p1.isAnswer()) {
            long pid = ((Answer) p1).getParentid();
            p1 = this.hashPost.get(pid);
            title = ((Question) p1).getTitle();
        }
        return new Pair<>(title,displayname);
    }
    // QUERY 2
    public List<Long> topMostActive(int N) {
        List<Long> res = new ArrayList<>();
        int c=0;
        for (User u: this.hashUser.values()) {
            if(c==N) break;
            res.add(u.getId());
            c++;
        }
        return res;
    }

    // QUERY 3
    public Pair<Long,Long> totalPosts(LocalDate begin, LocalDate end) {
        List<Post> all = this.hashPost.values().stream().filter(p -> p.betweenDate(begin,end)).collect(Collectors.toList());
        List<Post> q = all.stream().filter(p -> p.isQuestion()).collect(Collectors.toList());
        List <Post> a = all.stream().filter(p -> p.isAnswer()).collect(Collectors.toList());
        return new Pair<>((long) q.size(),(long) a.size());
    }

    //QUERY 4
    public List<Long> questionsWithTag(String tag, LocalDate begin, LocalDate end) {
        List <Post> lp = this.hashPost.values().stream().filter(post -> post.betweenDate(begin, end)).collect(Collectors.toList());
        List <Post> qfilter =lp.stream().filter(post -> (post.isQuestion() && ((Question) post).getTags().contains(tag))).collect(Collectors.toList());
        return qfilter.stream().map(Post::getId).collect(Collectors.toList());
    }

    //QUERY 5
    public Pair<String, List<Long>> getUserInfo(long id) {
        User u = this.hashUser.get(id);
        List<Post> aux = this.hashPost.values().stream().filter(post -> post.getOwnerid()==u.getId()).collect(Collectors.toList());
        List<Long> ids = new ArrayList<>();
        for(int i=0; i<10 ;i++) {
            ids.add(aux.get(i).getId());
        }
        return new Pair<>(u.getShortbio(),ids);
    }

    // QUERY 6
    public List<Long> mostVotedAnswers(int N, LocalDate begin, LocalDate end) {
        List<Post> lp = this.hashPost.values().stream().filter(post -> post.isAnswer() && post.betweenDate(begin,end)).collect(Collectors.toList());
        List<Post> la = lp.stream().sorted(new AnswerComparator()).collect(Collectors.toList());
        List <Long> res = new ArrayList<>();
        for (int i=0;i<N;i++) {
            res.add(la.get(i).getId());
        }
        return res;
    }
    // QUERY 7
    public List<Long> mostAnsweredQuestions(int N, LocalDate begin, LocalDate end) {
        List<Post> lp = this.hashPost.values().stream().filter(post -> post.isQuestion() && post.betweenDate(begin,end)).collect(Collectors.toList());
        List<Post> la = lp.stream().sorted(new QuestionComparator()).collect(Collectors.toList());
        List <Long> res = new ArrayList<>();
        for (int i=0;i<N ;i++) {
            res.add(la.get(i).getId());
        }
        return res;
    }
    //QUERY 8
    public List<Long> containsWord(int N, String word) {
        List<Post> questions = this.hashPost.values().stream().filter(post -> post.isQuestion()).collect(Collectors.toList());
        List<Post> filtered = questions.stream().filter(post -> ((Question) post).getTitle().contains(word)).collect(Collectors.toList());
        List<Long> aux = filtered.stream().map(Post::getId).collect(Collectors.toList());
        List <Long> res = new ArrayList<>();
        for (int i=0;i<N ;i++) {
            res.add(aux.get(i));
        }
        return res;
    }
    // QUERY 9
    public List<Long> bothParticipated(int N, long id1, long id2) {
        Util u = new Util();
        List<Post> all = this.hashPost.values().stream().filter( p -> (p.getOwnerid()==id2) || (p.getOwnerid()==id1)).collect(Collectors.toList());
        List<Post> common = new ArrayList<>();
        int c =0;
        for (Post p : all) {
            if (c==N) break;
            if (p.isQuestion()) {
                if (p.getOwnerid() == id1 && u.has_answered(all,id2,p.getId())) {
                    common.add(p.clone());
                    c++;
                }
                if (p.getOwnerid() == id2 && u.has_answered(all,id1,p.getId())) {
                    common.add(p.clone());
                    c++;
                }
            }
            else if(p.isAnswer()) {
                if (u.both_answered_question(all,id1,id2,((Answer)p).getParentid())) {
                    long l = ((Answer)p).getParentid();
                    Post aux = this.hashPost.get(l);
                    common.add(aux.clone());
                    c++;
                }
            }
        }
        return common.stream().map(Post::getId).collect(Collectors.toList());
    }
    // QUERY 10
    public long betterAnswer(long id) {
        long l =0;
        User u;
        Util u1 = null;
        if (this.hashPost.get(id).isQuestion()) {
            Question q = (Question) this.hashPost.get(id);
            if (q.getAnswercount()==0) return -1;
        }
        List<Post> answers_from = this.hashPost.values().stream().filter(post ->  post.isAnswer() && ((Answer)post).getParentid() == id).collect(Collectors.toList());
        double max =0;
        long x =0;
        for (Post p : answers_from) {
            x = p.getOwnerid();
            u = this.hashUser.get(x);
            double score =  0.65 * p.getScore();
            double rep = 0.25 * u.getReputation();
            double comment = 0.1 * p.getCommentcount();
            double res = score+rep+comment;
            if (max < res) {
                l = p.getId();
                max = res;
            }
        }
        return l;
    }

    //QUERY 11
    public List<Long> mostUsedBestRep(int N, LocalDate begin, LocalDate end) {
        List <User> aux = new ArrayList<>();
        int c=0;
        for(User u : this.hashUser.values()) {
            if (c==N) break;
            aux.add(u);
            c++;
        }
        Util u1 = new Util();
        List<Post> total = new ArrayList<>();
        List<Post> temp;
        for (User u : aux) {
            temp= this.hashPost.values().stream().filter(p -> p.getOwnerid()==u.getId() && p.isQuestion() && p.betweenDate(begin,end)).map(Post::clone).collect(Collectors.toList());
            if (!temp.isEmpty()) total.addAll(temp);
        }
        Map<Long,Tag> acumulador = new HashMap<>();
        for (Post p1 : total) {
            List<String> lista = u1.divide_tags(((Question) p1).getTags());
            Map<Long,Tag> aux1 = new HashMap<>();
            u1.increment_counter(lista, this.hashTag,aux1);
            acumulador.putAll(aux1);
        }
        List<Tag> lista = acumulador.values().stream().sorted(new TagComparator()).map(Tag::clone).collect(Collectors.toList());
        List<Long> l = new ArrayList<>();
        for(int i=0;i<N; i++) {
            l.add(lista.get(i).getId());
        }
        return l;
    }
    public void clear() {
        this.hashPost.clear();
        this.hashUser.clear();
        this.hashTag.clear();
    }
}
