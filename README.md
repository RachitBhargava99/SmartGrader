# Smart Grader
Welcome to Smart Grader - a user-friendly compiler for GradeScope's autograder!

## Features
### String Comparison
This feature allows you to compare the user output with the sample output at the exact line provided.
Please note that this string comparison method checks is case sensitive in nature.
UPDATE: String comparison now allows you to check if a given string is present in the user output at some provided line number.

### Variable Finder
This feature allows you to search for variables, no matter where they might be in the class.
However, this method does not search for any local variables.
You may search for instance variables / static variables only using this feature.

### Interface Finder
This feature allows you to search for interface in the submitted files.

### Method Existence Checker
This feature allows you to check for presence of some method.

### Method Return Value Verifier
This feature allows you to ensure that some given method is working as it should.
In other words, it checks whether or not is a given function returning the desired value.

### Element Finder
This feature allows you to look for some of the common elements in the submitted code.
The elements currently supported include loops and variable-dimensional arrays

## How To Use
### Basic Setup
The file must start with the following data:

        Number of points to be awarded if the file compiles and main method exists
        Flag to specify whether or not to check for the main method in primary class
        Primary Class Name
        Secondary Class Name(s) - OPTIONAL

After this configuration is set up, you may go ahead with the main test cases based on their types as follows.
Please note that you may raise a help request if you need any feature to be added to the Smart Grader if it does not
exist yet.

### String Comparison
The test cases in test_cases file must have 'SC' before the main test case information begin.
Here's the format the information is expected in starting at line 0, after the line containing 'SC':

        0   - filename : str
        1   - display_name : str
        2   - points : str
        3   - success_message : str
        4+  - error message

### Variable Finder
The test case in test_cases file must have 'VC' before the main test specifications.
Here's the format expected for the test specifications:

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

### Interface Finder
The test case in test_cases file must have 'IF' before the main test specifications
Here's the format expected for the test specifications:

        0   - display_name : str
        1   - points : str
        2   - interface_name : str
        3   - success_message : str

### Method Existence Checker
The test case in test_cases file must have 'ME' before the main test specifications.
Here's the format expected for the test specifications:

        0   - display_name : str
        1   - points : str
        2   - class_name : str
        3   - method_name : str
        4   - success_message : str

### Method Return Value Verifier
The test case in test_cases file must have 'MC' before the main test specifications.
Here's the format expected for the test specifications:

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

### Element Finder
The test case in test_cases file must have 'EF' before the main test specifications.
Here's the format expected for the test specifications:

        0   - display_name : str
        1   - points : str
        2   - class_name : str
        3   - element_name : str
            L for loop
            A for Array
        4   - extra_inputs : str    OPTIONAL
            Loop
                N/A
            Array
                Number of Dimensions of Array, Base Data Type of Array
        5   - success_message : str
