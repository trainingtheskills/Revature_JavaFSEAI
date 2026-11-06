# Linux OS, Basic Linux Commands & Shell Scripting -- Interview Questions and Answers

**Level:** Beginner to Intermediate\
**Topics:** Linux OS, Linux commands, Shell scripting, loops,
conditions, GREP, CUT, file processing and practical scenarios.

------------------------------------------------------------------------

## Section 1: Introduction to Linux OS

### Q1. What is Linux?

**Answer:** Linux is an open-source, Unix-like operating system based on
the Linux kernel. It manages hardware resources and provides an
environment for executing applications. It is widely used in servers,
cloud infrastructure, software development, databases, DevOps and
embedded systems.

### Q2. What is an operating system?

**Answer:** An operating system is system software that acts as an
interface between users, applications and computer hardware. Its
responsibilities include process management, memory management, file
management, device management, security and user management.

### Q3. What is the difference between Linux and Windows?

  -----------------------------------------------------------------------
  Linux                               Windows
  ----------------------------------- -----------------------------------
  Open-source operating system        Proprietary operating system

  Supports command-line and GUI       Supports command-line and GUI

  Highly customizable                 More standardized environment

  Commonly used for servers           Commonly used for desktops and
                                      servers

  Bash is a common shell              PowerShell and Command Prompt are
                                      common shells
  -----------------------------------------------------------------------

### Q4. What is a Linux distribution?

**Answer:** A Linux distribution is a complete operating system built
around the Linux kernel, along with system utilities, package managers,
libraries and applications. Examples include Ubuntu, Red Hat Enterprise
Linux (RHEL), Fedora, Debian, CentOS Stream and Linux Mint.

### Q5. What is the Linux kernel?

**Answer:** The kernel is the core component of the Linux operating
system. It manages communication between software and hardware,
including CPU scheduling, memory management, device drivers, process
management, system calls and file-system operations.

### Q6. What is a shell in Linux?

**Answer:** A shell is a command-line interpreter that accepts commands
from users, interprets them and requests the operating system to execute
them. Examples include Bash, Zsh, Fish, Ksh and Sh.

### Q7. What is the difference between Shell and Kernel?

  -----------------------------------------------------------------------
  Shell                               Kernel
  ----------------------------------- -----------------------------------
  Interface between user and OS       Core of the operating system
  services                            

  Accepts and interprets commands     Manages hardware and system
                                      resources

  User-facing command interpreter     Runs with privileged access

  Examples: Bash, Zsh                 Linux kernel
  -----------------------------------------------------------------------

### Q8. What are CLI and GUI?

**Answer:** - **CLI (Command Line Interface):** Users interact with the
system by entering commands. - **GUI (Graphical User Interface):** Users
interact using windows, icons, menus and graphical controls.

### Q9. What is an open-source operating system?

**Answer:** An open-source operating system provides access to its
source code under an open-source license, allowing users to study,
modify and redistribute it according to the license terms. Linux is a
prominent example.

### Q10. What is a process in Linux?

**Answer:** A process is an instance of a running program. Each process
generally has a Process ID (PID), process state, memory allocation,
execution context and associated resources.

``` bash
ps aux
```

This displays running processes.

### Q11. What is the difference between a process and a thread?

**Answer:** A process is an executing program with its own address space
and resources. A thread is an execution unit within a process. Multiple
threads within the same process generally share memory and resources.

### Q12. What is PID?

**Answer:** PID stands for Process ID. It is an identifier assigned to a
process by the operating system.

``` bash
ps -ef
```

### Q13. What is the root user?

**Answer:** The root user is the superuser in Linux. It has extensive
administrative privileges to manage files, users, processes and system
configurations.

### Q14. What is the difference between absolute and relative paths?

  -----------------------------------------------------------------------
  Absolute Path                       Relative Path
  ----------------------------------- -----------------------------------
  Starts from the root directory `/`  Starts from the current directory

  Specifies the complete path         Specifies the path relative to the
                                      current location

  Example: `/home/user/data.txt`      Example: `data/data.txt`
  -----------------------------------------------------------------------

### Q15. What is the Linux file-system hierarchy?

**Answer:** Linux organizes files and directories in a hierarchical
structure starting from `/`, called the root directory.

  Directory   Purpose
  ----------- ----------------------------------------
  `/`         Root directory
  `/home`     User home directories
  `/root`     Root user's home directory
  `/etc`      System configuration files
  `/var`      Variable data and logs
  `/tmp`      Temporary files
  `/bin`      Essential user commands
  `/usr`      User applications and shared resources
  `/opt`      Optional software packages

------------------------------------------------------------------------

## Section 2: Basic Linux Commands

### Q16. How do you check the current working directory?

**Answer:**

``` bash
pwd
```

Displays the present working directory.

### Q17. How do you list files and directories?

**Answer:**

``` bash
ls
ls -l
ls -a
ls -lh
ls -ltr
```

-   `-l`: Long listing format.
-   `-a`: Includes hidden files.
-   `-h`: Human-readable file sizes.
-   `-t`: Sort by modification time.
-   `-r`: Reverse sorting order.

### Q18. How do you create a directory?

**Answer:**

``` bash
mkdir reports
mkdir -p reports/2026/november
```

The `-p` option creates parent directories as needed.

### Q19. How do you create an empty file?

**Answer:**

``` bash
touch emp.txt
```

### Q20. How do you display the content of a file?

**Answer:**

``` bash
cat emp.txt
more emp.txt
less emp.txt
head emp.txt
tail emp.txt
```

### Q21. How do you copy a file?

**Answer:**

``` bash
cp emp.txt backup.txt
cp -r reports reports_backup
```

The `-r` option copies a directory recursively.

### Q22. How do you move or rename a file?

**Answer:**

``` bash
mv emp.txt employee.txt
```

### Q23. How do you delete a file and directory?

**Answer:**

``` bash
rm emp.txt
rmdir reports
rm -r reports
```

`rmdir` removes an empty directory. Use `rm -r` carefully because it
removes a directory and its contents.

### Q24. How do you count lines, words and characters in a file?

**Answer:**

``` bash
wc emp.txt
wc -l emp.txt
wc -w emp.txt
wc -c emp.txt
```

The options count lines, words and bytes respectively.

### Q25. How do you search for a file in Linux?

**Answer:**

``` bash
find /home -name "emp.txt"
find . -name "*.txt"
```

### Q26. What is the purpose of chmod?

**Answer:** `chmod` changes file and directory permissions.

``` bash
chmod +x script.sh
chmod 755 script.sh
```

Permission values:

    Number Permission
  -------- ------------------------
         4 Read
         2 Write
         1 Execute
         7 Read + Write + Execute
         5 Read + Execute

### Q27. What is the difference between chmod and chown?

**Answer:** - `chmod`: Changes file permissions. - `chown`: Changes file
ownership.

``` bash
chmod 644 emp.txt
sudo chown user:user emp.txt
```

### Q28. How do you check running processes?

**Answer:**

``` bash
ps
ps aux
ps -ef
top
```

### Q29. How do you terminate a process?

**Answer:**

``` bash
kill 1234
kill -9 1234
```

The second command sends SIGKILL and should be used only when a normal
termination is insufficient.

### Q30. How do you check disk usage?

**Answer:**

``` bash
df -h
du -sh /home/user
```

`df` reports file-system space usage; `du` reports space used by files
and directories.

### Q31. What is the difference between `>` and `>>`?

**Answer:**

  Operator   Purpose
  ---------- ------------------------------
  `>`        Creates or overwrites a file
  `>>`       Appends content to a file

``` bash
echo "Hello" > output.txt
echo "Welcome" >> output.txt
```

### Q32. What is a pipe in Linux?

**Answer:** A pipe (`|`) passes the output of one command as the input
of another command.

``` bash
cat emp.txt | grep "IT"
```

### Q33. What is the difference between `cat`, `head` and `tail`?

**Answer:** - `cat`: Displays the entire file. - `head`: Displays the
first 10 lines by default. - `tail`: Displays the last 10 lines by
default.

``` bash
head -5 emp.txt
tail -5 emp.txt
```

------------------------------------------------------------------------

## Section 3: Shell Scripting Fundamentals

### Q34. What is shell scripting?

**Answer:** Shell scripting is the process of writing a series of shell
commands in a file to automate repetitive tasks. A shell script commonly
uses the `.sh` extension.

``` bash
#!/bin/bash

echo "Welcome to Linux Shell Scripting"
```

### Q35. What is the purpose of the shebang line?

**Answer:** The shebang line specifies which interpreter should execute
the script.

``` bash
#!/bin/bash
```

### Q36. How do you execute a shell script?

**Answer:**

``` bash
chmod +x test.sh
./test.sh
```

Alternatively:

``` bash
bash test.sh
```

### Q37. How do you declare a variable in Bash?

**Answer:**

``` bash
name="Rahul"
age=25

echo "$name"
echo "$age"
```

Do not put spaces around the assignment operator. `name="Rahul"` is
correct; `name = "Rahul"` is incorrect.

### Q38. How do you accept input from a user?

**Answer:** Use the `read` command.

``` bash
#!/bin/bash

read -p "Enter your name: " name
echo "Welcome $name"
```

### Q39. What is the difference between single and double quotes?

**Answer:** Single quotes preserve literal text. Double quotes allow
variable and command substitution.

``` bash
name="Rahul"

echo 'Hello $name'
echo "Hello $name"
```

Output:

``` text
Hello $name
Hello Rahul
```

### Q40. What is command substitution?

**Answer:** Command substitution captures the output of a command and
assigns it to a variable.

``` bash
current_date=$(date)
echo "$current_date"
```

### Q41. What are positional parameters in shell scripting?

**Answer:** Positional parameters represent arguments passed to a shell
script.

``` bash
#!/bin/bash

echo "First argument: $1"
echo "Second argument: $2"
echo "Total arguments: $#"
```

Execute:

``` bash
bash test.sh Rahul IT
```

Output:

``` text
First argument: Rahul
Second argument: IT
Total arguments: 2
```

Important special variables:

  Variable   Meaning
  ---------- -----------------------------
  `$0`       Script name
  `$1`       First argument
  `$2`       Second argument
  `$#`       Number of arguments
  `$@`       All arguments
  `$?`       Exit status of last command
  `$$`       Current shell process ID

### Q42. What is the difference between an environment variable and a local shell variable?

**Answer:** A shell variable is available in the current shell and its
execution context. An exported environment variable is also inherited by
child processes.

``` bash
name="Rahul"
export CITY="Pune"
```

### Q43. What is the purpose of comments in shell scripting?

**Answer:** Comments explain the purpose of code and improve
readability.

``` bash
#!/bin/bash

# Display employee information
echo "Employee Details"
```

------------------------------------------------------------------------

## Section 4: Conditional Statements and Loops

### Q44. What is an if statement?

**Answer:** An `if` statement executes a block of code when a specified
condition is true.

``` bash
#!/bin/bash

read -p "Enter age: " age

if [ "$age" -ge 18 ]; then
    echo "Eligible to vote"
fi
```

### Q45. What is the difference between if and if-else?

**Answer:** - `if`: Executes code only when the condition is true. -
`if-else`: Executes one block when the condition is true and another
when it is false.

### Q46. What is an if-elif-else statement?

**Answer:** It is used to evaluate multiple conditions sequentially.

``` bash
#!/bin/bash

read -p "Enter marks: " marks

if [ "$marks" -ge 90 ]; then
    echo "Grade A"
elif [ "$marks" -ge 75 ]; then
    echo "Grade B"
elif [ "$marks" -ge 50 ]; then
    echo "Grade C"
else
    echo "Fail"
fi
```

### Q47. What are the different comparison operators in Bash?

  Operator   Meaning
  ---------- -----------------------
  `-eq`      Equal
  `-ne`      Not equal
  `-gt`      Greater than
  `-lt`      Less than
  `-ge`      Greater than or equal
  `-le`      Less than or equal
  `=`        String equality
  `!=`       String inequality

### Q48. What is a for loop?

**Answer:** A `for` loop executes a block of code repeatedly over a
sequence of values.

``` bash
#!/bin/bash

for i in 1 2 3 4 5
do
    echo "$i"
done
```

### Q49. What is a while loop?

**Answer:** A while loop executes repeatedly as long as its condition
remains true.

``` bash
#!/bin/bash

i=1

while [ "$i" -le 5 ]
do
    echo "$i"
    i=$((i+1))
done
```

### Q50. Does Bash support a do-while loop?

**Answer:** Bash does not have a native `do-while` construct. We can
simulate its behavior using `while true` and `break`.

``` bash
#!/bin/bash

while true
do
    read -p "Enter number (0 to exit): " num

    if [ "$num" -eq 0 ]; then
        break
    fi

    echo "Entered number: $num"
done
```

### Q51. What is the difference between break and continue?

**Answer:** - `break`: Terminates the current loop. - `continue`: Skips
the remaining statements in the current iteration and moves to the next
iteration.

``` bash
#!/bin/bash

for i in {1..5}
do
    if [ "$i" -eq 3 ]; then
        continue
    fi
    echo "$i"
done
```

Output:

``` text
1
2
4
5
```

### Q52. What is a nested loop?

**Answer:** A loop inside another loop is called a nested loop.

``` bash
#!/bin/bash

for i in 1 2 3
do
    for j in 1 2
    do
        echo "$i $j"
    done
done
```

### Q53. What is a case statement?

**Answer:** A `case` statement is used when a program needs to select
one action from multiple alternatives.

``` bash
#!/bin/bash

read -p "Enter choice: " choice

case $choice in
    1)
        echo "Create Employee"
        ;;
    2)
        echo "Search Employee"
        ;;
    3)
        echo "Delete Employee"
        ;;
    *)
        echo "Invalid choice"
        ;;
esac
```

------------------------------------------------------------------------

## Section 5: GREP, CUT and Text File Processing

For this section, consider the following `emp.txt` file:

``` text
101,Rahul,IT,50000,Pune
102,Priya,HR,45000,Mumbai
103,Amit,Finance,60000,Delhi
104,Neha,IT,55000,Pune
105,Rohan,HR,40000,Bangalore
```

### Q54. What is grep in Linux?

**Answer:** `grep` is a command-line utility used to search for matching
text patterns in files.

``` bash
grep "Rahul" emp.txt
```

### Q55. How do you search for IT department employees?

**Answer:**

``` bash
grep ",IT," emp.txt
```

### Q56. What is the difference between grep and grep -i?

**Answer:** - `grep`: Performs case-sensitive searching by default. -
`grep -i`: Performs case-insensitive searching.

``` bash
grep -i "rahul" emp.txt
```

### Q57. How do you count the number of IT employees?

**Answer:**

``` bash
grep -c ",IT," emp.txt
```

### Q58. How do you display records that do not contain IT?

**Answer:**

``` bash
grep -v ",IT," emp.txt
```

### Q59. What is the cut command?

**Answer:** `cut` extracts selected fields or character positions from
each input line.

``` bash
cut -d',' -f2 emp.txt
```

This extracts employee names.

### Q60. What is the purpose of -d and -f in cut?

**Answer:** - `-d`: Specifies the delimiter. - `-f`: Specifies the field
number or range to extract.

``` bash
cut -d',' -f1,2 emp.txt
```

### Q61. How do you display employee names and salaries?

**Answer:**

``` bash
cut -d',' -f2,4 emp.txt
```

### Q62. How do you combine grep and cut?

**Answer:** Combine them using a pipe.

``` bash
grep ",IT," emp.txt | cut -d',' -f2,4
```

This filters IT employees and extracts their names and salaries.

### Q63. What is the difference between grep and cut?

  grep                             cut
  -------------------------------- ----------------------------------
  Searches for matching patterns   Extracts fields or characters
  Filters matching lines           Extracts selected columns
  Used for searching               Used for data extraction
  Example: `grep "IT" emp.txt`     Example: `cut -d',' -f2 emp.txt`

### Q64. What is the difference between grep and awk?

**Answer:** `grep` is mainly used for pattern matching and filtering
lines. `awk` is a text-processing language that supports field
extraction, conditions, calculations and report generation.

``` bash
grep ",IT," emp.txt
```

Using awk:

``` bash
awk -F',' '$3=="IT" {print $2,$4}' emp.txt
```

### Q65. How do you count the total number of lines in a file?

**Answer:**

``` bash
wc -l emp.txt
```

### Q66. How do you search multiple departments using grep?

**Answer:**

``` bash
grep -E ",(IT|HR)," emp.txt
```

### Q67. How do you sort employee records?

**Answer:**

``` bash
sort emp.txt
```

Sort by salary numerically:

``` bash
sort -t',' -k4,4n emp.txt
```

------------------------------------------------------------------------

## Section 6: Practical Scenario-Based Interview Questions

### Q68. Your application generates a log file. How would you find all ERROR messages?

**Answer:**

``` bash
grep "ERROR" application.log
```

To count errors:

``` bash
grep -c "ERROR" application.log
```

### Q69. You want to display the last 20 lines of an application log file. Which command would you use?

**Answer:**

``` bash
tail -20 application.log
```

### Q70. How would you monitor a log file continuously?

**Answer:**

``` bash
tail -f application.log
```

This displays new lines as they are appended to the file.

### Q71. You want to identify all employees belonging to IT and save their details in another file. How would you do it?

**Answer:**

``` bash
grep ",IT," emp.txt > it_employees.txt
```

### Q72. How would you extract employee names and departments and save them in a report?

**Answer:**

``` bash
cut -d',' -f2,3 emp.txt > employee_report.txt
```

### Q73. How would you identify employees earning a salary greater than 50000?

**Answer:** Use `awk` for numeric comparison.

``` bash
awk -F',' '$4 > 50000 {print $0}' emp.txt
```

### Q74. How would you generate a department-wise employee count?

**Answer:**

``` bash
awk -F',' '{
    count[$3]++
}
END {
    for (dept in count)
        print dept, count[dept]
}' emp.txt
```

### Q75. Your shell script is not executing. What could be the reasons?

**Answer:** Possible reasons include: 1. Missing execute permission. 2.
Incorrect shebang. 3. Syntax errors. 4. Incorrect file path. 5. Missing
interpreter. 6. Invalid commands or variables.

Troubleshooting:

``` bash
chmod +x script.sh
bash -n script.sh
bash -x script.sh
```

### Q76. How would you check whether a file exists before processing it?

**Answer:**

``` bash
#!/bin/bash

if [ -f "emp.txt" ]; then
    echo "File exists"
else
    echo "File not found"
fi
```

### Q77. How would you schedule a shell script to execute daily?

**Answer:** Use `cron`.

Open the crontab editor:

``` bash
crontab -e
```

Example:

``` bash
0 9 * * * /home/user/backup.sh
```

This schedules the script to run daily at 9:00 AM according to the cron
system's timezone.

### Q78. How would you automate a backup of a directory?

**Answer:**

``` bash
#!/bin/bash

SOURCE="/home/user/documents"
DEST="/home/user/backup"

mkdir -p "$DEST"
cp -r "$SOURCE" "$DEST"

echo "Backup completed"
```

For production environments, tools such as `rsync` are often preferable
for incremental backups.

------------------------------------------------------------------------

## Section 7: Programming-Based Interview Questions

These questions require candidates to write shell scripts rather than
simply explain commands.

  ------------------------------------------------------------------------
                           No. Interview question    Expected concepts
  ---------------------------- --------------------- ---------------------
                             1 Write a script to     Input/output,
                               accept two numbers    variables
                               and perform           
                               arithmetic            
                               operations.           

                             2 Write a script to     If-else, modulus
                               check whether a       
                               number is even or     
                               odd.                  

                             3 Write a script to     Nested if
                               find the largest of   
                               three numbers.        

                             4 Write a script to     While loop
                               calculate factorial   
                               of a number.          

                             5 Write a script to     For loop
                               display               
                               multiplication        
                               tables.               

                             6 Write a script to     While, break
                               accept numbers until  
                               zero is entered.      

                             7 Write a script to     File handling
                               read and display all  
                               lines from a text     
                               file.                 

                             8 Write a script to     Grep
                               search an employee by 
                               name in emp.txt.      

                             9 Write a script to     Cut
                               display employee      
                               names and salaries.   

                            10 Write a script to     Grep, loops
                               count employees by    
                               department.           

                            11 Write a script to     Awk
                               find employees        
                               earning above 50000.  

                            12 Write a script to     Grep, cut,
                               generate a            redirection
                               department-wise       
                               report.               

                            13 Write a menu-driven   Case, loops
                               file management       
                               utility.              

                            14 Write a script to     File conditions
                               check whether a       
                               directory exists.     

                            15 Write a script to     Grep, date,
                               generate a daily      redirection
                               application log       
                               summary.              
  ------------------------------------------------------------------------

------------------------------------------------------------------------

## Section 8: Quick Revision MCQs

### Q1. Which command displays the current working directory?

A. `ls`\
B. `pwd`\
C. `cd`\
D. `cat`

**Answer:** B. `pwd` --- prints the present working directory.

### Q2. Which command provides execute permission?

A. `chmod +x`\
B. `chown`\
C. `grep`\
D. `touch`

**Answer:** A. `chmod +x` --- adds execute permission.

### Q3. Which component is the core of Linux?

A. Shell\
B. Terminal\
C. Kernel\
D. Bash

**Answer:** C. Kernel --- manages hardware and system resources.

### Q4. Which command searches for text in a file?

A. `cut`\
B. `grep`\
C. `mkdir`\
D. `pwd`

**Answer:** B. `grep` --- searches for matching text patterns.

### Q5. Which operator appends output to a file?

A. `>`\
B. `<`\
C. `|`\
D. `>>`

**Answer:** D. `>>` --- appends output without overwriting existing
content.

### Q6. Which command extracts the second field?

A. `cut -f2 file`\
B. `grep -f2 file`\
C. `ls -f2`\
D. `cat -f2`

**Answer:** A. `cut -f2 file` --- selects the second field. Specify `-d`
if the delimiter is not the default tab.

### Q7. Which Bash variable stores the first command-line argument?

A. `$0`\
B. `$1`\
C. `$#`\
D. `$?`

**Answer:** B. `$1` --- refers to the first positional argument.

### Q8. Which statement terminates a loop?

A. `continue`\
B. `skip`\
C. `break`\
D. `exit-loop`

**Answer:** C. `break` --- terminates the current loop.

### Q9. Does Bash have a native do-while loop?

A. Yes\
B. No\
C. Only in Linux\
D. Only in Zsh

**Answer:** B. No --- Bash simulates do-while behavior using
`while true` and `break`.

### Q10. Which command counts the number of lines?

A. `wc -l`\
B. `wc -w`\
C. `cut -l`\
D. `grep -l`

**Answer:** A. `wc -l` --- counts newline characters (normally one per
text record).

------------------------------------------------------------------------

## Section 9: Suggested Interview Preparation Strategy

  Preparation area     Focus
  -------------------- -------------------------------------------
  Linux fundamentals   Explain OS, kernel, shell and file system
  Commands             Practice commands directly in Terminal
  Shell scripting      Write scripts independently
  File processing      Practice `grep`, `cut`, `awk` and pipes
  Automation           Build menu-driven utilities and reports

### Trainer's recommendation

For entry-level Linux/DevOps interviews, give particular importance to
practical command execution, file permissions, process management, shell
loops, conditional statements and text-file processing. Candidates
should be able to write and explain at least 5--10 shell scripts
independently.
