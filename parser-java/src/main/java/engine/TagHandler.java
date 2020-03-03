package engine;

import common.Tag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TagHandler extends DefaultHandler {
    private Map<Long, Tag> hashTag;

    public TagHandler() {
        this.hashTag = new HashMap<>();
    }
    public Map<Long, Tag> getHashTag() {
        return this.hashTag.values().stream().collect(Collectors.toMap(Tag::getId,Tag::clone));
    }


    public void startElement(String uri, String localName, String qName, Attributes attrs)
            throws SAXException {
        if (qName.equals("row")) {
            Tag t = new Tag();
            long id = Long.valueOf((attrs.getValue("Id")));
            t.setId(id);
            String nome = attrs.getValue("TagName");
            t.setName(nome);
            this.hashTag.put(id,t);
        }
    }
}