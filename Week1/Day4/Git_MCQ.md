# Git MCQ Practice Questions

Topics: Git Introduction · Source Control Management (Git, VCS, CVCS, DVCS) · Git Fundamentals · Initializing a Repository · Pushing to a Remote Repository · Commit, Branch, Merge, Push, Pull · .gitignore

Choose one answer per question unless stated. Answers and explanations are in the **Answer Key** at the end.

---

## Section 1: Git Introduction

**Q1.** Git is best described as:
- A) A programming language
- B) A distributed version control system
- C) A cloud hosting platform
- D) A code editor

**Q2.** Who created Git?
- A) Dennis Ritchie
- B) James Gosling
- C) Linus Torvalds
- D) Guido van Rossum

**Q3.** Git was originally created to manage the source code of which project?
- A) Apache
- B) Linux kernel
- C) Windows
- D) MySQL

**Q4.** Which statement about Git and GitHub is correct?
- A) They are the same product
- B) GitHub is the tool that runs locally; Git is the website
- C) Git is the version control tool; GitHub is a hosting service for Git repositories
- D) GitHub cannot be used without paying

**Q5.** Which command shows the installed Git version?
- A) `git -v list`
- B) `git --version`
- C) `git info`
- D) `git show version`

**Q6.** Which of the following is NOT a benefit of using Git?
- A) Tracking the history of changes
- B) Working offline on most operations
- C) Automatically fixing bugs in code
- D) Collaborating without overwriting others' work

---

## Section 2: Source Control Management (Git, VCS, CVCS, DVCS)

**Q7.** What does VCS stand for?
- A) Virtual Code System
- B) Version Control System
- C) Verified Commit Storage
- D) Variable Control Software

**Q8.** What is the main purpose of a version control system?
- A) Compile code faster
- B) Record changes to files over time so earlier versions can be recalled
- C) Encrypt source files
- D) Deploy applications

**Q9.** Which of these is an example of a Centralized VCS (CVCS)?
- A) Git
- B) Mercurial
- C) Subversion (SVN)
- D) Bazaar

**Q10.** In a Centralized VCS, where is the full project history primarily stored?
- A) On every developer's machine
- B) On a central server
- C) In the local working directory
- D) In the staging area

**Q11.** In a Distributed VCS like Git, what does each developer's clone contain?
- A) Only the latest snapshot
- B) Only the files they edited
- C) The full repository including its history
- D) Only a list of filenames

**Q12.** Which is a key disadvantage of a Centralized VCS?
- A) It supports branching
- B) The central server is a single point of failure
- C) It tracks file history
- D) It supports multiple users

**Q13.** Which is a Distributed VCS?
- A) CVS
- B) Perforce (classic mode)
- C) Git
- D) Subversion

**Q14.** Which of these can you usually do offline in Git? *(Select the best answer)*
- A) Commit and view history
- B) Push to GitHub
- C) Pull from GitHub
- D) Open a pull request

**Q15.** What do the three generations of version control generally look like?
- A) Distributed → Centralized → Local
- B) Local → Centralized → Distributed
- C) Centralized → Local → Distributed
- D) Local → Distributed → Centralized

---

## Section 3: Git Fundamentals

**Q16.** Where do your edits live before you run `git add`?
- A) Staging area
- B) Remote repository
- C) Working directory
- D) Local repository

**Q17.** What is the staging area (index)?
- A) A backup of the remote
- B) A place where you prepare what will go into the next commit
- C) A list of branches
- D) The folder that holds `.gitignore`

**Q18.** Which is the correct order of the Git local workflow?
- A) Commit → Add → Edit
- B) Edit → Add → Commit
- C) Add → Edit → Commit
- D) Edit → Commit → Add

**Q19.** What is a commit in Git?
- A) A temporary change
- B) A snapshot of the project with a message, author and unique ID
- C) A remote server
- D) A type of branch

**Q20.** What does the hash (SHA) of a commit uniquely identify?
- A) The branch name
- B) That specific commit and its content
- C) The remote URL
- D) The user's password

**Q21.** Which command shows the status of the working directory and staging area?
- A) `git state`
- B) `git check`
- C) `git status`
- D) `git diff --all`

**Q22.** Which folder stores all of a repository's internal data?
- A) `.gitfiles`
- B) `.repo`
- C) `.git`
- D) `.version`

**Q23.** What does HEAD refer to?
- A) The first commit ever made
- B) The current commit or branch you are on
- C) The remote repository
- D) The staging area

**Q24.** Which command shows the commit history in a compact one-line form?
- A) `git history --short`
- B) `git log --oneline`
- C) `git show --compact`
- D) `git commits -1`

**Q25.** Git stores data primarily as:
- A) Line-by-line differences only
- B) Snapshots of the project at each commit
- C) Zip archives of each file
- D) Database tables

---

## Section 4: Initializing a Repository

**Q26.** Which command creates a new empty Git repository in the current folder?
- A) `git start`
- B) `git create`
- C) `git init`
- D) `git new`

**Q27.** What does `git init` create inside your project folder?
- A) A `README.md`
- B) A hidden `.git` directory
- C) A `.gitignore` file
- D) A remote connection

**Q28.** Which command copies an existing remote repository to your machine?
- A) `git copy <url>`
- B) `git download <url>`
- C) `git clone <url>`
- D) `git pull <url>` on an empty folder without init

**Q29.** Which command creates a repository inside a new folder called `myapp`?
- A) `git init myapp`
- B) `git folder myapp`
- C) `git new myapp`
- D) `git mkdir myapp`

**Q30.** Before your very first commit you must configure:
- A) Your remote URL
- B) Your name and email
- C) Your GitHub password
- D) Your branch protection rules

**Q31.** Which command sets your global username?
- A) `git set user "Asha"`
- B) `git config --global user.name "Asha"`
- C) `git user --global "Asha"`
- D) `git global name Asha`

**Q32.** You run `git init` and then `git status`. What is the usual message about commits?
- A) "Repository is corrupted"
- B) "No commits yet"
- C) "Remote not found"
- D) "Nothing to init"

**Q33.** Which command stages ALL changes in the current directory?
- A) `git stage --all`
- B) `git add .`
- C) `git commit .`
- D) `git push .`

---

## Section 5: Pushing to a Remote Repository

**Q34.** Which command links your local repo to a remote named `origin`?
- A) `git remote add origin <url>`
- B) `git connect origin <url>`
- C) `git link origin <url>`
- D) `git origin add <url>`

**Q35.** Which command lists your configured remotes with URLs?
- A) `git remote -v`
- B) `git remotes`
- C) `git origin -list`
- D) `git show remote`

**Q36.** What does `git push -u origin main` do?
- A) Deletes the `main` branch
- B) Pushes `main` to `origin` and sets it as the upstream tracking branch
- C) Pulls `main` from `origin`
- D) Updates Git itself

**Q37.** What is `origin`?
- A) A reserved Git keyword for the first commit
- B) The default name for the remote you cloned from or added
- C) The main branch
- D) The local repository

**Q38.** Your push is rejected because the remote has commits you don't have. What should you do first?
- A) Delete the remote
- B) Pull (or fetch and rebase/merge) the remote changes, then push again
- C) Re-run `git init`
- D) Always use `git push --force`

**Q39.** Which flag is the safest way to force-push after rewriting your own branch?
- A) `--force-with-lease`
- B) `--hard`
- C) `--delete`
- D) `--ignore`

**Q40.** After `git push -u origin main`, how can you push later?
- A) You must always type the full command
- B) Just `git push`
- C) `git commit --push`
- D) `git send`

**Q41.** Which command pushes all your tags to the remote?
- A) `git push --tags`
- B) `git tag --send`
- C) `git push tags`
- D) `git remote tags`

---

## Section 6: Git Commit, Branch, Merge, Push, Pull

**Q42.** Which command commits staged changes with a message?
- A) `git commit -m "message"`
- B) `git save "message"`
- C) `git push -m "message"`
- D) `git add -m "message"`

**Q43.** What does `git commit -am "msg"` do?
- A) Commits only new untracked files
- B) Stages all modified tracked files and commits them
- C) Amends the previous commit
- D) Pushes and commits

**Q44.** What is a branch?
- A) A copy of the whole repository on disk
- B) A lightweight movable pointer to a commit
- C) A remote server
- D) A type of tag that cannot move

**Q45.** Which command creates AND switches to a new branch called `feature`?
- A) `git branch feature`
- B) `git switch -c feature`
- C) `git new feature`
- D) `git checkout feature`

**Q46.** Which command lists all local branches?
- A) `git branches`
- B) `git branch`
- C) `git list`
- D) `git show branches`

**Q47.** To merge `feature` into `main`, you should:
- A) Be on `feature` and run `git merge main`
- B) Be on `main` and run `git merge feature`
- C) Run `git join feature main`
- D) Run `git push feature main`

**Q48.** What is a fast-forward merge?
- A) A merge that skips conflicts by deleting files
- B) A merge where the branch pointer just moves forward because no divergence exists
- C) A merge that always creates a new commit
- D) A merge done only on remotes

**Q49.** A merge conflict happens when:
- A) You forget to commit
- B) Both branches changed the same part of the same file differently
- C) You create a new branch
- D) You run `git status`

**Q50.** Which markers appear in a conflicted file? *(Select the best answer)*
- A) `<<<<<<<`, `=======`, `>>>>>>>`
- B) `/*`, `*/`, `//`
- C) `###`, `---`, `***`
- D) `[[[`, `|||`, `]]]`

**Q51.** After manually fixing a conflict, what do you do next?
- A) `git add <file>` then `git commit`
- B) `git delete`
- C) `git clone`
- D) Restart the computer

**Q52.** Which command cancels an in-progress merge?
- A) `git merge --stop`
- B) `git merge --abort`
- C) `git reset --merge-cancel`
- D) `git cancel`

**Q53.** What does `git pull` do?
- A) `git fetch` followed by merge (or rebase) into the current branch
- B) Only uploads local commits
- C) Deletes the remote branch
- D) Creates a new repository

**Q54.** Which is the difference between `git fetch` and `git pull`?
- A) No difference
- B) `fetch` downloads changes without merging; `pull` downloads and integrates them
- C) `fetch` uploads; `pull` downloads
- D) `pull` only works on tags

**Q55.** Which command safely deletes a branch that has been merged?
- A) `git branch -d feature`
- B) `git branch -rm feature`
- C) `git delete feature`
- D) `git remove branch feature`

**Q56.** Which command renames the current branch to `main`?
- A) `git branch -M main`
- B) `git rename main`
- C) `git switch --rename main`
- D) `git branch --new main`

**Q57.** Which command deletes a branch on the remote?
- A) `git push origin --delete feature`
- B) `git remote remove feature`
- C) `git branch -D origin/feature`
- D) `git fetch --delete feature`

**Q58.** Which sequence correctly publishes a new local branch `feature/login`?
- A) `git push -u origin feature/login`
- B) `git pull origin feature/login`
- C) `git merge origin feature/login`
- D) `git commit origin feature/login`

---

## Section 7: .gitignore

**Q59.** What is the purpose of `.gitignore`?
- A) To delete files from disk
- B) To tell Git which files or folders not to track
- C) To store passwords securely
- D) To list all branches

**Q60.** Where is `.gitignore` normally placed?
- A) Inside `.git/objects`
- B) In the repository root (or any subfolder)
- C) Only on the remote server
- D) In the user's home folder only

**Q61.** Which entry ignores the whole `node_modules` folder?
- A) `node_modules/`
- B) `-node_modules`
- C) `!node_modules`
- D) `#node_modules`

**Q62.** What does `*.log` in `.gitignore` do?
- A) Ignores only a file named `*.log`
- B) Ignores all files ending in `.log`
- C) Ignores a folder called `log`
- D) Tracks only log files

**Q63.** What does a line starting with `#` mean in `.gitignore`?
- A) An ignore rule
- B) A comment
- C) An include rule
- D) An error

**Q64.** What does `!important.log` do after `*.log` in `.gitignore`?
- A) Deletes `important.log`
- B) Re-includes `important.log` (negates the earlier ignore)
- C) Ignores it twice
- D) Causes an error

**Q65.** A file is already tracked by Git. You add it to `.gitignore`. What happens?
- A) Git immediately stops tracking it
- B) Nothing, Git keeps tracking it until you untrack it
- C) The file is deleted
- D) The repo is corrupted

**Q66.** Which command stops tracking an already committed file but keeps it on disk?
- A) `git rm --cached file.txt`
- B) `git rm file.txt`
- C) `git ignore file.txt`
- D) `git delete --cached file.txt`

**Q67.** Which of these should normally be in `.gitignore`? *(Select the best answer)*
- A) Source code
- B) `README.md`
- C) `.env` files with secrets and build output folders
- D) `package.json`

**Q68.** Which command shows why a path is being ignored?
- A) `git check-ignore -v <path>`
- B) `git why <path>`
- C) `git ignore --explain <path>`
- D) `git status --why`

**Q69.** What does `/build` (with a leading slash) mean in `.gitignore`?
- A) Ignore `build` anywhere in the repo
- B) Ignore `build` only at the same level as that `.gitignore` file
- C) Ignore all files that contain "build"
- D) Include `build` only

**Q70.** Is an empty folder tracked by Git?
- A) Yes, always
- B) No, Git tracks files only; use a placeholder like `.gitkeep` to keep an empty folder
- C) Yes, if it is named `empty`
- D) Only on Windows

---

## Answer Key

| Q | Ans | Explanation |
|---|---|---|
| 1 | B | Git is a distributed VCS. |
| 2 | C | Linus Torvalds created Git in 2005. |
| 3 | B | Built for Linux kernel development. |
| 4 | C | Git is the tool; GitHub hosts repos and adds PRs, issues, CI. |
| 5 | B | `git --version` |
| 6 | C | Git tracks changes; it does not fix bugs. |
| 7 | B | Version Control System. |
| 8 | B | VCS records changes so you can recall earlier versions. |
| 9 | C | SVN (also CVS, classic Perforce) is centralized. |
| 10 | B | History lives on the central server. |
| 11 | C | Every clone is a full copy with history. |
| 12 | B | Central server is a single point of failure. |
| 13 | C | Git (also Mercurial, Bazaar) is distributed. |
| 14 | A | Commit and log are local. Push, pull and PRs need a network. |
| 15 | B | Local, then centralized, then distributed. |
| 16 | C | Edits live in the working directory until staged. |
| 17 | B | The index holds what goes into the next commit. |
| 18 | B | Edit, `git add`, `git commit`. |
| 19 | B | A commit is a snapshot with metadata and a hash. |
| 20 | B | The hash identifies that commit and its content. |
| 21 | C | `git status` |
| 22 | C | `.git` holds all repository data. |
| 23 | B | HEAD is the current commit or branch. |
| 24 | B | `git log --oneline` |
| 25 | B | Git stores snapshots, not only diffs. |
| 26 | C | `git init` |
| 27 | B | It creates the hidden `.git` directory. |
| 28 | C | `git clone <url>` |
| 29 | A | `git init myapp` creates the folder and repo. |
| 30 | B | Name and email are recorded in each commit. |
| 31 | B | `git config --global user.name "Asha"` |
| 32 | B | Fresh repos report "No commits yet". |
| 33 | B | `git add .` stages all changes in the current directory. |
| 34 | A | `git remote add origin <url>` |
| 35 | A | `git remote -v` |
| 36 | B | `-u` sets upstream so later `git push` and `git pull` need no arguments. |
| 37 | B | `origin` is just the conventional remote name (an alias for a URL). |
| 38 | B | Integrate remote changes, then push. Force-pushing can destroy teammates' work. |
| 39 | A | `--force-with-lease` refuses if the remote has commits you haven't seen. |
| 40 | B | Upstream is set, so plain `git push` works. |
| 41 | A | `git push --tags` |
| 42 | A | `git commit -m "message"` |
| 43 | B | `-a` stages modified tracked files; new untracked files are not included. |
| 44 | B | A branch is a movable pointer to a commit. |
| 45 | B | `git switch -c feature` (or `git checkout -b feature`). Option A creates but doesn't switch. |
| 46 | B | `git branch` |
| 47 | B | You merge INTO the branch you are on. |
| 48 | B | Fast-forward just moves the pointer when no divergence exists. |
| 49 | B | Both sides changed the same lines differently. |
| 50 | A | Conflict markers are `<<<<<<<`, `=======`, `>>>>>>>`. |
| 51 | A | Edit, `git add <file>`, then `git commit`. |
| 52 | B | `git merge --abort` |
| 53 | A | `git pull` is fetch plus merge or rebase. |
| 54 | B | `fetch` downloads only; `pull` also integrates. |
| 55 | A | `-d` refuses to delete unmerged branches; `-D` forces. |
| 56 | A | `git branch -M main` |
| 57 | A | `git push origin --delete feature` |
| 58 | A | `git push -u origin feature/login` |
| 59 | B | Lists files and folders Git should not track. |
| 60 | B | Usually in the repo root; subfolders can have their own. |
| 61 | A | `node_modules/` ignores the folder. |
| 62 | B | The wildcard matches any `.log` file. |
| 63 | B | `#` starts a comment. |
| 64 | B | `!` negates an earlier pattern. |
| 65 | B | `.gitignore` only affects untracked files; untrack it with `git rm --cached`. |
| 66 | A | `git rm --cached file.txt` keeps the file on disk. |
| 67 | C | Secrets, dependencies and build output should be ignored. |
| 68 | A | `git check-ignore -v <path>` shows the matching rule. |
| 69 | B | A leading `/` anchors the pattern to that directory level. |
| 70 | B | Git tracks files, not empty folders; add `.gitkeep`. |

**Score guide:** 60+ correct = strong · 45-59 = good, review weak sections · below 45 = revisit the fundamentals.
