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


def class_finder_header():
    return f'''    public void test_basic_existence() {{
'''


def class_finder(class_name):
    return f'''        Class<?> currClass = ReflectHelper.getClass("{class_name}");

'''


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


def general_contain_assertion_creator(partial_string, index_to_check, error_message):
    return f'''        assert lines[{index_to_check}].trim().contains("{partial_string}") : "{error_message}";

'''


def general_success_filler(message):
    return f'''        System.out.println("{message}");
'''


def basic_variable_finder(variable_name):
    return f'''        ReflectAssert.assertFieldExists(currClass, "{variable_name}");
        Field f = ReflectHelper.getDeclaredField(currClass, "{variable_name}");
'''


def type_checker(variable_type):
    return f'''        ReflectAssert.assertFieldType(f, {variable_type}.TYPE);
'''


def access_specifier_checker(access_specifier):
    return f'''        ReflectAssert.assert{access_specifier[0].upper() + access_specifier[1:]}(f);
'''


def static_checker(is_static):
    return f'''        ReflectAssert.aseertStatic(f, {'true' if is_static else 'false'});
'''


def basic_interface_finder(interface_name):
    return f'''        Class<?> testInterface = ReflectHelper.getClass("{interface_name}");
        ReflectAssert.assertInterface(testInterface);
'''


def basic_method_finder(class_name, method_name):
    return f'''        ReflectAssert.assertMethodExists("Method {method_name} missing or incorrectly named.", {class_name}, "{method_name}";
'''


def method_return_value_checker(class_name, method_name, input_types, sample_inputs, is_void, expected_return=None):
    return f'''        Object instance;
        try {{
            Constructor con = ReflectHelper.getDeclaredConstuctor(ReflectHelper.getClass("{class_name}{', '.join([x + '.TYPE' for x in input_types])}"));
            instance ReflectHelper.getInstance(con{', '.join(sample_inputs)});
        }} catch (AssertionError ae) {{
            fail("Cannot instantiate Triangle class");
            return null;
        }}
        
        Method getPerimeter = ReflectHelper.getDeclaredMethod({class_name}, "{method_name}");

        MethodInvocationReport mir = ReflectHelper.invokeMethod({method_name}, instance);
        
        {f"""assertTrue("No return value present for {method_name}", mir.returnValue.isPresent());
        
        assertTrue("{method_name} does not return the correct value", mir.returnValue.get() == {expected_return})""" if not is_void 
    else f"""assertTrue("Return value present for {method_name}", !mir.returnValue.isPresent());"""}

        System.out.println("Your class has a method {method_name}");
'''
