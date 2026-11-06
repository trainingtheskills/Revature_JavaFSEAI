# 50 Git Interview Questions with Answers and Command Examples

Sections: Basics (1-12) · Branching and Merging (13-22) · Remotes and Collaboration (23-30) · Undoing and Recovery (31-38) · Advanced (39-46) · Scenarios and Internals (47-50)

---

## Basics

### 1. What is Git and why is it called distributed?
Git is a version control system that tracks changes to files. It is distributed because every clone holds the full repository and history, so you can commit, branch and view logs offline, and there is no single point of failure.
```bash
git clone https://github.com/acme/app.git   # full copy including history
git log --oneline                           # works offline
```

### 2. What is the difference between Git and GitHub?
Git is the version control tool that runs locally. GitHub (like GitLab and Bitbucket) is a hosting service for Git repositories that adds pull requests, issues, CI and access control.
```bash
git remote add origin git@github.com:acme/app.git   # link local Git repo to GitHub
```

### 3. What are the three states of a file in Git?
Modified (changed in working directory), Staged (marked for the next commit), Committed (safely stored in the local repo).
```bash
echo "hi" >> a.txt      # modified
git add a.txt           # staged
git commit -m "Update a.txt"   # committed
```

### 4. What is the staging area (index)?
A buffer between the working directory and the repository. It lets you build a commit selectively from only the changes you want.
```bash
git add -p              # stage chosen hunks only
git diff --staged       # see exactly what will be committed
```

### 5. How do you initialize and clone a repository?
`git init` creates a new repo in the current folder. `git clone` copies an existing one.
```bash
git init
git clone https://github.com/acme/app.git my-folder
```

### 6. What does `git status` show?
Current branch, staged changes, unstaged changes and untracked files.
```bash
git status
git status -s     # short form
```

### 7. What is a commit?
An immutable snapshot of the project with a unique SHA hash, author, timestamp, message and parent pointer(s).
```bash
git commit -m "feat: add search"
git show HEAD      # inspect the latest commit
```

### 8. How do you view history?
```bash
git log
git log --oneline --graph --all --decorate
git log -p -- src/app.js          # history of one file with diffs
git log --author="Asha" --since="2 weeks ago"
```

### 9. What is `.gitignore`?
A file listing patterns of files Git should not track (build output, secrets, dependencies). It does not affect files already tracked.
```bash
echo "node_modules/" >> .gitignore
git rm -r --cached node_modules    # stop tracking something already committed
```

### 10. What is the difference between `git diff`, `git diff --staged` and `git diff HEAD`?
- `git diff`: working directory vs staging area
- `git diff --staged`: staging area vs last commit
- `git diff HEAD`: working directory vs last commit
```bash
git diff main..feature      # compare two branches
```

### 11. How do you configure Git?
Config has three levels: system, global (user) and local (repo).
```bash
git config --global user.name "Asha"
git config --global user.email "asha@example.com"
git config --list --show-origin
```

### 12. What are aliases and why use them?
Shortcuts for long commands.
```bash
git config --global alias.lg "log --oneline --graph --all"
git lg
```

---

## Branching and Merging

### 13. What is a branch?
A lightweight movable pointer to a commit. Creating one is nearly free, which is why Git encourages feature branches.
```bash
git branch feature/login
git switch feature/login        # or: git switch -c feature/login
```

### 14. What is HEAD?
A pointer to the currently checked-out commit, usually via a branch. "Detached HEAD" means it points directly at a commit.
```bash
cat .git/HEAD                   # ref: refs/heads/main
git switch --detach v1.0.0      # detached HEAD
```

### 15. What is the difference between `git checkout` and `git switch`/`git restore`?
`checkout` did too many jobs (switch branches and restore files). Git 2.23 split them: `switch` for branches, `restore` for files.
```bash
git switch main
git restore app.js
```

### 16. What is a fast-forward merge?
When the target branch has no new commits, Git just moves its pointer forward with no merge commit.
```bash
git switch main
git merge feature        # fast-forward if main has not diverged
git merge --no-ff feature   # force a merge commit
```

### 17. What is a three-way merge?
When both branches have diverged, Git uses the common ancestor plus both tips to create a merge commit with two parents.
```bash
git merge feature        # creates a merge commit
git merge-base main feature   # shows the common ancestor
```

### 18. How do you resolve a merge conflict?
Open the conflicted files, choose the right content, remove the `<<<<<<<`, `=======`, `>>>>>>>` markers, stage and commit.
```bash
git merge feature
git status                 # lists "both modified"
# edit files
git add config.js
git commit
git merge --abort          # cancel instead
```

### 19. What is rebase and how does it differ from merge?
Rebase replays your commits on top of another base, giving a linear history but rewriting commits. Merge keeps the true history and adds a merge commit.
```bash
git switch feature
git rebase main
```

### 20. When should you not rebase?
Never rebase commits that others already pulled (shared or public branches). It rewrites hashes and causes duplicate or diverged history.
```bash
# safe: local, unpushed branch
git rebase main
# if you rebased a pushed feature branch you own:
git push --force-with-lease
```

### 21. What is interactive rebase?
It lets you squash, reorder, edit, reword or drop commits.
```bash
git rebase -i HEAD~4
# pick / reword / edit / squash / fixup / drop
```

### 22. How do you delete branches?
```bash
git branch -d feature        # safe, only if merged
git branch -D feature        # force
git push origin --delete feature   # delete remote branch
git fetch --prune            # clean stale remote-tracking refs
```

---

## Remotes and Collaboration

### 23. What is `origin`?
The default name Git gives the remote you cloned from. It is just an alias for a URL.
```bash
git remote -v
git remote add upstream git@github.com:original/app.git
```

### 24. Difference between `git fetch` and `git pull`?
`fetch` downloads new commits and updates remote-tracking branches without touching your work. `pull` is `fetch` plus merge (or rebase).
```bash
git fetch origin
git log HEAD..origin/main     # review incoming changes
git pull --rebase
```

### 25. How do you push a new local branch?
```bash
git push -u origin feature/login    # -u sets upstream tracking
git push                            # later pushes need no arguments
```

### 26. What is a remote-tracking branch?
A local read-only reference like `origin/main` that records where the remote branch was at your last fetch.
```bash
git branch -r
git branch -vv       # shows which local branch tracks which remote
```

### 27. What is a Pull Request (Merge Request)?
A hosting-platform feature (not core Git) to propose merging a branch, with code review, discussion and CI checks.
```bash
git push -u origin feature/x
gh pr create --fill       # GitHub CLI
```

### 28. What is the fork workflow?
You fork a repo, clone your fork, add the original as `upstream`, and open PRs from your fork. Common in open source.
```bash
git remote add upstream git@github.com:original/app.git
git fetch upstream
git merge upstream/main
```

### 29. What is `git push --force` and why is `--force-with-lease` safer?
`--force` overwrites the remote branch unconditionally. `--force-with-lease` refuses if the remote has commits you have not seen, protecting teammates' work.
```bash
git push --force-with-lease origin feature/x
```

### 30. How do you change a remote URL?
```bash
git remote set-url origin git@github.com:acme/new-name.git
git remote get-url origin
```

---

## Undoing and Recovery

### 31. How do you undo changes in the working directory?
```bash
git restore file.js          # discard edits to one file
git restore .                # discard all unstaged edits (destructive)
```

### 32. How do you unstage a file?
```bash
git restore --staged file.js
```

### 33. How do you change the last commit?
```bash
git commit --amend -m "Better message"
git add forgotten.js && git commit --amend --no-edit
```
Only amend unpushed commits.

### 34. Explain `reset --soft`, `--mixed` and `--hard`.
- `--soft`: move HEAD only; changes stay staged
- `--mixed` (default): move HEAD and reset index; changes stay in working dir
- `--hard`: move HEAD, reset index and working dir (loses uncommitted work)
```bash
git reset --soft HEAD~1
git reset HEAD~1
git reset --hard HEAD~1
```

### 35. Difference between `reset` and `revert`?
`reset` moves the branch pointer back (rewrites history). `revert` adds a new commit that inverses an old one (history-safe, use for pushed commits).
```bash
git revert a1b2c3d
git revert HEAD~3..HEAD      # revert a range
```

### 36. What is the reflog and how can it save you?
A local log of every position HEAD and branches have held. It lets you recover commits after a bad reset, rebase or deleted branch (usually for ~90 days).
```bash
git reflog
git switch -c recovered HEAD@{3}
git reset --hard HEAD@{1}
```

### 37. How do you recover a deleted branch?
```bash
git reflog | grep feature
git switch -c feature <sha>
```

### 38. You committed to the wrong branch. How do you fix it?
```bash
git switch correct-branch
git cherry-pick <sha>
git switch wrong-branch
git reset --hard HEAD~1       # if the commit was unpushed
```

---

## Advanced

### 39. What does `git stash` do?
Temporarily shelves uncommitted changes so you can switch context.
```bash
git stash push -m "wip login" -u    # -u includes untracked files
git stash list
git stash pop              # apply and drop
git stash apply stash@{1}  # apply and keep
```

### 40. What is cherry-pick?
Applies the changes of a specific commit onto the current branch, creating a new commit.
```bash
git cherry-pick a1b2c3d
git cherry-pick A..B
```

### 41. What is `git bisect`?
Binary search through history to find the commit that introduced a bug.
```bash
git bisect start
git bisect bad
git bisect good v2.0
git bisect run npm test
git bisect reset
```

### 42. What are Git tags? Lightweight vs annotated?
Tags mark releases. Lightweight is just a pointer. Annotated stores tagger, date and message (preferred for releases).
```bash
git tag v1.0.0
git tag -a v1.1.0 -m "Release 1.1.0"
git push origin --tags
```

### 43. What are Git hooks?
Scripts in `.git/hooks` run on events such as `pre-commit`, `commit-msg`, `pre-push`. Used for linting, tests and message rules. Share them via tools like Husky or `core.hooksPath`.
```bash
git config core.hooksPath .githooks
# .githooks/pre-commit
#!/bin/sh
npm run lint
```

### 44. What are submodules and subtrees?
Submodules pin another repo at a specific commit inside yours. Subtrees copy another repo's content into a subdirectory with its history, without special clone steps.
```bash
git submodule add https://github.com/lib/x.git libs/x
git clone --recurse-submodules <url>
git submodule update --init --recursive
```

### 45. What is `git worktree`?
Multiple working directories attached to one repository, so you can check out several branches at once without stashing.
```bash
git worktree add ../hotfix main
git worktree list
git worktree remove ../hotfix
```

### 46. What is `git blame` and `git log -S`?
`blame` shows who last changed each line. `-S` (pickaxe) finds commits that added or removed a string.
```bash
git blame -L 10,30 app.js
git log -S"calculateTax" --oneline
```

---

## Scenarios and Internals

### 47. How does Git store data internally?
As a content-addressable object database in `.git/objects`: **blob** (file contents), **tree** (directory), **commit** (snapshot plus metadata), **tag**. Objects are named by hash of their content, and Git stores snapshots, not diffs (with packfiles using delta compression for storage).
```bash
git cat-file -t HEAD            # commit
git cat-file -p HEAD            # commit contents
git cat-file -p HEAD^{tree}     # root tree
git count-objects -vH
```

### 48. A teammate pushed while you were committing, and your push is rejected. What do you do?
Integrate their work first, then push.
```bash
git pull --rebase origin main
# resolve conflicts if any, then: git rebase --continue
git push
```

### 49. You accidentally committed a secret. What do you do?
1. Rotate/revoke the secret immediately (it is already compromised once pushed).
2. Remove it from history.
3. Prevent recurrence.
```bash
git filter-repo --path secrets.env --invert-paths
git push --force --all
echo "secrets.env" >> .gitignore
```
Add secret scanning (e.g. gitleaks) in pre-commit and CI.

### 50. How would you handle a very large repo or a monorepo efficiently?
Use partial clone, sparse checkout, shallow clones in CI, and background maintenance. Use Git LFS for large binaries.
```bash
git clone --filter=blob:none --sparse <url>
git sparse-checkout set services/payments
git clone --depth 1 <url>              # CI
git maintenance start
git lfs track "*.psd"
```

---

## Bonus: Quick Answers to Common Follow-ups

| Question | One-line answer |
|---|---|
| Merge vs squash merge? | Squash collapses a branch into one commit on the target, keeping `main` tidy. |
| `HEAD~1` vs `HEAD^`? | Same for first parent; `^2` selects the second parent of a merge commit. |
| What is a bare repo? | A repo with no working directory (`git init --bare`), used as a central server remote. |
| What is a detached HEAD? | HEAD points to a commit, not a branch; new commits are orphaned unless you create a branch. |
| What does `git clean` do? | Removes untracked files. Preview with `git clean -nd`, run with `-fd`. |
| How do you see what changed in a commit? | `git show <sha>` or `git diff <sha>^ <sha>`. |
| How do you rename a branch? | `git branch -m old new` |
| How do you squash last N commits? | `git reset --soft HEAD~N && git commit` or `git rebase -i HEAD~N`. |
