package com.yasynskyi.analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HyperlinkAnalyzer {

    private ArrayList<UrlData> urlList = new ArrayList();

    private String domain;

    private int counterInnerLevel = 1;

    HyperlinkAnalyzer(String domain) {
        this.domain = domain;
    }

    public void showResult() {
        for (int i = 0; i < urlList.size(); i++) {
            System.out.println("url name: " + urlList.get(i).getUrl());
            System.out.println("links number on page: " + urlList.get(i).getNumberOfLinks());
            System.out.println("inner level of url: " + urlList.get(i).getInnerLevel());
            System.out.println();
        }
    }


    public void analyzeDomain() throws IOException {
        addUrl(domain, counterInnerLevel);
        makeOriginalList(domain);

        for (int i = 0; i < urlList.size(); i++) {
            urlList.get(i).setNumberOfLinks(analyzeNumberOfUrl(urlList.get(i).getUrl()));
        }
    }

    private int analyzeNumberOfUrl(String url) throws IOException {
        int counter = 0;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("a");
        for (Element element : elements) {
            String uri = element.attr("href");

            if (uri.length() < 5) {
                continue;
            }

            if (uri.substring(0, 5).equals("https")) {
                counter++;
            }
        }
        return counter;
    }

    private void makeOriginalList(String domain) throws IOException {
        Document document = null;
        try {
            document = Jsoup.connect(domain).get();
        } catch (IOException e) {
            document = Jsoup.connect(urlList.get(0).getUrl()).get();
        }
        Elements elements = document.select("a");
        for (Element element : elements) {
            String url = element.attr("href");

            if (url.length() < urlList.get(0).getUrl().length()) {
                continue;
            }

            if (url.substring(0, urlList.get(0).getUrl().length()).equals(urlList.get(0).getUrl()) && isUnique(url)) {
                addUrl(url, counterInnerLevel);
                counterInnerLevel++;
                makeOriginalList(url);
                counterInnerLevel--;
            }
        }
    }

    private void addUrl(String url, int innerLevel) {
        urlList.add(new UrlData(url, innerLevel));
    }

    private boolean isUnique(String url) {
        for (int i = 0; i < urlList.size(); i++) {
            if (urlList.get(i).getUrl().equals(url)) {
                return false;
            }
        }
        return true;
    }

    private class UrlData {
        private String url;
        private int innerLevel;
        private int numberOfLinks;

        UrlData(String url, int innerLevel) {
            this.url = url;
            this.innerLevel = innerLevel;
        }

        public void setNumberOfLinks(int numberOfLinks) {
            this.numberOfLinks = numberOfLinks;
        }

        public void setInnerLevel(int innerLevel) {
            this.innerLevel = innerLevel;
        }

        public int getInnerLevel() {
            return innerLevel;
        }

        public int getNumberOfLinks() {
            return numberOfLinks;
        }

        public String getUrl() {
            return url;
        }
    }
}



