# Shell Scripting Basics – Lab Manual
## Input/Output, Variables, `if`, `if-else`, `case`, `for`, `while`, `do-while` and File Read/Write

| | |
|---|---|
| **Level** | Beginner |
| **Duration** | 4–5 hours |
| **Platform** | Linux / macOS Terminal |
| **Shell** | Bash (`.sh` files) |

---

## 1. Aim

To learn the core building blocks of Bash scripting: taking input, showing output, storing values in variables, making decisions, repeating tasks, and reading and writing files.

## 2. Learning Outcomes

After completing this lab, you will be able to:

1. Create, make executable and run a Bash script.
2. Display output with `echo` and `printf`, and accept input with `read`.
3. Declare variables, use command-line arguments and perform arithmetic.
4. Write decisions with `if`, `if-else`, `if-elif-else` and nested `if`.
5. Write multi-way menus with `case`.
6. Repeat work with `for`, `while`, `until` and a do-while pattern.
7. Control loops with `break` and `continue`.
8. Read files line by line and write or append to files using redirection.
9. Combine these concepts in a small mini-project.

## 3. Lab Setup

```bash
bash --version
mkdir shell-basics-lab
cd shell-basics-lab
```

### 3.1 Running a script

Every script in this lab follows the same steps:

```bash
nano script_name.sh          # write the code (or use any editor)
chmod +x script_name.sh      # give execute permission (once)
./script_name.sh             # run it
```

Alternative (no permission change needed):

```bash
bash script_name.sh
```

### 3.2 Anatomy of a script

```bash
#!/bin/bash
# This is a comment
echo "Hello World!"
```

| Part | Purpose |
|---|---|
| `#!/bin/bash` | "Shebang": tells the system to run the file with Bash |
| `# ...` | Comment; ignored by the shell |
| `echo` | Prints text to the screen |
| `chmod +x` | Adds execute permission |
| `./file.sh` | Runs a script in the current directory |

---

## 4. Lab 1 – Output: `echo` and `printf`

### Program 1.1: Hello World

**File:** `hello.sh`

```bash
#!/bin/bash

echo "Hello World!"
echo "Welcome to Shell Scripting"
```

**Output**

```
Hello World!
Welcome to Shell Scripting
```

### Program 1.2: Formatted output with `printf`

**File:** `printf_demo.sh`

```bash
#!/bin/bash

printf "%-10s %-10s %s\n" "ID" "Name" "Salary"
printf "%-10s %-10s %d\n" 101 Rahul 50000
printf "%-10s %-10s %d\n" 102 Priya 45000
```

**Output**

```
ID         Name       Salary
101        Rahul      50000
102        Priya      45000
```

| Format | Meaning |
|---|---|
| `%s` | String |
| `%d` | Integer |
| `%f` / `%.2f` | Decimal / 2 decimal places |
| `%-10s` | Left-aligned in a 10-character column |
| `\n` | New line (`printf` does **not** add one automatically) |

### Practice 1
Print your name, course and city on three separate lines, then print them again in one `printf` row.

---

## 5. Lab 2 – Variables and Input

### Rules for variables

- No spaces around `=`: `name="Rahul"` ✔, `name = "Rahul"` ✘
- Read a value with `$name` (or `${name}`).
- Variable names are case-sensitive; use letters, digits and `_`, and do not start with a digit.
- Always quote variables in tests and commands: `"$name"`.

### Program 2.1: Variables

**File:** `variables.sh`

```bash
#!/bin/bash

name="Rahul"
age=25
city="Pune"

echo "Name : $name"
echo "Age  : $age"
echo "City : $city"
```

### Program 2.2: User input with `read`

**File:** `user_info.sh`

```bash
#!/bin/bash

echo "===== USER INFORMATION ====="

read -p "Enter your name: " name
read -p "Enter your age: " age
read -p "Enter your city: " city

echo ""
echo "User Details"
echo "----------------------"
echo "Name : $name"
echo "Age  : $age"
echo "City : $city"
```

**Sample run**

```
===== USER INFORMATION =====
Enter your name: Rahul
Enter your age: 25
Enter your city: Pune

User Details
----------------------
Name : Rahul
Age  : 25
City : Pune
```

### `read` options

| Option | Purpose | Example |
|---|---|---|
| `-p "text"` | Show a prompt | `read -p "Name: " n` |
| `-s` | Silent (hide typing; for passwords) | `read -s -p "Password: " pw` |
| `-n 1` | Read exactly one character | `read -n 1 key` |
| `-t 5` | Time out after 5 seconds | `read -t 5 -p "Quick: " x` |
| `-r` | Do not treat backslash as an escape | `read -r line` |

### Program 2.3: Command-line arguments

**File:** `args.sh`

```bash
#!/bin/bash

echo "Script name     : $0"
echo "First argument  : $1"
echo "Second argument : $2"
echo "All arguments   : $@"
echo "Argument count  : $#"
```

**Run**

```bash
./args.sh Rahul Pune
```

**Output**

```
Script name     : ./args.sh
First argument  : Rahul
Second argument : Pune
All arguments   : Rahul Pune
Argument count  : 2
```

| Variable | Meaning |
|---|---|
| `$0` | Script name |
| `$1`, `$2`, … | Positional arguments |
| `$@` | All arguments |
| `$#` | Number of arguments |
| `$?` | Exit status of the last command (`0` = success) |

### Program 2.4: Command substitution

**File:** `cmd_subst.sh`

```bash
#!/bin/bash

today=$(date +%d-%m-%Y)
user=$(whoami)
files=$(ls | wc -l)

echo "Date  : $today"
echo "User  : $user"
echo "Files in this folder: $files"
```

`$(command)` runs the command and stores its output in a variable.

### Practice 2
Write a script that asks for a book title and author and prints `"<title>" by <author>`.

---

## 6. Lab 3 – Arithmetic Operations

### Program 3.1: Calculator

**File:** `arithmetic.sh`

```bash
#!/bin/bash

read -p "Enter first number: " num1
read -p "Enter second number: " num2

sum=$((num1 + num2))
sub=$((num1 - num2))
mul=$((num1 * num2))
mod=$((num1 % num2))

echo "Addition       : $sum"
echo "Subtraction    : $sub"
echo "Multiplication : $mul"

if [ "$num2" -ne 0 ]; then
    echo "Division       : $((num1 / num2))"
    echo "Remainder      : $mod"
else
    echo "Division       : Cannot divide by zero"
fi
```

**Sample run (10 and 3)**

```
Addition       : 13
Subtraction    : 7
Multiplication : 30
Division       : 3
Remainder      : 1
```

> `$(( ))` performs **integer** arithmetic only. `10 / 3` gives `3`. For decimals use `bc` or `awk`:
> `echo "scale=2; 10/3" | bc` → `3.33`

| Operator | Meaning |
|---|---|
| `+` `-` `*` `/` | Add, subtract, multiply, divide |
| `%` | Remainder (modulus) |
| `**` | Power (`$((2**3))` = 8) |
| `((x++))` | Increment x |

### Practice 3
Accept the radius of a circle (integer) and print its diameter and circumference using `3.14` (use `bc`).

---

## 7. Lab 4 – Conditional Statements

### 7.1 Test operators

**Numbers** (inside `[ ]`)

| Operator | Meaning |
|---|---|
| `-eq` | Equal |
| `-ne` | Not equal |
| `-gt` | Greater than |
| `-lt` | Less than |
| `-ge` | Greater than or equal |
| `-le` | Less than or equal |

**Strings**

| Operator | Meaning |
|---|---|
| `=` | Equal |
| `!=` | Not equal |
| `-z "$s"` | String is empty |
| `-n "$s"` | String is not empty |

**Files**

| Operator | Meaning |
|---|---|
| `-f file` | Exists and is a regular file |
| `-d dir` | Exists and is a directory |
| `-r` / `-w` / `-x` | Readable / writable / executable |
| `-s file` | Exists and is not empty |

**Combining conditions**

| Operator | Meaning |
|---|---|
| `&&` | AND |
| `\|\|` | OR |
| `!` | NOT |

> Spaces are mandatory inside brackets: `[ "$a" -gt 5 ]` ✔, `["$a" -gt 5]` ✘.

### Program 4.1: Simple `if`

Scenario: Is an employee eligible for training?

**File:** `simple_if.sh`

```bash
#!/bin/bash

read -p "Enter experience in years: " experience

if [ "$experience" -ge 2 ]; then
    echo "Employee is eligible for training."
fi

echo "Program completed."
```

### Program 4.2: `if-else`

Scenario: Pass or fail.

**File:** `if_else.sh`

```bash
#!/bin/bash

read -p "Enter assessment marks: " marks

if [ "$marks" -ge 50 ]; then
    echo "Result: PASS"
else
    echo "Result: FAIL"
fi
```

### Program 4.3: `if-elif-else`

Scenario: Performance rating.

**File:** `if_elif.sh`

```bash
#!/bin/bash

read -p "Enter performance score: " score

if [ "$score" -ge 90 ]; then
    echo "Rating: Excellent"
elif [ "$score" -ge 75 ]; then
    echo "Rating: Good"
elif [ "$score" -ge 50 ]; then
    echo "Rating: Average"
else
    echo "Rating: Needs Improvement"
fi
```

| Input | Output |
|---|---|
| 95 | Excellent |
| 80 | Good |
| 55 | Average |
| 20 | Needs Improvement |

### Program 4.4: Nested `if`

Scenario: Login check (hard-coded credentials are for learning only).

**File:** `nested_if.sh`

```bash
#!/bin/bash

read -p "Enter username: " username
read -s -p "Enter password: " password
echo ""

if [ "$username" = "admin" ]; then
    if [ "$password" = "admin123" ]; then
        echo "Login successful."
    else
        echo "Invalid password."
    fi
else
    echo "Invalid username."
fi
```

### Program 4.5: Logical operators

**File:** `logical.sh`

```bash
#!/bin/bash

read -p "Enter age: " age
read -p "Enter marks: " marks

if [ "$age" -ge 18 ] && [ "$marks" -ge 60 ]; then
    echo "Eligible for admission"
else
    echo "Not eligible"
fi
```

### Program 4.6: Even or odd

**File:** `even_odd.sh`

```bash
#!/bin/bash

read -p "Enter a number: " n

if [ $((n % 2)) -eq 0 ]; then
    echo "$n is even"
else
    echo "$n is odd"
fi
```

### Practice 4
1. Check whether a number is positive, negative or zero.
2. Find the largest of three numbers.
3. Check whether a year is a leap year (divisible by 4 and not by 100, or divisible by 400).

---

## 8. Lab 5 – The `case` Statement

`case` is cleaner than a long `if-elif` chain when one value is compared to many fixed choices.

### Program 5.1: Day of the week

**File:** `case_day.sh`

```bash
#!/bin/bash

read -p "Enter day number (1-7): " day

case $day in
    1) echo "Monday" ;;
    2) echo "Tuesday" ;;
    3) echo "Wednesday" ;;
    4) echo "Thursday" ;;
    5) echo "Friday" ;;
    6|7) echo "Weekend" ;;
    *) echo "Invalid day" ;;
esac
```

### Program 5.2: Vowel or consonant (pattern matching)

**File:** `case_vowel.sh`

```bash
#!/bin/bash

read -n 1 -p "Enter a letter: " ch
echo ""

case $ch in
    [aAeEiIoOuU]) echo "Vowel" ;;
    [a-zA-Z])     echo "Consonant" ;;
    *)            echo "Not a letter" ;;
esac
```

### Program 5.3: Simple calculator menu

**File:** `case_calc.sh`

```bash
#!/bin/bash

read -p "Enter first number: " a
read -p "Enter second number: " b

echo "1. Add  2. Subtract  3. Multiply  4. Divide"
read -p "Choose: " ch

case $ch in
    1) echo "Result: $((a + b))" ;;
    2) echo "Result: $((a - b))" ;;
    3) echo "Result: $((a * b))" ;;
    4)
        if [ "$b" -eq 0 ]; then
            echo "Cannot divide by zero"
        else
            echo "Result: $((a / b))"
        fi
        ;;
    *) echo "Invalid choice" ;;
esac
```

### `case` syntax

```bash
case $variable in
    pattern1) commands ;;
    pattern2|pattern3) commands ;;
    *) default commands ;;
esac
```

- Each branch ends with `;;`.
- `|` separates alternatives.
- `*` is the default (like `else`).
- The statement ends with `esac` (`case` spelled backwards).

### Practice 5
Accept a month number (1–12) and print the month name; for numbers outside that range print "Invalid month".

---

## 9. Lab 6 – Loops

### 9.1 `for` loop

#### Program 6.1: Loop over a list

**File:** `for_list.sh`

```bash
#!/bin/bash

echo "Employee ID List"

for id in 101 102 103 104 105
do
    echo "Employee ID: $id"
done
```

#### Program 6.2: C-style `for` loop

**File:** `for_range.sh`

```bash
#!/bin/bash

for ((i=1; i<=10; i++))
do
    echo "$i"
done
```

#### Program 6.3: Brace expansion and step

**File:** `for_brace.sh`

```bash
#!/bin/bash

for i in {1..5}
do
    echo "Number $i"
done

echo "Even numbers up to 10:"
for i in {2..10..2}
do
    echo "$i"
done
```

#### Program 6.4: Multiplication table

**File:** `table.sh`

```bash
#!/bin/bash

read -p "Enter a number: " num

for ((i=1; i<=10; i++))
do
    echo "$num x $i = $((num * i))"
done
```

#### Program 6.5: Loop over files

**File:** `for_files.sh`

```bash
#!/bin/bash

for f in *.sh
do
    echo "Script found: $f"
done
```

### 9.2 `while` loop

#### Program 6.6: Countdown

**File:** `while_count.sh`

```bash
#!/bin/bash

count=5

while [ "$count" -gt 0 ]
do
    echo "Countdown: $count"
    count=$((count - 1))
done

echo "Completed!"
```

#### Program 6.7: Sum of 1 to N

**File:** `sum_n.sh`

```bash
#!/bin/bash

read -p "Enter N: " n
sum=0
i=1

while [ "$i" -le "$n" ]
do
    sum=$((sum + i))
    i=$((i + 1))
done

echo "Sum of 1 to $n = $sum"
```

For N = 10 the output is `Sum of 1 to 10 = 55`.

#### Program 6.8: Input validation loop

**File:** `while_validate.sh`

```bash
#!/bin/bash

read -p "Enter a number between 1 and 100: " number

while [ "$number" -lt 1 ] || [ "$number" -gt 100 ]
do
    echo "Invalid number. Try again."
    read -p "Enter a number between 1 and 100: " number
done

echo "Valid number entered: $number"
```

### 9.3 `until` loop

`until` repeats **until** the condition becomes true (the opposite of `while`).

**File:** `until_loop.sh`

```bash
#!/bin/bash

count=1

until [ "$count" -gt 5 ]
do
    echo "Count: $count"
    count=$((count + 1))
done
```

### 9.4 Do-while pattern

Bash has no native `do-while`. To run the body **at least once** and test the condition afterwards, use `while true` with `break`.

#### Program 6.9: Repeat until the user enters 0

**File:** `do_while.sh`

```bash
#!/bin/bash

while true
do
    read -p "Enter a number (0 to exit): " number

    if [ "$number" -eq 0 ]; then
        echo "Exiting program..."
        break
    fi

    echo "You entered: $number"
done

echo "Program terminated."
```

#### Program 6.10: Menu that always runs at least once

**File:** `do_while_menu.sh`

```bash
#!/bin/bash

while true
do
    echo ""
    echo "1. Say Hello"
    echo "2. Show Date"
    echo "3. Exit"
    read -p "Enter choice: " ch

    case $ch in
        1) echo "Hello!" ;;
        2) date ;;
        3) echo "Goodbye!"; break ;;
        *) echo "Invalid choice" ;;
    esac
done
```

### 9.5 `break` and `continue`

**File:** `break_continue.sh`

```bash
#!/bin/bash

for i in {1..10}
do
    if [ "$i" -eq 3 ]; then
        continue          # skip 3
    fi
    if [ "$i" -eq 7 ]; then
        break             # stop the loop at 7
    fi
    echo "$i"
done
```

**Output:** `1 2 4 5 6` (each on its own line).

### Loop comparison

| Loop | Behaviour | Use when |
|---|---|---|
| `for` | Iterates over a list or range | The number of repeats is known |
| `while` | Repeats while the condition is true | Repeat count is unknown |
| `until` | Repeats until the condition is true | Waiting for a condition to become true |
| Do-while pattern | Runs at least once, then tests | Menus, retry prompts |

### Practice 6
1. Print numbers 1 to 50.
2. Print the factorial of a number.
3. Print the sum of the digits of a number.
4. Print the Fibonacci series up to N terms.
5. Keep asking for a password until the user types `secret` (maximum 3 attempts).

---

## 10. Lab 7 – File Read and Write

### 10.1 Redirection operators

| Operator | Purpose | Example |
|---|---|---|
| `>` | Write output to a file (**overwrites**) | `echo "Hi" > a.txt` |
| `>>` | Append output to a file | `echo "More" >> a.txt` |
| `<` | Take input from a file | `wc -l < a.txt` |
| `2>` | Redirect error messages | `ls nofile 2> err.txt` |
| `2>&1` | Send errors to the same place as output | `cmd > all.txt 2>&1` |
| `\|` | Pipe: output of one command is input of the next | `cat a.txt \| sort` |

### Program 7.1: Write and append

**File:** `write_file.sh`

```bash
#!/bin/bash

FILE="notes.txt"

echo "Line 1: created by script" > "$FILE"
echo "Line 2: appended line" >> "$FILE"
date >> "$FILE"

echo "File written. Contents:"
cat "$FILE"
```

### Program 7.2: Write user input to a file

**File:** `save_employee.sh`

```bash
#!/bin/bash

FILE="emp.txt"

read -p "Enter employee ID: " id
read -p "Enter name: " name
read -p "Enter department: " dept
read -p "Enter salary: " salary

echo "$id,$name,$dept,$salary" >> "$FILE"

echo "Record saved."
```

Run it a few times, then check the file with `cat emp.txt`.

### Program 7.3: Create sample data

```bash
cat > emp.txt << 'EOF'
101,Rahul,IT,50000
102,Priya,HR,45000
103,Amit,Finance,60000
104,Neha,IT,55000
105,Rohan,HR,40000
EOF
```

The block between `<< 'EOF'` and `EOF` is a **here-document**; its text is written to the file.

### Program 7.4: Read a file line by line

**File:** `read_file.sh`

```bash
#!/bin/bash

FILE="emp.txt"

if [ -f "$FILE" ]; then
    echo "Employee Records"
    echo "----------------"

    while IFS= read -r line
    do
        echo "$line"
    done < "$FILE"
else
    echo "File does not exist."
fi
```

| Piece | Meaning |
|---|---|
| `-f "$FILE"` | True if the file exists |
| `IFS=` | Keeps leading/trailing spaces in each line |
| `read -r` | Backslashes are not interpreted |
| `done < "$FILE"` | Feeds the file into the loop |

### Program 7.5: Split each line into fields

**File:** `read_fields.sh`

```bash
#!/bin/bash

FILE="emp.txt"

printf "%-6s %-10s %-10s %s\n" ID Name Dept Salary

while IFS=',' read -r id name dept salary
do
    printf "%-6s %-10s %-10s %s\n" "$id" "$name" "$dept" "$salary"
done < "$FILE"
```

`IFS=','` tells `read` to split each line on commas and fill `id`, `name`, `dept` and `salary`.

### Program 7.6: Count lines, words and characters

**File:** `file_stats.sh`

```bash
#!/bin/bash

FILE="emp.txt"

echo "Lines : $(wc -l < "$FILE")"
echo "Words : $(wc -w < "$FILE")"
echo "Chars : $(wc -c < "$FILE")"
```

### Program 7.7: Total salary using a loop

**File:** `total_salary.sh`

```bash
#!/bin/bash

FILE="emp.txt"
total=0

while IFS=',' read -r id name dept salary
do
    total=$((total + salary))
done < "$FILE"

echo "Total salary: $total"
```

For the five records in Program 7.3 the total is `250000`.

### Program 7.8: Copy a file with a check

**File:** `backup.sh`

```bash
#!/bin/bash

SRC="emp.txt"
DEST="emp_backup.txt"

if [ ! -f "$SRC" ]; then
    echo "Source file not found."
    exit 1
fi

cp "$SRC" "$DEST"
echo "Backup created: $DEST"
```

### Program 7.9: Search a record

**File:** `search_file.sh`

```bash
#!/bin/bash

read -p "Enter name to search: " name

if grep -i "$name" emp.txt; then
    echo "Record found."
else
    echo "Record not found."
fi
```

`grep` returns exit status `0` when it finds a match, so it can be used directly as an `if` condition.

### Program 7.10: Show errors separately

```bash
ls missing_file > out.txt 2> err.txt
cat err.txt
```

### Practice 7
1. Read a file name from the user and display the file only if it exists.
2. Append a new employee record and display the updated file.
3. Display each line of a file with its line number (use a counter variable).
4. Write the multiplication table of a number into `table.txt`.

---

## 11. Lab 8 – Functions (bonus)

**File:** `functions.sh`

```bash
#!/bin/bash

greet() {
    echo "Hello, $1!"
}

add() {
    echo $(( $1 + $2 ))
}

greet "Rahul"
result=$(add 10 20)
echo "10 + 20 = $result"
```

Arguments inside a function are `$1`, `$2`, … just like for scripts. A function must be defined **before** it is called.

---

## 12. Mini-Project: Simple Employee Manager

Combines input/output, `case`, `while` (do-while pattern), `for`, `if`, and file read/write.

**File:** `employee_manager.sh`

```bash
#!/bin/bash

FILE="employees.txt"

# Create the file if it does not exist
[ -f "$FILE" ] || touch "$FILE"

add_employee() {
    read -p "Enter ID: " id
    read -p "Enter name: " name
    read -p "Enter department: " dept
    read -p "Enter salary: " salary

    if [ -z "$id" ] || [ -z "$name" ]; then
        echo "ID and name cannot be empty."
        return
    fi

    echo "$id,$name,$dept,$salary" >> "$FILE"
    echo "Employee added."
}

view_employees() {
    if [ ! -s "$FILE" ]; then
        echo "No records found."
        return
    fi

    printf "%-6s %-10s %-10s %s\n" ID Name Dept Salary
    while IFS=',' read -r id name dept salary
    do
        printf "%-6s %-10s %-10s %s\n" "$id" "$name" "$dept" "$salary"
    done < "$FILE"
}

search_employee() {
    read -p "Enter name to search: " key
    if [ -z "$key" ]; then
        echo "Search text cannot be empty."
        return
    fi
    grep -i "$key" "$FILE" || echo "No match found."
}

total_salary() {
    total=0
    while IFS=',' read -r id name dept salary
    do
        total=$((total + salary))
    done < "$FILE"
    echo "Total salary: $total"
}

while true
do
    echo ""
    echo "================================"
    echo "       EMPLOYEE MANAGER"
    echo "================================"
    echo "1. Add Employee"
    echo "2. View All Employees"
    echo "3. Search Employee"
    echo "4. Total Salary"
    echo "5. Exit"
    echo "================================"
    read -p "Enter your choice: " choice

    case $choice in
        1) add_employee ;;
        2) view_employees ;;
        3) search_employee ;;
        4) total_salary ;;
        5) echo "Thank you!"; break ;;
        *) echo "Invalid choice. Try again." ;;
    esac
done
```

### Suggested test cases

| Step | Input | Expected result |
|---|---|---|
| 1 | Choice `2` on a new file | `No records found.` |
| 2 | Choice `1`, add `101,Rahul,IT,50000` | `Employee added.` |
| 3 | Choice `2` | Table with one row |
| 4 | Choice `3`, text `rahul` | Matching record shown |
| 5 | Choice `3`, text `zzz` | `No match found.` |
| 6 | Choice `4` | `Total salary: 50000` |
| 7 | Choice `9` | `Invalid choice. Try again.` |
| 8 | Choice `5` | Program exits |

---

## 13. Common Errors and Fixes

| Error / symptom | Cause | Fix |
|---|---|---|
| `Permission denied` | Script not executable | `chmod +x script.sh` |
| `command not found` on `name = "x"` | Spaces around `=` | `name="x"` |
| `[: missing ]` / `[: too many arguments` | Missing spaces in `[ ]`, or unquoted variable with spaces | `[ "$a" -eq 1 ]` |
| `integer expression expected` | Comparing text with `-eq`, or empty variable | Validate input; quote variables |
| `unexpected end of file` | Missing `fi`, `done` or `esac` | Close every `if`, loop and `case` |
| `syntax error near unexpected token 'then'` | `then` not on the same line as `if` without `;` | `if [ ... ]; then` |
| `^M: bad interpreter` | Windows (CRLF) line endings | `sed -i 's/\r$//' script.sh` (macOS: `sed -i '' ...`) |
| `read: Illegal option -p` | Run with `sh` instead of Bash | Use `bash script.sh` |
| Script works in the lab folder only | Relative file paths | Run from the same folder or use absolute paths |

**Debugging tip:** run `bash -x script.sh` to see every command as it executes, or add `set -x` at the top of the script.

---

## 14. Quick Reference

```bash
# Output / Input
echo "text"            printf "%s\n" "text"
read -p "Prompt: " var

# Variables
x=10                   echo "$x"            y=$((x + 5))
now=$(date)            $1 $2 $# $@ $?

# Conditions
if [ cond ]; then ... elif [ cond ]; then ... else ... fi
case $v in a) ... ;; b|c) ... ;; *) ... ;; esac

# Loops
for i in 1 2 3; do ... done
for ((i=0; i<5; i++)); do ... done
while [ cond ]; do ... done
until [ cond ]; do ... done
while true; do ... break ... done        # do-while pattern

# Files
cmd > file      cmd >> file      cmd < file
while IFS= read -r line; do ... done < file
[ -f file ]   [ -d dir ]   [ -r file ]   [ -s file ]
```

---

## 15. Practice Assignments

| No. | Problem | Concepts |
|:---:|---|---|
| 1 | Accept name, ID and department and display them. | Input/output |
| 2 | Accept two numbers and show all arithmetic results. | Variables, arithmetic |
| 3 | Check whether a number is positive, negative or zero. | `if-else` |
| 4 | Check bonus eligibility: salary below 50000 gets 10%, otherwise 5%. | `if-else`, arithmetic |
| 5 | Print a grade (A/B/C/D/F) from marks. | `if-elif` |
| 6 | Print the day name from a number 1–7. | `case` |
| 7 | Build a menu to show date, current user and current folder. | `case`, loop |
| 8 | Print numbers 1 to 50. | `for` |
| 9 | Print the multiplication table of any number. | `for` |
| 10 | Sum the numbers from 1 to N. | `while` |
| 11 | Keep reading numbers until the user enters 0, then print their sum. | Do-while pattern |
| 12 | Read a file and display all records with line numbers. | File read |
| 13 | Append student records to a file and display them. | File write/read |
| 14 | Count the lines in a file and show the highest salary. | File read, comparison |
| 15 | Build a menu-driven application to add, view and search records. | Everything |

---

## 16. Viva Questions

1. What is the purpose of `#!/bin/bash`?
2. Why can there be no spaces around `=` in `name=value`?
3. What is the difference between `>` and `>>`?
4. What is the difference between `$1` and `$#`?
5. What does `$?` contain?
6. Why do we quote variables like `"$name"`?
7. When would you use `case` rather than `if-elif`?
8. Bash has no `do-while`. How can the same behaviour be achieved?
9. What is the difference between `break` and `continue`?
10. What does `while IFS=',' read -r a b c; do ...; done < file` do?
11. What does `$(...)` do?
12. How do you make a script executable?

---

## 17. Suggested Folder Structure

```
shell-basics-lab/
├── 01-output/          hello.sh  printf_demo.sh
├── 02-input/           variables.sh  user_info.sh  args.sh  cmd_subst.sh
├── 03-arithmetic/      arithmetic.sh
├── 04-conditions/      simple_if.sh  if_else.sh  if_elif.sh  nested_if.sh  logical.sh  even_odd.sh
├── 05-case/            case_day.sh  case_vowel.sh  case_calc.sh
├── 06-loops/           for_list.sh  for_range.sh  table.sh  while_count.sh  sum_n.sh
│                       while_validate.sh  until_loop.sh  do_while.sh  break_continue.sh
├── 07-files/           write_file.sh  read_file.sh  read_fields.sh  total_salary.sh  backup.sh
└── 08-mini-project/    employee_manager.sh
```

*End of lab manual.*
