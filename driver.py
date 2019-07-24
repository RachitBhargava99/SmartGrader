from utils import get_file_content, file_filler, string_comparison, variable_finder, interface_finder, method_finder,\
    method_value_verifier, element_finder
from common_data import common_header_data, common_footer_data, general_timeout, timeout_header, gradescope_header,\
    main_method_checker, common_closing_data, general_method_header, general_prompt_creator, general_assertion_creator,\
    general_success_filler, class_finder, class_finder_header


def smart_grader_driver():

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
    basic_data = test_case_files[0].split('\n')
    compilation_points = basic_data[0]
    check_main = basic_data[1]
    class_name = basic_data[2]
    secondary_class_names = basic_data[3:]
    test_case_files = test_case_files[1:]

    try:
        autograder = open('autograder.java', mode='w')
    except IOError:
        print("Sorry, we could not create / overwrite autograder.java." +
              " Please make sure that the file is not open in any other program before proceeding.")
        return 2

    # MAIN AUTOGRADER WRITING BEGINS HERE #

    file_filler(autograder, common_header_data)

    file_filler(autograder, class_finder(class_name))

    # Test compilation and existence of main method

    file_filler(autograder, timeout_header(general_timeout))
    file_filler(autograder, gradescope_header("Compilation/Main Method" if check_main == 'T' else "Compilation",
                                              compilation_points))
    file_filler(autograder, class_finder_header())
    file_filler(autograder, class_finder(class_name))
    [file_filler(autograder, x) for x in secondary_class_names]
    if check_main == 'T':
        file_filler(autograder, main_method_checker(class_name))
    file_filler(autograder, common_closing_data)

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
        elif case_data[0] == 'ME':
            case_data = case_data[1:]
            method_finder(autograder, case_data, class_name)
        elif case_data[0] == 'MC':
            case_data = case_data[1:]
            method_value_verifier(autograder, case_data, class_name)
        elif case_data[0] == 'EF':
            case_data = case_data[1:]
            element_finder(autograder, case_data, class_name)

    # Footer printing statement

    file_filler(autograder, common_footer_data)

    # MAIN AUTOGRADER WRITING ENDS HERE #

    autograder.close()
    return 0
