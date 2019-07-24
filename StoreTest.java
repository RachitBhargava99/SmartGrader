import org.junit.Test;
import static org.junit.Assert.*;
import com.gradescope.jh61b.grader.GradedTest;
import java.lang.reflect.*;
import java.io.File;
import java.util.Scanner;

public class StoreTest {

    @Test(timeout = 5000)
    @GradedTest(name = "Compilation/Main Method", max_score = 10)
    public void test_main_method() {
        Method main = TestHelper.getMethod("Store",
                "main", String[].class);
        System.out.println("Program compiles and main method exists.");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "FORMAT: Begin inventory prompt", max_score = 0)
    public void test_first_prompt_format() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "E");

        String[] lines = std_out.split("\n");

        assertEquals("Improper formatting on initial M/C/E prompt",
            lines[0].trim(), "Are you a [M]anager, a [C]ustomer, or would you like to [E]xit?");

        System.out.println("Formatting for the first prompt is correct");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Application exits based on initial input", max_score = 2)
    public void test_first_prompt_e() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "E");

        String[] lines = std_out.split("\n");

        if (lines.length > 2) {
            fail("Too many lines printed following 'E' input, make sure the game exits immediately "
            + "after printing 'Goodbye'");
        }

        System.out.println("Application correctly exits after initial E input");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Application continues based on initial M input", max_score = 1.5)
    public void test_first_prompt_m() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "M\nBroccoli\n5\nY\nE");

        String[] lines = std_out.split("\n");

        if (lines.length < 2) {
            fail("Not enough lines printed following 'M' input, make sure the app properly continues");
        }

        System.out.println("App correctly continues after initial M input");

    }


    @Test(timeout = 5000)
    @GradedTest(name = "Application continues based on initial C input", max_score = 1.5)
    public void test_first_prompt_c() {

        // get the output from running the main method. We have to go back into Manager mode
        // and undo what we bought because some people have the inventory saved as static.
        String std_out = TestHelper.testMain("Store", null, "C\nMilk\n1\nN\nM\nMilk\n1\nY\nE");

        String[] lines = std_out.split("\n");

        if (lines.length < 2) {
            fail("Not enough lines printed following 'C' input, make sure the app properly continues");
        }

        System.out.println("App correctly continues after initial C input");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Application correctly calculates cost of shopping cart test 1", max_score = 5)
    public void test_total_cost_one_milk() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nMilk\n1\nN\nM\nMilk\n1\nY\nE");

        String[] lines = std_out.split("\n");

        if (!std_out.contains("$1.99")) {
            fail("Your app did not output '$1.99' when 1 milk was purchased.");
        }

        System.out.println("App correctly calculated the total price in one instance");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Application correctly calculates cost of shopping cart test 2", max_score = 5, visibility="after_published")
    public void test_total_cost_three_oranges() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nOranges\n3\nN\nM\nOranges\n3\nY\nE");

        if (!std_out.contains("$3.87")) {
            fail("Your app did not output '$3.87' when 3 oranges were purchased.");
        }

        System.out.println("App correctly calculated the total price in a second instance");

    }


    @Test(timeout = 5000)
    @GradedTest(name = "Increases stock test 1", max_score = 5)
    public void test_restock_brocc() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "M\nBroccoli\n5\nY\nE");

        String[] lines = std_out.split("\n");

        try {
            assertEquals("Incorrect output when Broccoli stock is increased by 5. Make sure your "
                + "output matches the sample output *exactly*!",
                "Stock of Broccoli is now increased to 5.", lines[5].trim());
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Your app does not print out enough lines. Make sure that you match the "
             + "sample output *exactly*!");
        }
        System.out.println("App correctly increases broccoli stock by 5");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Increases stock test 2", max_score = 5, visibility="after_published")
    public void test_restock_apples() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "M\nApples\n2\nY\nC\nApples\n2\nN\nE");

        String[] lines = std_out.split("\n");

        try {
            assertEquals("Incorrect output when Apples stock is increased by 2.",
                "Stock of Apples is now increased to 14.", lines[5].trim());
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Your app does not print out enough lines. Make sure that you match the "
             + "sample output *exactly*!");
        }

        System.out.println("App correctly increases apple stock by 2");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Decreases stock test 1", max_score = 5, visibility="after_published")
    public void test_purchase_apples() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nApples\n12\nY\nApples\n1\nN\nM\nApples\n12\nY\nE");

        String[] lines = std_out.split("\n");

        if(!std_out.toLowerCase().contains("sorry")) {
               fail("Your code did not correctly remove all the apples when requested to by a customer.");
        } 

        System.out.println("App correctly decreases apple stock by 12.");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Decreases stock test 2", max_score = 5, visibility="after_published")
    public void test_purchase_cereal() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nCereal\n1\nY\nCereal\n1\nN\nM\nCereal\n1\nY\nE");

        String[] lines = std_out.split("\n");

        if(!std_out.contains("0")) {
               fail("Your code did not correctly remove one cereal when purchased by the customer.");
        } 

        System.out.println("App correctly decreases cereal stock by 1.");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "FORMAT: Total cost report", max_score = 10)
    public void test_total_formatting() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nMilk\n1\nN\nE");

        String[] lines = std_out.split("\n");

        String totalLine = lines[lines.length - 4];

        assertEquals("Your total cost output is incorrect. If it appears blank, ensure your line breaks are correct.",
                "The total cost of your purchase is", totalLine.substring(0,34));
        assertEquals("Your total cost output is incorrect. If it appears blank, ensure your line breaks are correct.",
                ". Thank you for shopping!", totalLine.substring(40, totalLine.length()));

        System.out.println("Correct formatting for the total cost output");

    }
    
    @Test(timeout = 5000)
    @GradedTest(name = "Correct output when restocking item that does not exist 1", max_score = 5, visibility="after_published")
    public void test_restock_not_exist_1() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "M\nMalk\nY\nE");

        if (!std_out.contains("You don't have Malk!")) {
            fail("Your code did not output \"You don't have Malk\" when the manager input \"Malk\".");
        }
        
        System.out.println("Your code correctly outputs \"You don't have\" somewhere when incorrect "
                        + "manager input provided.");

    }
    
    @Test(timeout = 5000)
    @GradedTest(name = "Correct output when purchasing an item that does not exist", max_score = 5, visibility="after_published")
    public void test_purhcase_not_exist() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nAplas\nN\nE");

        String[] lines = std_out.split("\n");

        String error_message = ("Your code does not output the right line in the right place when given the invalid "
                + "entry \"Aplas\".");

        if (!std_out.contains("Sorry, we don't have Aplas.")) {
                fail(error_message);
        }
        
        System.out.println("Your code correctly outputs \"Sorry, we don't have\" in the right place when incorrect "
                        + "customer input provided.");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Correct output when not enough stock 1", max_score = 5, visibility="after_published")
    public void test_not_enough_stock_1() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nOranges\n1000\nN\nE");

        String[] lines = std_out.split("\n");

        if(!std_out.contains("only have 6 Oranges in stock")) {
            fail("Your code does not output \"only have ... in stock\" when asked for 1000 oranges.");
        }

        
        System.out.println("Your code correctly outputs \"only have ... in stock\" somewhere when incorrect "
                        + "customer input provided.");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Correct output when not enough stock 2", max_score = 5, visibility="after_published")
    public void test_not_enough_stock_2() {

        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, "C\nWatermelon\n50\nN\nE");

        String[] lines = std_out.split("\n");

        String error_message = ("Your code does not output the right line in the right place when given the invalid "
                + "entry \"50 Watermelon\".");

        assertEquals(error_message, "Sorry, we only have 20 Watermelon in stock.", lines[5]);

        
        System.out.println("Your code correctly outputs \"Sorry, we only have 20 Watermelon in stock.\" "
                        + "in the right place when incorrect customer input provided.");

    }

    @Test(timeout = 5000)
    @GradedTest(name = "Has a String[][] array", max_score = 10, visibility="after_published")
    public void test_has_2d_string_array() {


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
    @GradedTest(name = "Has a loop of some kind", max_score = 10, visibility="after_published")
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
    @GradedTest(name = "Correctly loops through and finds items", max_score = 5, visibility="after_published")
    public void test_loops_properly() {

        String input = "M\nWatermelon\n50\nN\nMilk\n1\nN\nCereal\n1\nN\nJava\nY\nC\nMilk\n1\nY\nCereal\n1\nN\nE";
        // get the output from running the main method.
        String std_out = TestHelper.testMain("Store", null, input); 
        
        String[] lines = std_out.split("\n");

        int count = 0;

        for (String s : lines) {
            if (s.toLowerCase().contains("sorry")) {
                count++;
            }
        }

        if (count != 0) {
            fail("Your code does not loops through and find items that exist/do not exist properly");
        }

        System.out.println("Your code loops through and finds items properly.");
    }

}
