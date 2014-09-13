package org.summarly;


public class ArticleModel {

    private String articleText;

    public ArticleModel() {

    }

    public ArticleModel(String articleText) {
        this.articleText = articleText;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }
}
