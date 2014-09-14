package org.summarly.lib.common;

import java.net.URL;

/**
 * Created by Kostya on 14.09.2014.
 */
public class Article {

    private String title;
    private String text;
    private URL KDPVimage;

    public Article(String title, String text, URL KDPVimage) {
        this.title = title;
        this.text = text;
        this.KDPVimage = KDPVimage;
    }

    public Article(){

    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public URL getKDPVimage() {
        return KDPVimage;
    }

    public void setKDPVimage(URL KDPVimage) {
        this.KDPVimage = KDPVimage;
    }

}
