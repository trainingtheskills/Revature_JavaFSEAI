# 4. Dependency Management: pip
**pip** (Package Installer for Python) is the standard tool for installing and managing additional libraries that are not part of the Python Standard Library.

## 4.1 Installing and Managing Packages
You interact with pip via the terminal (command line).

- Install a package: pip install requests
- Uninstall a package: pip uninstall requests
- List installed packages: pip list
- Show package details: pip show requests
  
## 4.2 Requirements Files
In professional projects, you must track exactly which versions of which libraries your code needs. This is done using a requirements.txt file.

Creating a requirements file:

pip freeze > requirements.txt
Installing from a requirements file:

pip install -r requirements.txt
Why use requirements.txt?

**Reproducibility:** Ensures that a teammate (or a deployment server) installs the exact same versions you used.
**Simplicity:** One command sets up the entire environment.
↑ Back to Table of Contents

Installing packages globally can lead to "Dependency Hell," where different projects require conflicting versions of the same library. Virtual Environments solve this problem.

5. Environment Isolation: Virtual Environments
A Virtual Environment is an isolated directory that contains its own Python installation and its own set of installed packages.

5.1 Why Use Virtual Environments?
Without isolation, if Project A needs Django 3.0 and Project B needs Django 4.0, you cannot have both installed globally. Virtual environments solve this by creating "bubbles" for each project.

The Benefits of Isolation:

Conflict Prevention: Prevents one project's updates from breaking another project.
Cleanliness: Keeps your global Python installation uncluttered.
Portability: Makes it easy to generate a precise requirements.txt.
5.2 Workflow: Creating and Activating Environments
The most common tool for this is the built-in venv module.

1. Create the environment:

# 'venv' is the module, '.venv' is the name of the folder we are creating
python -m venv .venv
2. Activate the environment: Activation tells your terminal to use the Python interpreter inside the .venv folder instead of the global one.

Windows (Command Prompt): .venv\Scripts\activate
Windows (PowerShell): .\.venv\Scripts\Activate.ps1
macOS / Linux: source .venv/bin/activate
3. Verify Activation: Once activated, your terminal prompt will usually show (.venv). You can also run:

which python  # macOS/Linux
where python  # Windows
It should point to a path inside your .venv folder.

4. Deactivate: When you are done working, simply type:

deactivate
