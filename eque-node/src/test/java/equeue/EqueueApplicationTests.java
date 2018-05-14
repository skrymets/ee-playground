package equeue;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EqueueApplicationTests extends ShellTest {

    @Test
    public void testWithNoShell() {
        System.out.println("No shell is required for this test");
    }

    @Test
    public void testWithShell() {
        Object statusResult = getShell().evaluate(() -> "status");
        System.out.println(statusResult);
        assertEquals(statusResult, "Node is offline");

    }

}
