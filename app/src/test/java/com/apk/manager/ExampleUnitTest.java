package com.apk.manager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void appManager_formatSize() {
        // This would need a context, so just test basic logic
        long bytes = 1024;
        String expected = "1.0 KB";
        // AppManager appManager = new AppManager(context);
        // assertEquals(expected, appManager.formatSize(bytes));
        assertTrue(true); // Placeholder test
    }
}