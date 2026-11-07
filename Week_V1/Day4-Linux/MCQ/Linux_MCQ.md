# Linux Multiple-Choice Questions (MCQs)

**Level:** Beginner to Intermediate  
**Topics:** Linux OS, Linux Commands, File Management, Permissions, Processes, Shell Scripting, GREP, CUT and Redirection.

## Section 1: Introduction to Linux

**1. What is Linux?**

A. A programming language  
B. An operating system  
C. A database  
D. A web browser  

**Answer: B. An operating system**

**2. Who created the Linux kernel?**

A. James Gosling  
B. Dennis Ritchie  
C. Linus Torvalds  
D. Bill Gates  

**Answer: C. Linus Torvalds**

**3. Which of the following is a Linux distribution?**

A. Ubuntu  
B. Oracle  
C. MySQL  
D. Python  

**Answer: A. Ubuntu**

**4. What is the core component of the Linux operating system?**

A. Shell  
B. Kernel  
C. Terminal  
D. File Manager  

**Answer: B. Kernel**

**5. Which of the following is an open-source operating system?**

A. Linux  
B. Windows  
C. macOS  
D. MS-DOS  

**Answer: A. Linux**

**6. What is the primary purpose of a shell in Linux?**

A. Manage databases  
B. Execute user commands  
C. Create hardware  
D. Design websites  

**Answer: B. Execute user commands**

## Section 2: Basic Linux Commands

**7. Which command displays the current working directory?**

A. `ls`  
B. `pwd`  
C. `cd`  
D. `mkdir`  

**Answer: B. `pwd`**

**8. Which command lists files and directories?**

A. `ls`  
B. `pwd`  
C. `cat`  
D. `touch`  

**Answer: A. `ls`**

**9. Which command is used to create a new directory?**

A. `rmdir`  
B. `mkdir`  
C. `rm`  
D. `cp`  

**Answer: B. `mkdir`**

**10. Which command creates an empty file?**

A. `cat`  
B. `touch`  
C. `grep`  
D. `mv`  

**Answer: B. `touch`**

**11. Which command is used to display the contents of a file?**

A. `cat`  
B. `mkdir`  
C. `chmod`  
D. `ps`  

**Answer: A. `cat`**

**12. Which command changes the current directory?**

A. `pwd`  
B. `cd`  
C. `ls`  
D. `find`  

**Answer: B. `cd`**

**13. Which command copies a file?**

A. `mv`  
B. `rm`  
C. `cp`  
D. `touch`  

**Answer: C. `cp`**

**14. Which command is used to rename or move a file?**

A. `mv`  
B. `cp`  
C. `rm`  
D. `find`  

**Answer: A. `mv`**

**15. Which command removes a file?**

A. `rmdir`  
B. `rm`  
C. `touch`  
D. `cat`  

**Answer: B. `rm`**

**16. Which command counts the number of lines, words, and characters in a file?**

A. `grep`  
B. `cut`  
C. `wc`  
D. `sort`  

**Answer: C. `wc`**

## Section 3: File Management and Permissions

**17. Which command is used to search for files and directories?**

A. `find`  
B. `pwd`  
C. `whoami`  
D. `date`  

**Answer: A. `find`**

**18. What does the `-l` option represent in the `ls -l` command?**

A. List only directories  
B. Long listing format  
C. List hidden files  
D. List recently modified files  

**Answer: B. Long listing format**

**19. Which command is used to change file permissions?**

A. `chown`  
B. `chmod`  
C. `chgrp`  
D. `chdir`  

**Answer: B. `chmod`**

**20. What does permission `r` represent in Linux?**

A. Rename  
B. Read  
C. Remove  
D. Run  

**Answer: B. Read**

**21. What does permission `w` represent?**

A. Write  
B. Work  
C. Wait  
D. Watch  

**Answer: A. Write**

**22. What is the numeric value of read permission?**

A. 1  
B. 2  
C. 4  
D. 7  

**Answer: C. 4**

**23. What does the command `chmod 755 script.sh` do?**

A. Gives everyone full permissions  
B. Gives the owner full permissions and group/others read and execute permissions  
C. Gives only read permissions  
D. Removes all permissions  

**Answer: B. Gives the owner full permissions and group/others read and execute permissions**

**24. Which command changes the ownership of a file?**

A. `chmod`  
B. `chown`  
C. `ls`  
D. `whoami`  

**Answer: B. `chown`**

## Section 4: Linux Processes and System Information

**25. Which command displays currently running processes?**

A. `ps`  
B. `pwd`  
C. `mkdir`  
D. `cut`  

**Answer: A. `ps`**

**26. What does PID stand for?**

A. Process Internal Directory  
B. Program Input Data  
C. Process ID  
D. Primary Internet Device  

**Answer: C. Process ID**

**27. Which command is commonly used to terminate a process using its PID?**

A. `stop`  
B. `kill`  
C. `exit`  
D. `remove`  

**Answer: B. `kill`**

**28. Which command displays disk space usage of mounted filesystems?**

A. `du`  
B. `df`  
C. `free`  
D. `ps`  

**Answer: B. `df`**

**29. Which command displays the size of files and directories?**

A. `du`  
B. `df`  
C. `pwd`  
D. `who`  

**Answer: A. `du`**

**30. Which command displays the current logged-in username?**

A. `who`  
B. `whoami`  
C. `users`  
D. `uname`  

**Answer: B. `whoami`**

## Section 5: GREP, CUT and Text Processing

**31. Which command is used to search for a specific pattern in a file?**

A. `cut`  
B. `grep`  
C. `sort`  
D. `wc`  

**Answer: B. `grep`**

**32. What does the following command do?**

```bash
grep "IT" emp.txt
```

A. Deletes IT records  
B. Displays lines containing IT  
C. Counts all records  
D. Displays only the first line  

**Answer: B. Displays lines containing IT**

**33. Which option makes grep search without considering uppercase or lowercase?**

A. `-v`  
B. `-c`  
C. `-i`  
D. `-n`  

**Answer: C. `-i`**

**34. What does `grep -c "IT" emp.txt` display?**

A. Matching lines  
B. Number of matching lines  
C. Line numbers  
D. Number of columns  

**Answer: B. Number of matching lines**

**35. Which command extracts specific columns or fields from a file?**

A. `grep`  
B. `cut`  
C. `find`  
D. `head`  

**Answer: B. `cut`**

**36. What does the following command do?**

```bash
cut -d',' -f2 emp.txt
```

A. Extracts the first field  
B. Extracts the second field using comma as delimiter  
C. Extracts the second line  
D. Deletes the second field  

**Answer: B. Extracts the second field using comma as delimiter**

**37. Which command displays the first 10 lines of a file by default?**

A. `tail`  
B. `head`  
C. `more`  
D. `less`  

**Answer: B. `head`**

**38. Which command displays the last 10 lines of a file by default?**

A. `head`  
B. `tail`  
C. `cat`  
D. `cut`  

**Answer: B. `tail`**

## Section 6: Linux Redirection and Pipes

**39. Which symbol redirects standard output to a file and overwrites its existing content?**

A. `>>`  
B. `>`  
C. `<`  
D. `|`  

**Answer: B. `>`**

**40. Which symbol appends output to an existing file?**

A. `>`  
B. `>>`  
C. `<`  
D. `&&`  

**Answer: B. `>>`**

**41. What is the purpose of the pipe (`|`) operator?**

A. Deletes files  
B. Passes the output of one command as input to another  
C. Changes directory  
D. Creates a new file  

**Answer: B. Passes the output of one command as input to another**

**42. What will the following command do?**

```bash
ls -l | grep ".txt"
```

A. Deletes text files  
B. Displays matching lines containing `.txt` in the listing output  
C. Creates a text file  
D. Counts all files  

**Answer: B. Displays matching lines containing `.txt` in the listing output**

## Section 7: Shell Scripting Fundamentals

**43. What is a shell script?**

A. A collection of Linux commands saved in a file  
B. A database table  
C. A Linux distribution  
D. A hardware component  

**Answer: A. A collection of Linux commands saved in a file**

**44. Which symbol is commonly used at the beginning of a Bash script to specify the interpreter?**

A. `//`  
B. `#include`  
C. `#!/bin/bash`  
D. `<?php`  

**Answer: C. `#!/bin/bash`**

**45. Which command is used to display output in a shell script?**

A. `read`  
B. `echo`  
C. `input`  
D. `printline`  

**Answer: B. `echo`**

**46. Which command accepts input from the user in Bash?**

A. `echo`  
B. `read`  
C. `input`  
D. `scanf`  

**Answer: B. `read`**

**47. Which symbol is used to access the value of a shell variable?**

A. `#`  
B. `$`  
C. `@`  
D. `%`  

**Answer: B. `$`**

**48. Which of the following is the correct way to assign a value to a variable in Bash?**

A. `name = "Rahul"`  
B. `name: "Rahul"`  
C. `name="Rahul"`  
D. `$name := "Rahul"`  

**Answer: C. `name="Rahul"`**

**49. Which command makes a shell script executable?**

A. `chmod +x script.sh`  
B. `touch +x script.sh`  
C. `execute script.sh`  
D. `run script.sh`  

**Answer: A. `chmod +x script.sh`**

## Section 8: Conditional Statements and Loops

**50. Which keyword is used to implement conditional logic in Bash?**

A. `check`  
B. `if`  
C. `when`  
D. `condition`  

**Answer: B. `if`**

**51. Which keyword is used to define an alternative block when the `if` condition is false?**

A. `otherwise`  
B. `else`  
C. `then`  
D. `endif`  

**Answer: B. `else`**

**52. Which loop is generally used when the number of iterations is known?**

A. `for`  
B. `while`  
C. `until`  
D. `case`  

**Answer: A. `for`**

**53. Which loop executes as long as its condition remains true?**

A. `for`  
B. `while`  
C. `case`  
D. `select`  

**Answer: B. `while`**

**54. Does Bash provide a native `do-while` loop?**

A. Yes  
B. No  
C. Only in Ubuntu  
D. Only in Linux servers  

**Answer: B. No**

**55. Which statement terminates a loop immediately?**

A. `continue`  
B. `break`  
C. `exitloop`  
D. `skip`  

**Answer: B. `break`**

**56. Which statement skips the current iteration and moves to the next iteration?**

A. `break`  
B. `continue`  
C. `stop`  
D. `return`  

**Answer: B. `continue`**

## Section 9: Practical Output-Based Questions

**57. What will be the output of this script?**

```bash
#!/bin/bash
name="Rahul"
echo "Hello $name"
```

A. `Hello name`  
B. `Hello Rahul`  
C. `$name`  
D. Syntax error  

**Answer: B. `Hello Rahul`**

**58. What will be the output?**

```bash
#!/bin/bash
a=10
b=20
echo $((a+b))
```

A. 10  
B. 20  
C. 30  
D. 1020  

**Answer: C. 30**

**59. What will this script display?**

```bash
#!/bin/bash
for i in 1 2 3
do
    echo $i
done
```

A. 1 2 3 on separate lines  
B. 0 1 2  
C. 3 2 1  
D. Error  

**Answer: A. 1 2 3 on separate lines**

**60. What is the output of the following command?**

```bash
echo "Linux Training" | wc -w
```

A. 1  
B. 2  
C. 14  
D. 15  

**Answer: B. 2**

**61. What does the following command display?**

```bash
grep -n "Pune" emp.txt
```

A. Only the number of Pune records  
B. Matching lines with their line numbers  
C. Only the first line  
D. All records without Pune  

**Answer: B. Matching lines with their line numbers**

**62. What will this command display?**

```bash
cut -d',' -f1,2 emp.txt
```

A. First and second fields  
B. Second and third fields  
C. First and last fields  
D. Only the first field  

**Answer: A. First and second fields**

## Answer Key – Quick Revision

| Question range | Correct answers |
|---|---|
| 1–10 | B, C, A, B, A, B, B, A, B, B |
| 11–20 | A, B, C, A, B, C, A, B, B, B |
| 21–30 | A, C, B, B, A, C, B, B, A, B |
| 31–40 | B, B, C, B, B, B, B, B, B, B |
| 41–50 | B, B, A, C, B, B, B, C, A, B |
| 51–62 | B, A, B, B, B, B, B, B, C, A, B, A |

## Suggested Classroom Assessment

- Questions 1–20: Linux fundamentals and basic commands.
- Questions 21–42: Permissions, processes and text processing.
- Questions 43–56: Shell scripting concepts.
- Questions 57–62: Practical command and output understanding.

**Total: 62 MCQs.**
