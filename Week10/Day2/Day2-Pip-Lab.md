
# Python Package Management and Virtual Environments

This practical guide demonstrates how to install Python packages, manage dependencies, create a `requirements.txt` file, and work with virtual environments.

We will use a simple Python project named **MyPythonProject** for demonstration.

---

# Part 1: Dependency Management Using pip

**Objective:** Learn how to install, uninstall, and manage external Python libraries.

## Step 1: Open Terminal in VS Code

1. Open Visual Studio Code.
2. Open your project folder.
3. Select **Terminal → New Terminal**.
4. Check whether Python is installed.

```bash
python --version
```

On some systems, use:

```bash
python3 --version
```

Expected output (version may differ):

```text
Python 3.13.0
```

## Step 2: Check pip Installation

Execute the following command:

```bash
pip --version
```

Expected output (simplified):

```text
pip 25.x from ... (python 3.13)
```

If `pip` is not recognized, try:

```bash
python -m pip --version
```

## Step 3: Install a Python Package

Let's install the `requests` package, which allows Python applications to communicate with web services.

Execute:

```bash
pip install requests
```

Alternatively:

```bash
python -m pip install requests
```

Expected output (simplified):

```text
Collecting requests
Installing collected packages: requests
Successfully installed requests
```

## Step 4: Verify the Installed Package

Execute:

```bash
pip show requests
```

This displays package information such as:

- Package name
- Version
- Installation location
- Dependencies

You can also list all installed packages:

```bash
pip list
```

## Step 5: Create a Python Program Using the Package

Create a Python file named `app.py`.

```python
import requests

response = requests.get("https://api.github.com")

print("Status Code:", response.status_code)
print("Response:", response.json())
```

Run the program:

```bash
python app.py
```

Expected output (illustrative):

```text
Status Code: 200
Response: {...}
```

**Note:** An internet connection is required to access the GitHub API.

## Step 6: Uninstall a Package

To remove an installed package, execute:

```bash
pip uninstall requests
```

When prompted, enter:

```text
y
```

The package will be uninstalled.

---

# Part 2: Managing Dependencies Using requirements.txt

**Objective:** Learn how to record project dependencies and install them together.

## Step 1: Install Required Packages

Install two packages:

```bash
pip install requests
pip install pandas
```

## Step 2: Generate requirements.txt

Execute:

```bash
pip freeze > requirements.txt
```

This command creates a file named `requirements.txt` containing the installed packages and their versions.

Example content:

```text
pandas==2.x.x
requests==2.x.x
```

**Note:** Actual versions depend on the installed packages.

## Step 3: View requirements.txt

Open `requirements.txt` in VS Code.

You will see the list of installed dependencies and their versions.

## Step 4: Install Dependencies from requirements.txt

Imagine another developer receives your project.

Instead of installing packages individually, they can execute:

```bash
pip install -r requirements.txt
```

Python will install the packages listed in the file.

## Why Use requirements.txt?

- **Reproducibility:** Helps maintain consistent package versions across environments.
- **Simplicity:** One command installs multiple dependencies.
- **Collaboration:** Makes project sharing easier.
- **Deployment:** Simplifies application deployment.

---

# Part 3: Creating a Virtual Environment

**Objective:** Create an isolated Python environment for a project.

## Step 1: Create a Project Folder

Create a folder named:

```text
MyPythonProject
```

Open this folder in VS Code.

## Step 2: Create a Virtual Environment

Open the VS Code terminal and execute:

```bash
python -m venv .venv
```

On macOS, you can also use:

```bash
python3 -m venv .venv
```

### Explanation

- `python`: Executes the Python interpreter.
- `-m`: Runs a Python module as a script.
- `venv`: Built-in module for creating virtual environments.
- `.venv`: Name of the virtual environment directory.

Your project structure will look like this:

```text
MyPythonProject/
│
└── .venv/
    ├── bin/              # macOS/Linux
    │   └── python
    ├── include/
    ├── lib/
    └── pyvenv.cfg
```

**Note:** Windows uses a `Scripts` directory instead of `bin`.

## Step 3: Activate the Virtual Environment

Choose the command based on your operating system.

### Windows Command Prompt

```bash
.venv\Scripts\activate
```

### Windows PowerShell

```powershell
.\.venv\Scripts\Activate.ps1
```

### macOS / Linux

```bash
source .venv/bin/activate
```

For macOS, execute:

```bash
source .venv/bin/activate
```

After activation, your terminal prompt should display:

```text
(.venv) MyPythonProject %
```

This indicates that the virtual environment is activated.

## Step 4: Verify the Virtual Environment

For macOS/Linux, execute:

```bash
which python
```

Expected output (example):

```text
/Users/username/MyPythonProject/.venv/bin/python
```

This confirms that your terminal is using the Python interpreter inside the virtual environment.

For Windows, use:

```bash
where python
```

## Step 5: Install Packages Inside the Virtual Environment

Now install the `requests` package:

```bash
pip install requests
```

The package will be installed inside the active virtual environment instead of your global Python environment.

Verify the installation:

```bash
pip list
```

## Step 6: Deactivate the Virtual Environment

When you finish working, execute:

```bash
deactivate
```

The `(.venv)` prefix will disappear from your terminal prompt.

---

# Part 4: Complete Project Workflow

Let's combine everything into one practical workflow.

## Step 1: Create a Project Directory

```bash
mkdir MyPythonProject
cd MyPythonProject
```

## Step 2: Create a Virtual Environment

```bash
python -m venv .venv
```

## Step 3: Activate the Environment

For macOS/Linux:

```bash
source .venv/bin/activate
```

## Step 4: Install Required Packages

```bash
pip install requests pandas
```

## Step 5: Generate requirements.txt

```bash
pip freeze > requirements.txt
```

## Step 6: Develop Your Python Application

Create a file named `app.py` and write your application code.

## Step 7: Deactivate the Environment

```bash
deactivate
```

## Final Project Structure

```text
MyPythonProject/
│
├── .venv/
│   └── ...
│
├── app.py
│
└── requirements.txt
```

---

# Part 5: Important Commands – Quick Reference

| Operation | Command |
|---|---|
| Check Python version | `python --version` |
| Check pip version | `pip --version` |
| Install package | `pip install requests` |
| Uninstall package | `pip uninstall requests` |
| List installed packages | `pip list` |
| Show package details | `pip show requests` |
| Generate requirements file | `pip freeze > requirements.txt` |
| Install requirements | `pip install -r requirements.txt` |
| Create virtual environment | `python -m venv .venv` |
| Activate (macOS/Linux) | `source .venv/bin/activate` |
| Activate (Windows CMD) | `.venv\Scripts\activate` |
| Deactivate environment | `deactivate` |

---

# Part 6: Important Best Practices

1. Create a separate virtual environment for every Python project.
2. Activate the virtual environment before installing packages.
3. Maintain a `requirements.txt` file for project dependencies.
4. Do not upload the `.venv` directory to GitHub; add it to `.gitignore`.
5. Share `requirements.txt` with other developers instead of sharing the entire virtual environment.
6. Use `python -m pip` if the `pip` command does not work or you want to ensure pip belongs to the selected Python interpreter.

---

# Part 7: Hands-on Practice for Participants

### Task 1: Project Setup

Create a Python project named `EmployeeManagement`.

### Task 2: Virtual Environment

Create and activate a virtual environment named `.venv`.

### Task 3: Package Installation

Install the following packages:

- `requests`
- `pandas`

### Task 4: Package Verification

Display all installed packages.

### Task 5: Dependency Management

Generate a `requirements.txt` file.

### Task 6: Environment Deactivation

Deactivate the virtual environment.

### Task 7: Environment Reactivation

Reactivate the environment and install dependencies using `requirements.txt`.

### Task 8: Python Application

Create an `app.py` file and import the installed packages.

---

## Learning Outcomes

After completing this practical guide, participants should be able to:

1. Install and uninstall external Python packages.
2. Check installed package information.
3. Generate and use `requirements.txt`.
4. Create and activate virtual environments.
5. Install dependencies inside isolated environments.
6. Understand the importance of dependency management.
7. Set up a Python project independently.
