package com.yasynskyi.analyzer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HyperlinkAnalyzer hyperlinkAnalyzer = new HyperlinkAnalyzer("https://idpredmetov.ru/");
        hyperlinkAnalyzer.analyzeDomain();
        hyperlinkAnalyzer.showResult();
    }

}
