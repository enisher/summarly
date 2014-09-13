package org.summarly.model;


public class ArticleModel {

    private String articleText;
    private String articleURL;

    public ArticleModel() {

    }

    public ArticleModel(String articleText, String articleURL) {
        this.articleText = articleText;
        this.articleURL = articleURL;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

	public String getArticleURL() {
		return articleURL;
	}

	public void setArticleURL(String articleURL) {
		this.articleURL = articleURL;
	}
    
}
