import org.junit.Test;
import static org.junit.Assert.*;
import com.gradescope.jh61b.grader.GradedTest;
import java.lang.reflect.*;

public class autograder {
        Class<?> currClass = ReflectHelper.getClass("Store");


    @Test(timeout = 5000)
    @GradedTest(name = "Compilation", max_score = 10)
    public void test_basic_existence() {
        Class<?> currClass = ReflectHelper.getClass("Store");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Exits Properly at E", max_score = 2)
    public void program_exits_e() {

        String std_out = TestHelper.testMain("Store", null, "E");

        String[] lines = std_out.split("\n");

        assertEquals("Typo in main prompt / incorrect prompt",
            lines[0].trim(), "Are you a (M)anager, (C)ustomer, or would you like to (E)xit?");

        assertEquals("Typo in goodbye message",
            lines[1].trim(), "Goodbye!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Exits Properly after an M cycle", max_score = 1.5)
    public void program_exits_m() {

        String std_out = TestHelper.testMain("Store", null, "M\nbroccoli\n5\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Typo in goodbye message",
            lines[10].trim(), "Goodbye!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Exits Properly after a C cycle", max_score = 1.5)
    public void program_exits_c() {

        String std_out = TestHelper.testMain("Store", null, "C\nmilk\n2\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Typo in goodbye message",
            lines[14].trim(), "Goodbye!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Gives Correct Output (along with correct formatting) For Purchased Items", max_score = 10)
    public void test_formatting_random_purchase() {

        String std_out = TestHelper.testMain("Store", null, "M\nBroccoli\n3\nN\nJava\nY\nC\nBroccoli\n2\nY\nY\nWatermelon\n15\nY\nMilk\n1\nY\nWatermelon\n5\nWatermelon\n3000\nY\nJava\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not format this line properly: *Here are the items you have purchased:* / typo / Customer cycle not working properly",
            lines[55].trim(), "Here are the items you have purchased:");

        assertEquals("Program does not format this line properly: *2 Broccoli* / typo / Customer cycle not working properly",
            lines[56].trim(), "2 Broccoli");

        assertEquals("Program does not format this line properly: *20 Watermelon* / typo / Customer cycle not working properly",
            lines[57].trim(), "20 Watermelon");

        assertEquals("Program does not format this line properly: *1 Milk* / typo / Customer cycle not working properly",
            lines[58].trim(), "1 Milk");

        assertEquals("Program does not format this line properly: *The total cost of your purchase is $53.77. Thank you for shopping!* / typo / Customer cycle not working properly",
            lines[59].trim(), "The total cost of your purchase is $53.77. Thank you for shopping!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Has a String[][] array", max_score = 10)
    public void test_has_2D_String_array() {
        try {
                Scanner sc = new Scanner(new File("Store.java"));
                sc.useDelimiter("\\Z");
                String submission_file = sc.next();
                String[] lines = submission_file.split("\n");
                boolean found = false;
                for (String s : lines) {
                    s = s.trim();
                    if (!s.startsWith("//")) {
                        s = s.replaceAll(" ", "");
                        if (s.contains("String[][]")) {
                            found = true;
                        }
                    }
                }

                if (!found) {
                    fail("Your code does not contain a 2D String array.");
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }
            System.out.println("Your code declares a 2d string array");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Has a loop of some kind", max_score = 10)
    public void test_has_loop() {
        try {
            Scanner sc = new Scanner(new File("Store.java"));
            sc.useDelimiter("\\Z");
            String submission_file = sc.next();
            String[] lines = submission_file.split("\n");
            boolean found = false;
            for (String s : lines) {
                s = s.trim();
                if (!s.startsWith("//")) {
                    s = s.replaceAll(" ", "");
                    if (s.contains("while") || s.contains("for") || s.contains("do")) {
                        found = true;
                    }
                }
            }

            if (!found) {
                fail("Your code does not contain a loop of some kind.");
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        System.out.println("Your code uses a loop of some kind to check for items in the array.");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Typo Test Case", max_score = 10)
    public void simple_test_case() {

        String std_out = TestHelper.testMain("Store", null, "M\nBroccoli\n3\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Typo in requesting to restock",
            lines[1].trim(), "Which item would you like to restock?");

        assertEquals("Typo in asking the quantity to restock",
            lines[2].trim(), "By how much are you restocking Broccoli?");

        assert lines[3].trim().contains("3") : "Incorrect message printed upon restock";

        assertEquals("Incorrect message printed when asked for restocking confirmation",
            lines[4].trim(), "Are you finished with restocking? [Y]es or [N]o?");

        assertEquals("Typo in asking if the user is a manager or customer, or if he wants to exit",
            lines[5].trim(), "Are you a [M]anager, a [C]ustomer, or would you like to [E]xit?");

        System.out.println("Congratulations! You just passed the typo test");
    }
}