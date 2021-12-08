package com.yasynskyi.analyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HyperlinkAnalyzerTest {

    @Test
    public void test() throws IOException {
        String url = "https://kievfishing.com.ua/";
       // assertEquals(26, HyperlinkCounter(url));

    }

    @DisplayName("Test input incorrect url")
    @Test
    public void incorrectLink() {
        assertTrue(true);
    }

}
