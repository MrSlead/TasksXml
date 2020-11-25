package xmlEntities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "articles")
public class ArticlesXmlFormat {
    @XmlElement(name = "article")
    private List<Article> articleList;

    public ArticlesXmlFormat(List<Article> articleList) {
        this.articleList = articleList;
    }

    public ArticlesXmlFormat() {}

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
