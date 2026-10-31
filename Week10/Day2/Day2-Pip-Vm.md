# 1. Dependency Management: pip
---
**pip** (Package Installer for Python) is the standard tool for installing and managing additional libraries that are not part of the Python Standard Library.

## 1.1 Installing and Managing Packages
You interact with pip via the terminal (command line).

- Install a package:  ``` pip install requests ```
- Uninstall a package: ``` pip uninstall requests ```
- List installed packages: ``` pip list ```
- Show package details: ``` pip show requests ```
  
## 1.2 Requirements Files
In professional projects, you must track exactly which versions of which libraries your code needs. This is done using a requirements.txt file.

Creating a requirements file:
```
pip freeze > requirements.txt
Installing from a requirements file:

pip install -r requirements.txt
```

## Why use requirements.txt?

**Reproducibility:** Ensures that a teammate (or a deployment server) installs the exact same versions you used.
**Simplicity:** One command sets up the entire environment.


Installing packages globally can lead to "Dependency Hell," where different projects require conflicting versions of the same library. Virtual Environments solve this problem.

# 2. Environment Isolation: Virtual Environments
A Virtual Environment is an isolated directory that contains its own Python installation and its own set of installed packages.
A Virtual Environment in Python is an isolated workspace that allows developers to install and manage Python packages and dependencies separately for each project.

It helps prevent conflicts between package versions used by different Python projects.

## 2.1 Why Use Virtual Environments?
Without isolation, if Project A needs Django 3.0 and Project B needs Django 4.0, you cannot have both installed globally. Virtual environments solve this by creating "bubbles" for each project.
### 1. Why Do We Need a Virtual Environment?
Consider two Python projects:
- Project A: Requires Django 4.2.
- Project B: Requires Django 5.0.
  
If we install both versions globally, managing dependencies can become difficult.

A virtual environment allows each project to maintain its own Python packages and versions independently.

### 2. How Does a Virtual Environment Work?
System Python Installation
Common Python installation on your computer
**Project A**
Virtual Environment
- Django 4.2
- NumPy 1.26
- Pandas 2.0

**Project B**
Virtual Environment
- Django 5.0
- NumPy 2.0
- Pandas 2.2

Each project manages its own installed packages independently.

### The Benefits of Isolation:

- **Conflict Prevention:** Prevents one project's updates from breaking another project.
- **Cleanliness:** Keeps your global Python installation uncluttered.
- **Portability:** Makes it easy to generate a precise requirements.txt.
  
## 2.2 Workflow: Creating and Activating Environments
The most common tool for this is the built-in venv module.

#### 1. Create the environment:

**  'venv' is the module, '.venv' is the name of the folder we are creating **
```
python -m venv .venv
```

#### 2. Activate the environment: Activation tells your terminal to use the Python interpreter inside the .venv folder instead of the global one.

- Windows (Command Prompt):
  ``` .venv\Scripts\activate ```
- Windows (PowerShell):
  ``` .\.venv\Scripts\Activate.ps1 ```
- macOS / Linux:
  ```source .venv/bin/activate```
  
#### 3. Verify Activation: Once activated, your terminal prompt will usually show (.venv). You can also run:

``` which python  # macOS/Linux ```

``` where python  # Windows ```

It should point to a path inside your .venv folder.

#### 4. Deactivate: When you are done working, simply type:

``` deactivate ```


