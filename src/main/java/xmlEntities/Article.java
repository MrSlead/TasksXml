package xmlEntities;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement()
public class Article implements Serializable {
    @XmlAttribute(name = "id_art")
    private String idArt;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "code")
    private String code;

    @XmlAttribute(name = "username")
    private String username;

    @XmlAttribute(name = "guid")
    private String guid;

    public Article(String idArt, String name, String code, String username, String guid) {
        this.idArt = idArt;
        this.name = name;
        this.code = code;
        this.username = username;
        this.guid = guid;
    }

    public Article() {
    }

    public String getIdArt() {
        return idArt;
    }

    public void setIdArt(String idArt) {
        this.idArt = idArt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
