from utils import get_file_content, file_filler, string_comparison, variable_finder, interface_finder
from common_data import common_header_data, common_footer_data, general_timeout, timeout_header, gradescope_header,\
    main_method_checker, common_closing_data, general_method_header, general_prompt_creator, general_assertion_creator,\
    general_success_filler, class_finder


def smart_grader_driver(class_name):

    """Generates the autograder .java files for the user.
    Method Type: MAIN - DRIVER

    Special Restrictions
    --------------------
    There must be a file with name test_cases in the same directory as driver.py.
    utils.py must be in the same directory as driver.py.
    All the test_cases files must be in the same directory as driver.py.

    Parameters
    ----------
    class_name : str
        Name of class to be tested using the generated autograder

    Returns
    -------
    status : int
        Returns 0 if program ran successfully, 1 for test_cases_not_found, 2 for ind_test_case_not_found
    """

    # Get list of test cases
    test_case_files = get_file_content('test_cases').split('\n\n')
    compilation_points = test_case_files[0]
    test_case_files = test_case_files[1:]

    autograder = open('autograder.java', mode='w')

    # MAIN AUTOGRADER WRITING BEGINS HERE #

    file_filler(autograder, common_header_data)

    file_filler(autograder, class_finder(class_name))

    # Test compilation and existence of main method

    file_filler(autograder, timeout_header(general_timeout))
    file_filler(autograder, gradescope_header("Compilation/Main Method", compilation_points))
    file_filler(autograder, main_method_checker(class_name))

    # Test Case Printing Starts Here

    for test_case in test_case_files:
        case_data = test_case.split('\n')
        if case_data[0] == 'SC':
            case_data = case_data[1:]
            string_comparison(autograder, case_data, class_name)
        elif case_data[0] == 'VC':
            case_data = case_data[1:]
            variable_finder(autograder, case_data, class_name)
        elif case_data[0] == 'IF':
            case_data = case_data[1:]
            interface_finder(autograder, case_data, class_name)

    # Footer printing statement

    file_filler(autograder, common_footer_data)

    # MAIN AUTOGRADER WRITING ENDS HERE #

    autograder.close()
    return 0
