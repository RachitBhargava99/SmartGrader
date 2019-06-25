#Smart Grader
Welcome to Smart Grader - a user-friendly compiler for GradeScope's autograder!

##Features
###String Comparison
This feature allows you to compare the user output with the sample output at the exact line provided.
Please note that this string comparison method checks is case sensitive in nature.

##How To Use
###Basic Setup
The file must start with the following data:

        Number of points to be awarded if the file compiles and main method exists

After this flag is set up, you may go ahead with the main test cases based on their types as follows.
Please note that you may raise a help request if you need any feature to be added to the Smart Grader if it does not
exist yet.

###String Comparison
The test cases in test_cases file must have 'SC' before the main test case information begin.
Here's the format the information is expected in starting at line 0, after the line containing 'SC':

        0   - filename : str
        1   - display_name : str
        2   - points : str
        3   - success_message : str
        4+  - error message

