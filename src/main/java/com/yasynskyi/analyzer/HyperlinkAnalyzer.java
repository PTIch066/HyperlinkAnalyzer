package com.yasynskyi.analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HyperlinkAnalyzer {

    ArrayList<UrlData> urlList = new ArrayList();

//    public static long HyperlinkCounter(String domain) throws IOException {
//        long linkCounter = 0;
//
//        Document document = Jsoup.connect(domain).get();
//        Elements elements = document.select("a");
//        for (Element element:elements){
//            String url = element.attr("href");
//            if(url.isEmpty()){
//                continue;
//            }
//
//            if(url.substring(0, 5).equals("https")) {
//                linkCounter++;
//                HyperlinkCounter(url);
//            }
//        }
//
//        return linkCounter;
//    }

    public void showResult(){
        for (int i = 0; i < urlList.size(); i++) {
            System.out.println("url name: " + urlList.get(i).getUrl());
            System.out.println("links number on page: " + urlList.get(i).getNumberOfLinks());
            System.out.println("inner level of url: " + urlList.get(i).getInnerLevel());
        }
        System.out.println();
        System.out.println();
    }


    public void analyzeDomain(String domain) throws IOException {
        System.out.println("1");
        addUrl(domain);
        makeOriginalList(domain);

        for (int i = 0; i < urlList.size(); i++){
            urlList.get(i).setNumberOfLinks(analyzeNumberOfUrl(urlList.get(i).getUrl()));
        }
    }

    private int analyzeNumberOfUrl(String url) throws IOException {
        int counter = 0;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("a");
        for (Element element:elements) {
            String uri = element.attr("href");

            if(uri.length() < 5){
                continue;
            }

            if (uri.substring(0, 5).equals("https")) {
                counter++;
            }
        }
        System.out.println("3");
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
        for (Element element:elements){
            String url = element.attr("href");

            if(url.length() < 5){
                continue;
            }

            if(url.substring(0, 5).equals("https") && isUnique(url)) {
                addUrl(url);
                makeOriginalList(url);
            }
        }
        System.out.println("2");
    }

    private void addUrl(String url){
        urlList.add(new UrlData(url));
    }

    private boolean isUnique(String url){
        for (int i = 0; i < urlList.size(); i++) {
            if(urlList.get(i).getUrl().equals(url)){
                return false;
            }
        }
        return true;
    }

    private class UrlData{
        private String url;
        private int innerLevel;
        private int numberOfLinks;

        UrlData(String url){
            this.url = url;
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



