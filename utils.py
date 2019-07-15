from common_data import *


def get_file_content(filename):

    """Gets the content of the file whose filename is provided
    Method Type: NORMAL - HELPER

    Special Restrictions
    --------------------
    The requested file must be in the same directory as utils.py

    Parameters
    ---------------
    filename : str
        Name of the file to be parsed

    Returns
    -------
    file_data : str
        Contents of the file in string format
    """

    file = open(filename, mode='r')
    file_data = file.read()
    file.close()
    return file_data


def file_filler(file, data):

    """Fills the file with the provided data
    Method Type: NORMAL - HELPER

    Parameters
    ----------
    file : <class '_io.TextIOWrapper'>
        File to be written to

    Returns
    -------
    status : int
        Returns 0 for success, 1 for failure
    """

    file.write(data)
    return 0


def string_comparison(file, case_data, class_name):

    """Fills the file with the string comparison data
    Method Type: NORMAL - HELPER

    Parameters
    ----------
    file : <class '_io.TextIOWrapper'>
        File to be written to
    case_data : List with all data necessary for the function to work
        0   - filename : str
        1   - display_name : str
        2   - points : str
        3   - success_message : str
        4+  - error message

    Returns
    -------
    status : int
        Returns 0 for success, 1 for failure
    """

    filename = case_data[0]
    print(filename)
    file_data = get_file_content(filename)
    test_lines = file_data.split('\n')
    input_lines = [(test_lines[x][3:], x) for x in range(len(test_lines)) if test_lines[x][:3] == '[I]']
    output_lines = [(test_lines[x][3:], test_lines[x][1]) for x in range(len(test_lines)) if
                    test_lines[x][:3] == '[O]' or test_lines[x][:3] == '[T]']
    ignore_lines = [(output_lines[x][0], x) for x in range(len(output_lines)) if output_lines[x][1] == 'O']
    test_lines = [(output_lines[x][0], x) for x in range(len(output_lines)) if output_lines[x][1] == 'T']
    input_text = '\\n'.join([x[0] for x in input_lines])
    # print(f"Input Lines: {input_lines}\nIgnore Lines: {ignore_lines}\nTest Lines: {test_lines}" +
    #       f"\nInput Text: {input_text}")

    file_filler(file, timeout_header(general_timeout))
    file_filler(file, gradescope_header(case_data[1], case_data[2]))
    file_filler(file, general_method_header(case_data[0]))

    file_filler(file, general_prompt_creator(class_name, input_text))

    for i in range(len(test_lines)):
        file_filler(file, general_assertion_creator(test_lines[i][0], test_lines[i][1], case_data[4 + i]))

    file_filler(file, general_success_filler(case_data[3]))

    file_filler(file, common_closing_data)


def variable_finder(file, case_data, class_name):

    """Fills the file with the string comparison data
    Method Type: NORMAL - HELPER

    Parameters
    ----------
    file : <class '_io.TextIOWrapper'>
        File to be written to
    case_data : List with all data necessary for the function to work
        0   - display_name : str
        1   - points : str
        2   - variable_name : str
        3   - check_parameters : str
                T for variable_type
                A for access_specifier
                S for isStatic
        4   - variable_type : str       OPTIONAL
        5   - access_specifier : str    OPTIONAL
        6   - isStatic : str            OPTIONAL
                true / false
        7   - success_message : str

    Returns
    -------
    status : int
        Returns 0 for success, 1 for failure
    """

    file_filler(file, timeout_header(general_timeout))
    file_filler(file, gradescope_header(case_data[0], case_data[1]))
    file_filler(file, general_method_header(f'test_{case_data[2]}'))
    file_filler(file, basic_variable_finder(case_data[2]))

    check_parameters = case_data[3]
    i = 4
    if 'T' in check_parameters:
        file_filler(file, type_checker(case_data[i]))
        i += 1
    if 'A' in check_parameters:
        file_filler(file, access_specifier_checker(case_data[i]))
        i += 1
    if 'S' in check_parameters:
        file_filler(file, static_checker(True if case_data[i] == 'true' else False))
        i += 1

    file_filler(file, general_success_filler(case_data[i]))
    file_filler(file, common_closing_data)


def interface_finder(file, case_data, class_name):

    """Fills the file with the string comparison data
    Method Type: NORMAL - HELPER

    Parameters
    ----------
    file : <class '_io.TextIOWrapper'>
        File to be written to
    case_data : List with all data necessary for the function to work
        0   - display_name : str
        1   - points : str
        2   - interface_name : str
        3   - success_message : str

    Returns
    -------
    status : int
        Returns 0 for success, 1 for failure
    """

    # TODO: Add feature to check for variables and abstract / default methods in interfaces

    file_filler(file, timeout_header(general_timeout))
    file_filler(file, gradescope_header(case_data[0], case_data[1]))
    file_filler(file, general_method_header(f'test_interface_{case_data[2]}'))

    file_filler(file, basic_interface_finder(case_data[2]))

    file_filler(file, general_success_filler(case_data[3]))
    file_filler(file, common_closing_data)


def method_finder(file, case_data, class_name):

    """Fills the file with the string comparison data
    Method Type: NORMAL - HELPER

    Parameters
    ----------
    file : <class '_io.TextIOWrapper'>
        File to be written to
    case_data : List with all data necessary for the function to work
        0   - display_name : str
        1   - points : str
        2   - class_name : str
        3   - method_name : str
        4   - success_message : str

    Returns
    -------
    status : int
        Returns 0 for success, 1 for failure
    """

    file_filler(file, timeout_header(general_timeout))
    file_filler(file, gradescope_header(case_data[0], case_data[1]))
    file_filler(file, general_method_header(f'test_{case_data[3]}_exists'))

    file_filler(file, basic_method_finder(case_data[2], case_data[3]))

    file_filler(file, general_success_filler(case_data[4]))
    file_filler(file, common_closing_data)


def method_value_verifier(file, case_data, class_name):

    """Fills the file with the string comparison data
    Method Type: NORMAL - HELPER

    Parameters
    ----------
    file : <class '_io.TextIOWrapper'>
        File to be written to
    case_data : List with all data necessary for the function to work
        0   - display_name : str
        1   - points : str
        2   - class_name : str
        3   - method_name : str
        4   - input_types : str
            Comma-separated input data types
        5   - sample_inputs : str
            Comma-separated sample inputs
        6   - expected_return : str
            Expected return value (enter 'void' for void return type / no return expected)
        7   - success_message : str

    Returns
    -------
    status : int
        Returns 0 for success, 1 for failure
    """

    file_filler(file, timeout_header(general_timeout))
    file_filler(file, gradescope_header(case_data[0], case_data[1]))
    file_filler(file, general_method_header(f'test_{case_data[3]}_exists'))

    file_filler(file, basic_method_finder(case_data[2], case_data[3]))
    file_filler(file, method_return_value_checker(case_data[2], case_data[3], [x.strip() for x in case_data[4].split(',')], [x.strip() for x in case_data[5].split(',')], True if case_data[6] != 'void' else False, None if case_data[6] == 'void' else case_data[6]))

    file_filler(file, general_success_filler(case_data[4]))
    file_filler(file, common_closing_data)
