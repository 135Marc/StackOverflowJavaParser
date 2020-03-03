package engine;

import common.User;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import java.util.stream.Collectors;

public class UserHandler extends DefaultHandler {
    private Map<Long,User> hashUser;

    public UserHandler () {
        this.hashUser = new HashMap<>();
    }

    public Map<Long, User> getHashUser() {
        return this.hashUser.values().stream().collect(Collectors.toMap(User::getId,User::clone));
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs)
            throws SAXException {
        if (qName.equals("row")) {
            User u = new User();
            long id = Long.valueOf((attrs.getValue("Id")));
            u.setId(id);
            int reputation = Integer.valueOf((attrs.getValue("Reputation")));
            u.setReputation(reputation);
            String display = attrs.getValue("DisplayName");
            u.setDisplayname(display);
            String shortbio = attrs.getValue("AboutMe");
            u.setShortbio(shortbio);
            this.hashUser.put(id,u);
        }
    }
}
