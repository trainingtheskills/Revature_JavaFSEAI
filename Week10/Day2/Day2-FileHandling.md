# File Handling
---
File handling refers to the process of performing operations on a file, such as creating, opening, reading, writing and closing it through a programming interface. 

It involves managing the data flow between the program and the file system on the storage device, ensuring that data is handled safely and efficiently.

## Need for File Handling
1. Store data permanently, even after the program ends.
2. Access external files like .txt, .csv, .json, etc.
3. Process large files efficiently without using much memory.
4. Automate tasks like reading configs or saving outputs.

## # 1. Opening and Closing Files

Before reading from or writing to a file, you must **open** it using Python's built-in `open()` function.

The `open()` function takes two primary arguments:

- **File Path:** Specifies the location and name of the file.
- **Mode:** Specifies how the file should be opened (read, write, append, etc.).

### Common File Modes

| Mode | Description |
|---|---|
| `'r'` | **Read:** Opens a file for reading. Raises `FileNotFoundError` if the file does not exist. |
| `'w'` | **Write:** Opens a file for writing. Creates a new file if it does not exist or overwrites its existing content. |
| `'a'` | **Append:** Opens a file for appending data at the end without deleting existing content. |
| `'x'` | **Exclusive Creation:** Creates a new file. Raises `FileExistsError` if the file already exists. |
| `'+'` | **Updating:** Opens a file for both reading and writing. Examples: `'r+'`, `'w+'`. |

## Example 1: Opening and Closing a File Manually

```python
# Open a file in write mode
file = open("student.txt", "w")

# Write data into the file
file.write("Welcome to Python File Handling!")

# Close the file manually
file.close()

```
# Opening a file safely using a context manager
with open("example.txt", "r") as file:
    # Perform file operations here
    pass
# File is automatically closed here
```

## 2. Reading Files

Once a file is opened in **read mode (`'r'`)**, you can extract its contents using different file-reading methods provided by Python.

Python provides three commonly used methods for reading file contents:

### Common File Reading Methods

| Method | Description |
|---|---|
| `read(size)` | Reads the entire file if no size is specified. If a size is provided, reads up to the specified number of characters. |
| `readline()` | Reads a single line from the file at a time. |
| `readlines()` | Reads all lines from the file and returns them as a list of strings. |

### Example 1: Using `read()`

The `read()` method reads the entire content of a file as a single string.

```python
with open("student.txt", "r") as file:

    # Read the entire file
    content = file.read()

    print(content)

```
# Writing a quick sample file first
with open("notes.txt", "w") as f:
    f.write("Line 1: Python file handling\nLine 2: Easy and powerful\nLine 3: Always use 'with'")

# Reading the file line by line
print("--- Reading Line by Line ---")
with open("notes.txt", "r") as f:
    for line in f:
        print(line.strip()) # .strip() removes trailing newline characters
```

## 3. Writing and Appending to Files

To write data into a file, Python provides two commonly used file modes:

- **Write (`'w'`)**: Removes any existing content from the file and starts writing fresh content. If the file does not exist, Python creates a new file.
- **Append (`'a'`)**: Preserves the existing content and adds new data at the end of the file. If the file does not exist, Python creates a new file.

### Example 1: Writing to a File Using `'w'` Mode

The `write()` method is used to write text into a file.

```python
# Open the file in write mode
with open("student.txt", "w") as file:

    # Write data into the file
    file.write("101, Ashu, Python\n")
    file.write("102, Rahul, Java\n")
    file.write("103, Amit, JavaScript\n")

print("Student data written successfully!")

```
# Writing new data (overwrites existing file)
with open("output.txt", "w") as f:
    f.write("Hello, World!\n")
    f.writelines(["Second line\n", "Third line\n"])

# Appending data to the same file
with open("output.txt", "a") as f:
    f.write("Appended a new line at the end.\n")

# Verify contents
with open("output.txt", "r") as f:
    print(f.read())
```


## 4. Modern Path Management with `pathlib`

While passing string paths such as `"data/file.txt"` works, Python provides the built-in **`pathlib` module** for handling file and directory paths.

Introduced in Python 3.4, `pathlib` offers a modern, object-oriented, and cross-platform approach to working with file system paths.

It automatically handles differences between operating systems such as Windows, macOS, and Linux.

### Advantages of `pathlib`

- **Object-Oriented:** Represents file paths using `Path` objects.
- **Cross-Platform:** Automatically handles operating system-specific path separators.
- **Readable Code:** Provides simple methods for common file and directory operations.
- **File Management:** Supports checking, creating, reading, writing, and deleting files and directories.

### Example 1: Creating a Path Using `pathlib`

```python
from pathlib import Path

# Create a file path
file_path = Path("data/student.txt")

# Display the path
print(file_path)

```
from pathlib import Path

# Create a Path object
file_path = Path("notes.txt")

# Check if the file exists
if file_path.exists():
    print(f"File exists! Size: {file_path.stat().st_size} bytes")
    
    # Read text in one clean line using pathlib
    content = file_path.read_text()
    print(content)
```
