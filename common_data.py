common_header_data = '''import org.junit.Test;
import static org.junit.Assert.*;
import com.gradescope.jh61b.grader.GradedTest;
import java.lang.reflect.*;

public class autograder {
'''

common_closing_data = '''    }
'''

common_footer_data = '''}'''

general_timeout = 5000


def timeout_header(timeout):
    return f'\n    @Test(timeout = {timeout})\n'


def gradescope_header(name, points):
    return f'    @GradedTest(name = \"{name}\", max_score = {points})\n'


def main_method_checker(class_name):
    return f'''    public void test_main_method() {{
        Method main = TestHelper.getMethod("{class_name}",
                "main", String[].class);
        System.out.println("Program compiles and main method exists.");
    }}
'''


def general_method_header(method_name):
    return f'''    public void {method_name}() {{
'''


def general_prompt_creator(class_name, prompt):
    return f'''
        String std_out = TestHelper.testMain("{class_name}", null, "{prompt}");

        String[] lines = std_out.split("\\n");

'''


def general_assertion_creator(ideal_string, index_to_check, error_message):
    return f'''        assertEquals("{error_message}",
            lines[{index_to_check}].trim(), "{ideal_string}");

'''


def general_success_filler(message):
    return f'''        System.out.println("{message}");
'''
