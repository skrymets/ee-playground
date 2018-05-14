package equeue;

import static org.junit.Assert.*;
import org.junit.Test;

public class EqueueApplicationTests extends ShellTest {

    @Test
    public void testWithNoShell() {
        System.out.println("No shell is required for this test");
    }

    @Test
    public void testWithShell() {
        Object statusResult = getShell().evaluate(() -> "status");
        System.out.println(statusResult);
        assertEquals(statusResult, "unknown");

    }

}
