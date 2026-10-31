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

	## Assignment 1: AIPrompt
```
Develop a Python program that accepts Student ID, Student Name, and Course Name from the user and saves the information in a file named student.txt. Display a confirmation message after saving the details.
```

# Assignment 2: Student Information – Read from a File
Objective: Practice reading file contents using read().

Requirements:
1. Open the existing student.txt file.
2. Read all student information.
3. Display the file contents on the console.
4. Handle the situation where the file does not exist.
   
	## Assignment 2: AIPrompt
```
	Develop a Python program that reads student information from student.txt and displays all stored student records on the console. Display an appropriate message if the file is unavailable.
```

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

	## Assignment 3: AIPrompt
```
Develop a Python program that accepts Employee ID, Name, Department, and Salary from the user and adds the employee details to employee.txt. Previously stored employee records must remain unchanged.
```

# Assignment 4:
Student Records – Read Line by Line
Objective: Practice using readline() and file iteration.

Requirements:
1. Create a file named students.txt containing at least five student records.
2. Read the file one line at a time.
3. Display each student record separately.
4. Count and display the total number of student records.

	## Assignment 4: AIPrompt
```
Develop a Python program that reads student records from students.txt and displays each student record separately. Also, display the total number of students stored in the file.
``` 

# Assignment 5:Student Attendance – Count Records
Objective: Practice using readlines().

Requirements:
1. Create a file named attendance.txt.
2. Store student attendance records in the file.
3. Read all records using readlines().
4. Count the total number of records.
5. Display the attendance records and total count.

	## Assignment 5: AIPrompt
```
Develop a Python program that reads student attendance records from attendance.txt. Display each student's ID, Name, and Attendance Status. Calculate and display the total number of attendance records.
```

 
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

	## Assignment 6: AIPrompt
```
Develop a menu-driven Notes Application that allows users to add new notes, view previously saved notes, and exit the application. All notes must be stored permanently in notes.txt.
```

# Assignment 7: File Management Using pathlib
Objective: Practice modern file and directory management.

Requirements:
1. Import the Path class from pathlib.
2. Create a directory named EmployeeData.
3. Create a file named employee.txt inside the directory.
4. Write employee details into the file using write_text().
5. Check whether the file exists using exists().
6. Display the file name and extension.

	## Assignment 7: AIPrompt
```
	Develop a Python program to create an EmployeeData folder and an employee.txt file inside it. Store employee information in the file and display details about the created file, including its name, extension, and existence status.
```

# Assignment 8: File Handling with Exception Handling
Objective: Practice handling file-related exceptions.

Requirements:
1. Ask the user to enter a file name.
2. Attempt to open the specified file in read mode.
3. Display the file contents if the file exists.
4. Handle FileNotFoundError.
5. Handle PermissionError.
6. Display an appropriate error message.

	## Assignment 8: AIPrompt
```
Develop a Python program that accepts a file name from the user and displays its contents. If the file does not exist or cannot be accessed, display an appropriate error message instead of terminating the application unexpectedly.
```
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

	## Assignment 9: AIPrompt
```
Develop a Python application that reads an article from article.txt and calculates the total number of characters, words, and lines. Display the calculated statistics.
```

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

	## Assignment 10: AIPrompt
```
Develop a menu-driven Student Result Management System that allows users to add student results, view all student records, search for a student using Student ID, and exit the application. Store all student information in results.txt.
```
