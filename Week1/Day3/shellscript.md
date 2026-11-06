# Shell Scripting Lab Manual
## Searching and Extracting Employee Data with `grep` and `cut`

| | |
|---|---|
| **Level** | Beginner to Intermediate |
| **Duration** | 3–4 hours |
| **Platform** | Linux / macOS Terminal |
| **Shell** | Bash (`.sh` files) |
| **Dataset** | `emp.txt` (CSV employee records) |

---

## 1. Aim

To learn how to search, filter, extract and summarise employee records stored in a text file, using the Linux `grep` and `cut` commands inside Bash shell scripts.

## 2. Learning Outcomes

After completing this lab, you will be able to:

1. Create and run a Bash script (`#!/bin/bash`, `chmod +x`, `./script.sh`).
2. Search records with `grep` (plain, case-insensitive, inverted, counted, numbered, multi-pattern).
3. Anchor patterns to a specific field so that matches are accurate.
4. Extract one or more columns with `cut`.
5. Combine `grep` and `cut` with a pipe (`|`).
6. Use command substitution `$(...)` and file redirection (`>`, `>>`).
7. Build a menu-driven report utility using `while`, `case` and `for`.
8. Validate input and handle missing files.

## 3. Prerequisites

- Basic Linux navigation (`pwd`, `ls`, `cd`, `mkdir`).
- Ability to use a text editor (`nano`, `vim`, VS Code).
- Familiarity with variables and `echo`/`read` (see the shell scripting tutorial, sections 1–4).

Check that Bash is available:

```bash
bash --version
```

---

## 4. Lab Setup

### 4.1 Create the working directory

```bash
mkdir grep-cut-lab
cd grep-cut-lab
```

### 4.2 Create the dataset `emp.txt`

Copy and paste the whole block below into the terminal. It creates the file with no editor needed.

```bash
cat > emp.txt << 'EOF'
101,Rahul,IT,50000,Pune
102,Priya,HR,45000,Mumbai
103,Amit,Finance,60000,Delhi
104,Neha,IT,55000,Pune
105,Rohan,HR,40000,Bangalore
106,Anita,IT,65000,Mumbai
107,Vikas,Finance,70000,Pune
108,Kiran,Sales,35000,Delhi
109,Meena,Sales,42000,Mumbai
110,Arjun,IT,75000,Bangalore
EOF
```

Verify:

```bash
cat emp.txt
wc -l emp.txt        # expected: 10
```

### 4.3 Dataset structure

| Field # | Column | Description | Example |
|:---:|---|---|---|
| 1 | Employee ID | Unique ID | `101` |
| 2 | Name | Employee name | `Rahul` |
| 3 | Department | IT, HR, Finance, Sales | `IT` |
| 4 | Salary | Monthly salary | `50000` |
| 5 | City | Work location | `Pune` |

All records are comma-separated, with **no header row** and **no spaces** around commas.

### 4.4 Running any script in this lab

```bash
chmod +x script_name.sh
./script_name.sh
# or, without changing permissions:
bash script_name.sh
```

> **Important:** every script reads `emp.txt` using a relative path, so run scripts from inside `grep-cut-lab/`.

---

## 5. Concept Primer

### 5.1 `grep` – search for lines

```
grep [options] "pattern" filename
```

| Option | Purpose |
|---|---|
| `-i` | Ignore case |
| `-v` | Invert: show lines that do **not** match |
| `-c` | Count matching lines |
| `-n` | Show line numbers |
| `-E` | Extended regular expressions (`\|` alternation, `()` groups) |
| `-w` | Match whole words only |

### 5.2 `cut` – extract columns

```
cut -d'<delimiter>' -f<fields> filename
```

| Form | Meaning |
|---|---|
| `-f2` | Field 2 only |
| `-f2,4` | Fields 2 and 4 |
| `-f1-3` | Fields 1 through 3 |
| `-f3-` | Field 3 to the end |

### 5.3 Field-anchored patterns (avoiding false matches)

Searching for `IT` alone is risky: it would also match any record where `IT` appears in another field (a name such as *Kitty*, for instance). Surround the value with the delimiter so only the intended field matches.

| Pattern | Matches |
|---|---|
| `",IT,"` | Department field exactly (field 3, a middle field) |
| `",Pune$"` | City field exactly (last field; `$` = end of line) |
| `"^101,"` | Employee ID exactly (first field; `^` = start of line) |
| `",60000,"` | Salary field exactly (field 4, a middle field) |

### 5.4 Pipe `|`

A pipe sends the output of one command into the next:

```
emp.txt ──► grep ",IT," ──► cut -d',' -f2 ──► Rahul, Neha, Anita, Arjun
            (filter rows)    (pick a column)
```

Rule of thumb: **`grep` selects rows, `cut` selects columns.**

---

## 6. Part A – `grep` Exercises

### Exercise 1: Display all IT employees

**Objective:** Search records by department.

**File:** `grep_it.sh`

```bash
#!/bin/bash

echo "Employees belonging to IT department"

grep ",IT," emp.txt
```

**Expected output**

```
Employees belonging to IT department
101,Rahul,IT,50000,Pune
104,Neha,IT,55000,Pune
106,Anita,IT,65000,Mumbai
110,Arjun,IT,75000,Bangalore
```

---

### Exercise 2: Search an employee by name

**Objective:** Find a particular record using user input.

**File:** `grep_name.sh`

```bash
#!/bin/bash

read -p "Enter employee name: " name

grep "$name" emp.txt
```

**Sample run**

```
Enter employee name: Rahul
101,Rahul,IT,50000,Pune
```

**Observe:** Typing `rahul` (lower case) returns nothing. `grep` is case-sensitive by default.

---

### Exercise 3: Case-insensitive search

**Objective:** Ignore upper/lower case with `-i`.

**File:** `grep_case.sh`

```bash
#!/bin/bash

read -p "Enter employee name: " name

grep -i "$name" emp.txt
```

**Sample run**

```
Enter employee name: rahul
101,Rahul,IT,50000,Pune
```

---

### Exercise 4: Employees outside IT

**Objective:** Invert the match with `-v`.

**File:** `grep_not_it.sh`

```bash
#!/bin/bash

echo "Employees outside IT department"

grep -v ",IT," emp.txt
```

**Expected output**

```
Employees outside IT department
102,Priya,HR,45000,Mumbai
103,Amit,Finance,60000,Delhi
105,Rohan,HR,40000,Bangalore
107,Vikas,Finance,70000,Pune
108,Kiran,Sales,35000,Delhi
109,Meena,Sales,42000,Mumbai
```

---

### Exercise 5: Count employees in IT

**Objective:** Count matches with `-c` and capture the result with command substitution.

**File:** `grep_count.sh`

```bash
#!/bin/bash

count=$(grep -c ",IT," emp.txt)

echo "Total IT employees: $count"
```

**Expected output**

```
Total IT employees: 4
```

---

### Exercise 6: IT or HR employees

**Objective:** Match several values with `-E`.

**File:** `grep_multiple.sh`

```bash
#!/bin/bash

echo "Employees belonging to IT or HR"

grep -E ",(IT|HR)," emp.txt
```

**Expected output**

```
Employees belonging to IT or HR
101,Rahul,IT,50000,Pune
102,Priya,HR,45000,Mumbai
104,Neha,IT,55000,Pune
105,Rohan,HR,40000,Bangalore
106,Anita,IT,65000,Mumbai
110,Arjun,IT,75000,Bangalore
```

---

### Exercise 7: Show line numbers

**Objective:** Locate matching records with `-n`.

**File:** `grep_line.sh`

```bash
#!/bin/bash

echo "IT employee records with line numbers"

grep -n ",IT," emp.txt
```

**Expected output**

```
IT employee records with line numbers
1:101,Rahul,IT,50000,Pune
4:104,Neha,IT,55000,Pune
6:106,Anita,IT,65000,Mumbai
10:110,Arjun,IT,75000,Bangalore
```

---

### `grep` quick reference

| Command | Objective |
|---|---|
| `grep "Rahul" emp.txt` | Search by name |
| `grep -i "rahul" emp.txt` | Case-insensitive search |
| `grep -v ",IT," emp.txt` | Exclude IT employees |
| `grep -c ",IT," emp.txt` | Count IT employees |
| `grep -n ",IT," emp.txt` | Show line numbers |
| `grep -E ",(IT\|HR)," emp.txt` | IT **or** HR (in the shell, type `(IT|HR)`; the backslash is only a Markdown table escape) |
| `grep -w "Rahul" emp.txt` | Whole-word match |

---

## 7. Part B – `cut` Exercises

### Exercise 8: Employee IDs

**File:** `cut_id.sh`

```bash
#!/bin/bash

echo "Employee IDs"

cut -d',' -f1 emp.txt
```

**Expected output**

```
Employee IDs
101
102
103
104
105
106
107
108
109
110
```

---

### Exercise 9: Employee names

**File:** `cut_name.sh`

```bash
#!/bin/bash

echo "Employee Names"

cut -d',' -f2 emp.txt
```

**Expected output**

```
Employee Names
Rahul
Priya
Amit
Neha
Rohan
Anita
Vikas
Kiran
Meena
Arjun
```

---

### Exercise 10: Names and departments (two columns)

**File:** `cut_name_dept.sh`

```bash
#!/bin/bash

echo "Employee Name and Department"

cut -d',' -f2,3 emp.txt
```

**Expected output (first 3 lines)**

```
Employee Name and Department
Rahul,IT
Priya,HR
...
```

---

### Exercise 11: ID, name and salary (non-adjacent columns)

**File:** `cut_salary.sh`

```bash
#!/bin/bash

echo "Employee ID, Name and Salary"

cut -d',' -f1,2,4 emp.txt
```

**Expected output (first 3 lines)**

```
Employee ID, Name and Salary
101,Rahul,50000
102,Priya,45000
...
```

---

### Exercise 12: A range of columns

**File:** `cut_range.sh`

```bash
#!/bin/bash

echo "Employee ID, Name and Department"

cut -d',' -f1-3 emp.txt
```

**Expected output (first 3 lines)**

```
Employee ID, Name and Department
101,Rahul,IT
102,Priya,HR
...
```

---

### `cut` quick reference

| Command | Objective |
|---|---|
| `cut -d',' -f1 emp.txt` | Employee ID |
| `cut -d',' -f2 emp.txt` | Name |
| `cut -d',' -f3 emp.txt` | Department |
| `cut -d',' -f4 emp.txt` | Salary |
| `cut -d',' -f5 emp.txt` | City |
| `cut -d',' -f2,3 emp.txt` | Name and department |
| `cut -d',' -f1,2,4 emp.txt` | ID, name and salary |
| `cut -d',' -f1-3 emp.txt` | First three columns |

---

## 8. Part C – Combining `grep` and `cut`

### Exercise 13: Names of IT employees

**File:** `grep_cut_it.sh`

```bash
#!/bin/bash

echo "IT Department Employee Names"

grep ",IT," emp.txt | cut -d',' -f2
```

**Expected output**

```
IT Department Employee Names
Rahul
Neha
Anita
Arjun
```

---

### Exercise 14: Names and salaries of IT employees

**File:** `grep_cut_salary.sh`

```bash
#!/bin/bash

echo "IT Employee Salary Details"

grep ",IT," emp.txt | cut -d',' -f2,4
```

**Expected output**

```
IT Employee Salary Details
Rahul,50000
Neha,55000
Anita,65000
Arjun,75000
```

---

### Exercise 15: Employees working in Pune

**Objective:** Filter on the **last** field using the `$` end-of-line anchor.

**File:** `grep_cut_city.sh`

```bash
#!/bin/bash

echo "Employees working in Pune"

grep ",Pune$" emp.txt | cut -d',' -f2,3
```

**Expected output**

```
Employees working in Pune
Rahul,IT
Neha,IT
Vikas,Finance
```

---

### Exercise 16: HR employee names

**File:** `grep_cut_hr.sh`

```bash
#!/bin/bash

echo "HR Department Employees"

grep ",HR," emp.txt | cut -d',' -f2
```

**Expected output**

```
HR Department Employees
Priya
Rohan
```

---

### Exercise 17: Employees earning exactly 60000

**Objective:** Match a numeric field exactly by surrounding it with delimiters (so `160000` or `600000` would not match).

**File:** `grep_salary.sh`

```bash
#!/bin/bash

echo "Employees earning 60000"

grep ',60000,' emp.txt
```

**Expected output**

```
Employees earning 60000
103,Amit,Finance,60000,Delhi
```

---

## 9. Part D – Mini-Project: Employee Search and Report Generator

**Business scenario:** The HR operations team needs a simple utility that searches employee information and produces department-wise reports.

**Concepts used:** `while`, `case`, `for`, `grep`, `cut`, command substitution, redirection, file checks.

**File:** `employee_report.sh`

```bash
#!/bin/bash

FILE="emp.txt"

if [ ! -f "$FILE" ]; then
    echo "Employee file not found!"
    exit 1
fi

while true
do

    echo ""
    echo "=================================="
    echo "       EMPLOYEE REPORT SYSTEM"
    echo "=================================="
    echo "1. Display All Employees"
    echo "2. Search Employee by Name"
    echo "3. Display IT Employees"
    echo "4. Display HR Employees"
    echo "5. Display Employees by City"
    echo "6. Count Employees by Department"
    echo "7. Generate Department Report"
    echo "8. Exit"
    echo "=================================="

    read -p "Enter your choice: " choice

    case $choice in

        1)
            cat "$FILE"
            ;;

        2)
            read -p "Enter employee name: " name
            grep -i "$name" "$FILE"
            ;;

        3)
            grep ",IT," "$FILE"
            ;;

        4)
            grep ",HR," "$FILE"
            ;;

        5)
            read -p "Enter city: " city
            grep ",$city$" "$FILE"
            ;;

        6)
            for dept in IT HR Finance Sales
            do
                count=$(grep -c ",$dept," "$FILE")
                echo "$dept : $count"
            done
            ;;

        7)
            REPORT="department_report.txt"

            echo "EMPLOYEE DEPARTMENT REPORT" > "$REPORT"
            echo "==========================" >> "$REPORT"

            for dept in IT HR Finance Sales
            do
                echo "" >> "$REPORT"
                echo "$dept DEPARTMENT" >> "$REPORT"

                grep ",$dept," "$FILE" |
                cut -d',' -f1,2,4 >> "$REPORT"
            done

            echo "Report generated: $REPORT"
            ;;

        8)
            echo "Exiting application..."
            break
            ;;

        *)
            echo "Invalid choice!"
            ;;

    esac

done
```

### Run it

```bash
chmod +x employee_report.sh
./employee_report.sh
```

### Sample session

```
==================================
       EMPLOYEE REPORT SYSTEM
==================================
...
Enter your choice: 6
IT : 4
HR : 2
Finance : 2
Sales : 2
```

### Expected `department_report.txt` after option 7

```
EMPLOYEE DEPARTMENT REPORT
==========================

IT DEPARTMENT
101,Rahul,50000
104,Neha,55000
106,Anita,65000
110,Arjun,75000

HR DEPARTMENT
102,Priya,45000
105,Rohan,40000

Finance DEPARTMENT
103,Amit,60000
107,Vikas,70000

Sales DEPARTMENT
108,Kiran,35000
109,Meena,42000
```

### Known limitation (discussion point)

Menu option 2 with an **empty** name makes `grep -i ""` match every line. Menu option 5 with an unknown city prints nothing and gives no message. See Section 11 (Input validation) for improvements.

---

## 10. Common Errors and Troubleshooting

| Symptom | Likely cause | Fix |
|---|---|---|
| `Permission denied` | Script is not executable | `chmod +x script.sh` or run `bash script.sh` |
| `grep: emp.txt: No such file or directory` | Wrong directory | `cd` into `grep-cut-lab`; check with `ls` |
| `bad interpreter` / `^M` in output | File saved with Windows (CRLF) line endings | `sed -i 's/\r$//' script.sh` (on macOS: `sed -i '' 's/\r$//' script.sh`) |
| City search returns nothing | `emp.txt` has trailing spaces or CRLF, so `$` anchor fails | Re-create `emp.txt`; check with `cat -A emp.txt` |
| `read: Illegal option -p` | Running with `sh` (not Bash) | Use `bash script.sh` or `./script.sh` |
| `grep -E ",(IT\|HR),"` matches nothing | Backslash typed before `\|` | In the shell, write `(IT|HR)` with a plain pipe |
| Output shows the whole file | Empty search string passed to `grep` | Validate the input first (Section 11) |
| `cut` prints the whole line | Wrong delimiter or no delimiter in the line | Use `-d','` and check the data |

---

## 11. Extension: Input Validation (before using scripts in production)

Always check that the input file exists and is readable, and that user input is not empty.

```bash
#!/bin/bash

FILE="emp.txt"

if [ ! -f "$FILE" ] || [ ! -r "$FILE" ]; then
    echo "Error: $FILE not found or not readable."
    exit 1
fi

read -p "Enter employee name: " name

if [ -z "$name" ]; then
    echo "Error: name cannot be empty."
    exit 1
fi

result=$(grep -i "$name" "$FILE")

if [ -z "$result" ]; then
    echo "No employee found matching '$name'."
else
    echo "$result"
fi
```

Check that a salary threshold is numeric:

```bash
read -p "Enter minimum salary: " min

case "$min" in
    ''|*[!0-9]*) echo "Please enter digits only."; exit 1 ;;
esac
```

Useful `grep` exit status: `0` = match found, `1` = no match, `2` = error. You can test it directly:

```bash
if grep -q ",IT," emp.txt; then
    echo "IT department has employees"
fi
```

---

## 12. Practice Assignments

Use the same `emp.txt`. Write one script per task and save it with a meaningful name.

| No. | Problem statement | Concept |
|:---:|---|---|
| 1 | Find all employees working in Finance. | `grep` |
| 2 | Search an employee by name without case sensitivity. | `grep -i` |
| 3 | Find all employees who are not from HR. | `grep -v` |
| 4 | Count employees working in Bangalore. | `grep -c` |
| 5 | Display records of employees belonging to IT and Finance. | `grep -E` |
| 6 | Display employee names only. | `cut` |
| 7 | Display employee IDs and cities. | `cut` |
| 8 | Extract employee name, department and salary. | `cut` |
| 9 | Find employees working in Mumbai and display their names. | `grep` + `cut` |
| 10 | Display the names and salaries of Finance employees. | `grep` + `cut` |
| 11 | Find employees whose salary is exactly 75000. | `grep` |
| 12 | Generate a report containing the employee count for each department. | `grep` + loops |
| 13 | Generate a city-wise employee report. | `grep` + `cut` |
| 14 | Generate separate text files for IT, HR and Finance employees. | `grep` + redirection |
| 15 | Develop a menu-driven employee search utility. | `grep` + `cut` + `case` |

### Challenge tasks (optional)

1. Ask the user for a department and display that department's employee names, with a friendly message if none are found.
2. Ask for a city and display the count of employees there.
3. Display the names of employees whose names start with the letter `A` (hint: `grep "^[0-9]*,A"`).
4. Display all employees whose salary has exactly five digits and starts with `4` (hint: `grep -E ",4[0-9]{4},"`).

---

## 13. Viva / Self-Assessment Questions

1. What is the difference between `grep "IT" emp.txt` and `grep ",IT," emp.txt`?
2. What does `-v` do? Give an example using `emp.txt`.
3. What do `^` and `$` mean in a `grep` pattern?
4. What is the purpose of `-d` and `-f` in `cut`?
5. What is the difference between `-f2,4` and `-f2-4`?
6. Why can `cut` not select rows, and `grep` not select columns?
7. What does `$(...)` do?
8. How is `>` different from `>>`?
9. Write a single command to print the names of employees in Delhi.
10. Why does the mini-project check `[ ! -f "$FILE" ]` before the loop?

---

## 14. Instructor Solutions (Assignments 1–14)

<details>
<summary>Click to expand</summary>

```bash
# 1  Finance employees
grep ",Finance," emp.txt

# 2  Case-insensitive name search
read -p "Name: " n; grep -i "$n" emp.txt

# 3  Not from HR
grep -v ",HR," emp.txt

# 4  Count in Bangalore
grep -c ",Bangalore$" emp.txt          # expected: 2

# 5  IT and Finance
grep -E ",(IT|Finance)," emp.txt

# 6  Names only
cut -d',' -f2 emp.txt

# 7  IDs and cities
cut -d',' -f1,5 emp.txt

# 8  Name, department, salary
cut -d',' -f2-4 emp.txt

# 9  Names of Mumbai employees
grep ",Mumbai$" emp.txt | cut -d',' -f2          # Priya, Anita, Meena

# 10 Names and salaries of Finance employees
grep ",Finance," emp.txt | cut -d',' -f2,4       # Amit,60000  Vikas,70000

# 11 Salary exactly 75000
grep ",75000," emp.txt                           # 110,Arjun,IT,75000,Bangalore

# 12 Department-wise counts
for d in IT HR Finance Sales
do
    echo "$d : $(grep -c ",$d," emp.txt)"
done

# 13 City-wise report
for c in Pune Mumbai Delhi Bangalore
do
    echo "== $c =="
    grep ",$c$" emp.txt | cut -d',' -f1,2,3
done > city_report.txt

# 14 Separate files per department
for d in IT HR Finance
do
    grep ",$d," emp.txt > "${d}_employees.txt"
done
```

**Assignment 15** is the extended version of `employee_report.sh` in Part D: add a salary search, a city-wise report and input validation.

</details>

---

## 15. Suggested Folder Structure

```
grep-cut-lab/
├── emp.txt
├── part-a-grep/
│   ├── grep_it.sh
│   ├── grep_name.sh
│   ├── grep_case.sh
│   ├── grep_not_it.sh
│   ├── grep_count.sh
│   ├── grep_multiple.sh
│   └── grep_line.sh
├── part-b-cut/
│   ├── cut_id.sh
│   ├── cut_name.sh
│   ├── cut_name_dept.sh
│   ├── cut_salary.sh
│   └── cut_range.sh
├── part-c-combined/
│   ├── grep_cut_it.sh
│   ├── grep_cut_salary.sh
│   ├── grep_cut_city.sh
│   ├── grep_cut_hr.sh
│   └── grep_salary.sh
└── part-d-mini-project/
    └── employee_report.sh
```

If you move scripts into subfolders, change `emp.txt` to `../emp.txt` or run them from the lab root (`bash part-a-grep/grep_it.sh`).

---

## 16. Summary

| Task | Tool | Example |
|---|---|---|
| Select matching rows | `grep` | `grep ",IT," emp.txt` |
| Exclude rows | `grep -v` | `grep -v ",IT," emp.txt` |
| Count rows | `grep -c` | `grep -c ",IT," emp.txt` |
| Several patterns | `grep -E` | `grep -E ",(IT\|HR)," emp.txt` |
| Select columns | `cut` | `cut -d',' -f2,4 emp.txt` |
| Rows **and** columns | pipe | `grep ",IT," emp.txt \| cut -d',' -f2,4` |
| Save to a file | `>` / `>>` | `grep ",IT," emp.txt > it.txt` |

*End of lab manual.*
