# Assignments

# Assignment 1: Student Information – Write to a File
Objective: Practice creating and writing data into a file.

Requirements:
1. Accept student details:
   - Student ID
   - Student Name
   - Course Name
2. Write the details into student.txt.
3. Display a success message after writing the data.

```
Develop a Python program to accept Student ID, Student Name, and Course Name from the user and save the details into a file named `student.txt`.

**Requirements:**
- Use the `open()` function in write (`'w'`) mode.
- Use the `write()` method to store student details.
- Store each student's information on a separate line.
- Display a success message after saving the data.
- Use the `with` statement for file handling.
```

# Assignment 2: Student Information – Read from a File
Objective: Practice reading file contents using read().

Requirements:
1. Open the existing student.txt file.
2. Read all student information.
3. Display the file contents on the console.
4. Handle the situation where the file does not exist.


# Assignment 3: Employee Management – Append Data to a File
Objective: Practice appending records without deleting existing data.

Requirements:
1. Accept employee details:
   - Employee ID
   - Employee Name
   - Department
   - Salary
2. Append the employee details to employee.txt.
3. Allow multiple employee records to be added.
4. Preserve previously stored employee records.

   
# Assignment 4:
Student Records – Read Line by Line
Objective: Practice using readline() and file iteration.

Requirements:
1. Create a file named students.txt containing at least five student records.
2. Read the file one line at a time.
3. Display each student record separately.
4. Count and display the total number of student records.

   
# Assignment 5:Student Attendance – Count Records
Objective: Practice using readlines().

Requirements:
1. Create a file named attendance.txt.
2. Store student attendance records in the file.
3. Read all records using readlines().
4. Count the total number of records.
5. Display the attendance records and total count.

   
# Assignment 6: Simple Notes Application
Objective: Practice writing and appending text.

Requirements:
1. Display a menu:
   - Add a new note.
   - View all notes.
   - Exit.
2. Accept note content from the user.
3. Save each note in notes.txt.
4. Use append mode to preserve existing notes.
5. Allow users to view all previously saved notes.

6. 
# Assignment 7: File Management Using pathlib
Objective: Practice modern file and directory management.

Requirements:
1. Import the Path class from pathlib.
2. Create a directory named EmployeeData.
3. Create a file named employee.txt inside the directory.
4. Write employee details into the file using write_text().
5. Check whether the file exists using exists().
6. Display the file name and extension.
 
# Assignment 8: File Handling with Exception Handling
Objective: Practice handling file-related exceptions.

Requirements:
1. Ask the user to enter a file name.
2. Attempt to open the specified file in read mode.
3. Display the file contents if the file exists.
4. Handle FileNotFoundError.
5. Handle PermissionError.
6. Display an appropriate error message.

# Assignment 9:
Word Counter Application
Objective: Practice reading file content and processing strings.

Requirements:
1. Create a file named article.txt.
2. Store a paragraph in the file.
3. Read the complete file content using read().
4. Count the total number of:
   - Characters
   - Words
   - Lines
5. Display the calculated results.

# Assignment 10: Student Result Management System
Objective: Combine file reading, writing, appending, and exception handling.

Requirements:

1. Display the following menu:
===== Student Result Management =====

1. Add Student Result
2. View All Results
3. Search Student by ID
4. Exit


2. For Add Student Result:
   - Accept Student ID, Name, and Marks.
   - Store the details in results.txt.
   - Append new records without deleting existing records.
3. For View All Results:
   - Read all student records.
   - Display them on the console.
4. For Search Student by ID:
   - Accept Student ID.
   - Search for the corresponding record in the file.
   - Display the record if found.
   - Display an appropriate message if not found.
5. Handle missing files using exception handling.
