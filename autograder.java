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
    @GradedTest(name = "Program Exits Properly at E", max_score = 1)
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
    @GradedTest(name = "Program Exits Properly after an M cycle", max_score = 2)
    public void program_exits_m() {

        String std_out = TestHelper.testMain("Store", null, "M\nbroccoli\n5\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Typo in goodbye message",
            lines[10].trim(), "Goodbye!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Exits Properly after a C cycle", max_score = 2)
    public void program_exits_c() {

        String std_out = TestHelper.testMain("Store", null, "C\nmilk\n2\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Typo in goodbye message",
            lines[14].trim(), "Goodbye!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Loops to First Item - through M", max_score = 1.5)
    public void test_item_first() {

        String std_out = TestHelper.testMain("Store", null, "M\nMilk\n2\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program could not find Milk / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Milk has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Loops to a Middle Item - Test 1 - through M", max_score = 2)
    public void test_item_middle_1() {

        String std_out = TestHelper.testMain("Store", null, "M\nCereal\n3\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program could not find Cereal / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Cereal has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Loops to a Middle Item - Test 1 - through M", max_score = 2)
    public void test_item_middle_2() {

        String std_out = TestHelper.testMain("Store", null, "M\nBroccoli\n4\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program could not find Broccoli / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Broccoli has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Loops to a Middle Item - Test 2 - through M", max_score = 1.5)
    public void test_item_last() {

        String std_out = TestHelper.testMain("Store", null, "M\nOlive Oil\n3\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program could not find Olive Oil / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Olive Oil has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Does Not Loop to a Non-Existent Item - through M", max_score = 2)
    public void test_item_non_existent() {

        String std_out = TestHelper.testMain("Store", null, "M\napple\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program somehow found apple / typo / Manager cycle not working properly",
            lines[3].trim(), "You don't have apple!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Loops to First Item - All Lowercase - through M", max_score = 2)
    public void test_item_all_lower() {

        String std_out = TestHelper.testMain("Store", null, "M\nmilk\n2\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program could not find *milk* / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Milk has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Loops to First Item - All Uppercase - through M", max_score = 2)
    public void test_item_all_upper() {

        String std_out = TestHelper.testMain("Store", null, "M\nMILK\n2\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program could not find *MILK* / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Milk has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Loops to First Item - Jaggered Case - through M", max_score = 2)
    public void test_item_scooby_case() {

        String std_out = TestHelper.testMain("Store", null, "M\nMilK\n2\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program could not find *MilK* / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Milk has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Updates Item Stocks after Single Restock Request", max_score = 2)
    public void test_single_restock() {

        String std_out = TestHelper.testMain("Store", null, "M\nMilk\n2\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not restock Milk to 4 / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Milk has now been increased to 4.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Updates Item Stocks after Multiple Restock Requests", max_score = 2.5)
    public void test_multi_restock() {

        String std_out = TestHelper.testMain("Store", null, "M\nMilk\n2\nN\nBroccoli\n3\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not restock Milk to 4 / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Milk has now been increased to 4.");

        assertEquals("Program does not restock Broccoli to 3 / typo / Manager cycle not working properly",
            lines[12].trim(), "Stock of Broccoli has now been increased to 3.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Updates Item Stocks after Single Purchase Request", max_score = 2)
    public void test_single_purchase() {

        String std_out = TestHelper.testMain("Store", null, "C\nMilk\n2\nY\nMilk\n100\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not restock Milk to 0 / typo / Customer cycle not working properly",
            lines[12].trim(), "Sorry, we only have 0 Milk in stock.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Updates Item Stocks after Multiple Purchase Requests", max_score = 2.5)
    public void test_multi_purchase() {

        String std_out = TestHelper.testMain("Store", null, "C\nMilk\n2\nY\nOranges\n4\nY\nMilk\n100\nY\nOranges\n100\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not restock Milk to 0 / typo / Customer cycle not working properly",
            lines[19].trim(), "Sorry, we only have 0 Milk in stock.");

        assertEquals("Program does not restock Oranges to 2 / typo / Customer cycle not working properly",
            lines[26].trim(), "Sorry, we only have 2 Oranges in stock.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Updates Item Stocks after Single Restock, Single Purchase Requests", max_score = 3)
    public void test_single_restock_purchase() {

        String std_out = TestHelper.testMain("Store", null, "M\nMilk\n3\nY\nC\nMilk\n2\nY\nMilk\n100\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not restock Milk to 5 / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Milk has now been increased to 5.");

        assertEquals("Program does not restock Milk to 3 / typo / Customer cycle not working properly",
            lines[21].trim(), "Sorry, we only have 3 Milk in stock.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Updates Item Stocks after Single Purchase, Single Restock Requests", max_score = 3)
    public void test_single_purchase_restock() {

        String std_out = TestHelper.testMain("Store", null, "C\nMilk\n1\nY\nMilk\n100\nN\nM\nMilk\n2\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not restock Milk to 1 / typo / Customer cycle not working properly",
            lines[12].trim(), "Sorry, we only have 1 Milk in stock.");

        assertEquals("Program does not restock Milk to 3 / typo / Manager cycle not working properly",
            lines[25].trim(), "Stock of Milk has now been increased to 3.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Updates Item Stocks after Multiple Purchase-Restock Requests", max_score = 5)
    public void test_random_purchase_restock() {

        String std_out = TestHelper.testMain("Store", null, "M\nBroccoli\n3\nN\nJava\nY\nC\nBroccoli\n2\nY\nY\nWatermelon\n15\nY\nMilk\n1\nY\nWatermelon\n5\nWatermelon\n3000\nY\nJava\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not restock Broccoli to 3 / typo / Manager cycle not working properly",
            lines[5].trim(), "Stock of Broccoli is now increased to 3.");

        assertEquals("Program does not restock Watermelon to 0 / typo / Customer cycle not working properly",
            lines[46].trim(), "Sorry, we only have 0 Watermelon in stock.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Correctly Calculates Price for Single Purchase Request", max_score = 1)
    public void test_price_single_purchase() {

        String std_out = TestHelper.testMain("Store", null, "C\nWatermelon\n3\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not calculate cost to purchase 3 watermelon to $7.47 / typo / Customer cycle not working properly",
            lines[11].trim(), "The total cost of your purchase is $7.47. Thank you for shopping!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Correctly Calculates Price for Multiple Purchase for Same Item", max_score = 1.5)
    public void test_price_multiple_purchase_same_item() {

        String std_out = TestHelper.testMain("Store", null, "C\nWatermelon\n1\nY\nWatermelon\n2\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not calculate cost to purchase 3 watermelon to $7.47 / typo / Customer cycle not working properly",
            lines[18].trim(), "The total cost of your purchase is $7.47. Thank you for shopping!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Correctly Calculates Price for Multiple Purchase for Different Items", max_score = 2.5)
    public void test_price_multiple_purchase_different_items() {

        String std_out = TestHelper.testMain("Store", null, "C\nWatermelon\n1\nY\nMilk\n2\nY\nWatermelon\n2\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not calculate cost to purchase 3 watermelon and 2 milk to $11.45 / typo / Customer cycle not working properly",
            lines[26].trim(), "The total cost of your purchase is $11.45. Thank you for shopping!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Correctly Calculates Price for Multiple Purchase for Different Items", max_score = 2)
    public void test_price_invalid_purchase() {

        String std_out = TestHelper.testMain("Store", null, "C\napple\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not calculate cost to purchase no items to $0.00 / typo / Customer cycle not working properly",
            lines[8].trim(), "The total cost of your purchase is $0.00. Thank you for shopping!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Correctly Calculates Price for Multiple Purchase for Different Items", max_score = 3)
    public void test_price_random_purchase() {

        String std_out = TestHelper.testMain("Store", null, "M\nBroccoli\n3\nN\nJava\nY\nC\nBroccoli\n2\nY\nY\nWatermelon\n15\nY\nMilk\n1\nY\nWatermelon\n5\nWatermelon\n3000\nY\nJava\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not calculate cost to purchase 2 Broccoli, 20 Watermelon and 1 Milk to $53.77 / typo / Customer cycle not working properly",
            lines[59].trim(), "The total cost of your purchase is $53.77. Thank you for shopping!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Gives Correct Output when Item does not exist for Manager", max_score = 5)
    public void test_item_does_not_exist_m() {

        String std_out = TestHelper.testMain("Store", null, "M\napple\nY\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not give correct output when item does not exist for manager / typo / Manager cycle not working properly",
            lines[3].trim(), "You don't have apple!");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Gives Correct Output when Item does not exist for Customer", max_score = 5)
    public void test_item_does_not_exist_c() {

        String std_out = TestHelper.testMain("Store", null, "C\napple\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not give correct output when item does not exist for customer / typo / Customer cycle not working properly",
            lines[3].trim(), "Sorry, we don't have apple.");

        System.out.println("Success");
    }

    @Test(timeout = 5000)
    @GradedTest(name = "Program Gives Correct Output when Item is Insufficient", max_score = 10)
    public void test_insufficient_item() {

        String std_out = TestHelper.testMain("Store", null, "C\nApples\n100\nN\nE");

        String[] lines = std_out.split("\n");

        assertEquals("Program does not give correct output when item is insufficient / typo / Customer cycle not working properly",
            lines[5].trim(), "Sorry, we only have 12 Apples in stock.");

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