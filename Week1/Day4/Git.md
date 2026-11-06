
# Git Fundamentals: Concepts, Architecture and Real-World Use Cases

## 1. What is Git?

Git is a **distributed version control system (DVCS)**. Every developer has a full copy of the repository, including its entire history. You can commit, branch, diff and inspect history offline, and sync with others when you choose.

**Why teams use it**
- Complete history: who changed what, when and why.
- Cheap branching, so work is isolated and parallel.
- Safe collaboration: changes are merged, not overwritten.
- Integrity: every object is checksummed (SHA-1, or SHA-256 in newer repos).
- Recoverability: almost nothing committed is truly lost.

| | Centralized VCS (SVN) | Distributed VCS (Git) |
|---|---|---|
| History location | Central server | Every clone |
| Offline work | Limited | Full |
| Branching | Heavy | Lightweight (a 41-byte pointer) |
| Single point of failure | Yes | No |

---

## 2. Core Concepts

| Concept | Meaning |
|---|---|
| **Repository (repo)** | The project plus its history, stored in the `.git/` directory |
| **Working directory** | The files you see and edit |
| **Staging area (index)** | A draft of your next commit |
| **Commit** | An immutable snapshot with a message, author, timestamp and parent pointer(s) |
| **Branch** | A movable pointer to a commit |
| **HEAD** | A pointer to the commit or branch you are currently on |
| **Tag** | A fixed, named pointer to a commit, usually a release |
| **Remote** | A named reference to another copy of the repo (e.g. `origin`) |
| **Merge** | Combines the histories of two branches |
| **Rebase** | Replays commits on top of another base to produce a linear history |

### The three states of a file

```
Modified  ──git add──▶  Staged  ──git commit──▶  Committed
(working dir)           (index)                  (.git repo)
```

---

## 3. Architecture

### 3.1 The four areas

```
 ┌──────────────┐  git add   ┌──────────────┐ git commit ┌──────────────┐ git push  ┌──────────────┐
 │   Working    │ ─────────▶ │   Staging    │ ─────────▶ │    Local     │ ────────▶ │    Remote    │
 │  Directory   │            │  Area/Index  │            │  Repository  │           │  Repository  │
 │              │ ◀───────── │              │            │   (.git)     │ ◀──────── │ (GitHub etc.)│
 └──────────────┘  restore   └──────────────┘            └──────────────┘ fetch/pull└──────────────┘
         ▲                                                       │
         └───────────────────── git checkout / switch ───────────┘
```

### 3.2 Internal object model

Git is a **content-addressable filesystem**. Everything in `.git/objects` is stored by the hash of its content. There are four object types:

| Object | Stores |
|---|---|
| **blob** | The contents of a file (no filename) |
| **tree** | A directory: a list of names pointing to blobs and subtrees |
| **commit** | A pointer to a root tree, parent commit(s), author, message |
| **tag** (annotated) | A pointer to a commit, plus a tagger and message |

```
commit C3 ──▶ tree ──▶ ├── README.md  ──▶ blob
   │                   └── src/       ──▶ tree ──▶ app.js ──▶ blob
   ▼
commit C2 ──▶ tree ...
   │
   ▼
commit C1 ──▶ tree ...
```

Git stores **snapshots, not diffs**. Unchanged files are reused by pointing to the same blob.

### 3.3 The `.git` directory

```
.git/
├── HEAD           # current branch, e.g. "ref: refs/heads/main"
├── config         # repo-level configuration
├── index          # staging area
├── objects/       # all blobs, trees, commits, tags
├── refs/
│   ├── heads/     # local branches
│   ├── remotes/   # remote-tracking branches
│   └── tags/
├── hooks/         # scripts triggered by events (pre-commit, pre-push, ...)
└── logs/          # reflog: history of where HEAD and branches pointed
```

### 3.4 Branches are just pointers

```
            main
              │
 C1 ◀── C2 ◀── C3
              ▲
              └── feature ◀── C4 ◀── C5
                               │
                             feature (moves forward with each commit)
```

`HEAD → main → C3`. Committing moves the branch pointer that HEAD refers to.

### 3.5 Distributed topology

```
          ┌─────────────────────┐
          │  origin (GitHub)    │
          └──▲──────▲───────▲───┘
       push/ │      │       │ push/
       pull  │      │       │ pull
   ┌─────────┴┐ ┌───┴─────┐ ┌┴────────┐
   │ Dev A    │ │ Dev B   │ │ CI/CD   │
   │ full repo│ │full repo│ │ clone   │
   └──────────┘ └─────────┘ └─────────┘
```

---

## 4. Command Reference by Task

### 4.1 Setup

```bash
git config --global user.name  "Your Name"
git config --global user.email "you@example.com"
git config --global init.defaultBranch main
git config --global pull.rebase true        # optional, keeps history linear
```

### 4.2 Create or obtain a repo

```bash
git init                          # start a new repo in the current folder
git clone <url>                   # copy a remote repo (with full history)
git clone --depth 1 <url>         # shallow clone, faster for CI
```

### 4.3 Everyday workflow

```bash
git status                        # what changed?
git diff                          # unstaged changes
git diff --staged                 # staged changes
git add file.js                   # stage a file
git add -p                        # stage interactively, hunk by hunk
git commit -m "Add login form"    # record a snapshot
git log --oneline --graph --all   # visual history
```

### 4.4 Branching and merging

```bash
git branch                        # list branches
git switch -c feature/login       # create and switch
git switch main                   # switch back
git merge feature/login           # merge into current branch
git merge --no-ff feature/login   # always create a merge commit
git branch -d feature/login       # delete a merged branch
```

### 4.5 Remotes

```bash
git remote -v
git remote add origin <url>
git fetch origin                  # download, don't merge
git pull                          # fetch + merge (or rebase)
git push -u origin feature/login  # publish and set upstream
git push --force-with-lease       # safer force-push after a rebase
```

### 4.6 Undoing things

| Goal | Command |
|---|---|
| Discard unstaged edits to a file | `git restore file.js` |
| Unstage a file | `git restore --staged file.js` |
| Fix the last commit message or add files | `git commit --amend` |
| Undo a commit, keep changes staged | `git reset --soft HEAD~1` |
| Undo a commit, keep changes unstaged | `git reset --mixed HEAD~1` |
| Undo a commit and discard changes (**destructive**) | `git reset --hard HEAD~1` |
| Undo a **pushed** commit safely (new inverse commit) | `git revert <sha>` |
| Recover "lost" commits | `git reflog` then `git reset --hard <sha>` |

### 4.7 Advanced tools

```bash
git stash push -m "wip"           # shelve uncommitted work
git stash pop                     # bring it back
git cherry-pick <sha>             # copy one commit to the current branch
git rebase main                   # replay current branch onto main
git rebase -i HEAD~4              # squash, reorder, edit last 4 commits
git bisect start                  # binary-search for the commit that broke things
git blame file.js                 # who last changed each line
git tag -a v1.2.0 -m "Release 1.2.0"
git worktree add ../hotfix main   # a second working directory, same repo
git submodule add <url> libs/x    # embed another repo
```

---

## 5. Branching Strategies

| Strategy | Best for | Idea |
|---|---|---|
| **GitHub Flow** | Web apps, continuous delivery | `main` is always deployable. Short-lived feature branches go through a PR. |
| **Trunk-based development** | Mature CI/CD teams | Tiny commits to `main` (or very short branches) behind feature flags. |
| **Git Flow** | Versioned, scheduled releases | `main`, `develop`, `feature/*`, `release/*`, `hotfix/*` |
| **Forking workflow** | Open source | Contributors fork, then open PRs to upstream. |

```
GitHub Flow
main     ●────●────────●────────●────▶
           \         /  \     /
feature     ●──●──●─┘    ●──●─┘     (PR, review, merge, deploy)
```

---

## 6. Real-World Use Cases

### Use case 1: Starting a new project and publishing it

**Scenario:** You build an internal tool and want it on GitHub.

```bash
mkdir inventory-api && cd inventory-api
git init
echo "node_modules/" > .gitignore
git add .
git commit -m "Initial commit: project scaffold"
git remote add origin git@github.com:acme/inventory-api.git
git push -u origin main
```

**Real-world note:** Always add `.gitignore` *before* the first commit. Secrets and `node_modules` committed once stay in history.

---

### Use case 2: Feature development with a Pull Request (daily team workflow)

**Scenario:** You add "password reset" while teammates keep merging to `main`.

```bash
git switch main && git pull                    # start from the latest
git switch -c feature/password-reset
# ...edit code...
git add -p                                     # review what you stage
git commit -m "Add password reset endpoint"
git fetch origin
git rebase origin/main                         # stay current, linear history
git push -u origin feature/password-reset
# open a Pull Request on GitHub, get review, squash-merge
git switch main && git pull
git branch -d feature/password-reset
```

---

### Use case 3: Resolving a merge conflict

**Scenario:** You and a teammate both edited `config.js`.

```bash
git pull origin main
# CONFLICT (content): Merge conflict in config.js
git status                       # shows "both modified: config.js"
```

The file now contains markers:

```
<<<<<<< HEAD
timeout = 30
=======
timeout = 60
>>>>>>> origin/main
```

```bash
# edit the file, keep the correct value, remove markers
git add config.js
git commit                       # (or `git rebase --continue` if rebasing)
# bail out if needed: git merge --abort / git rebase --abort
```

---

### Use case 4: Emergency hotfix in production

**Scenario:** A bug in release `v2.3.0` is hitting customers while `main` has half-finished work.

```bash
git fetch --tags
git switch -c hotfix/2.3.1 v2.3.0
# fix the bug
git commit -am "Fix null pointer in checkout"
git tag -a v2.3.1 -m "Hotfix 2.3.1"
git push origin hotfix/2.3.1 --tags
# bring the fix to main too
git switch main
git cherry-pick <fix-sha>
```

`git worktree add ../hotfix v2.3.0` lets you do this without disturbing your current uncommitted work.

---

### Use case 5: Interrupted work, stash

**Scenario:** Mid-feature, your lead asks you to review an urgent PR.

```bash
git stash push -m "login form WIP"
git switch review-branch
# ...review...
git switch feature/login
git stash pop
```

---

### Use case 6: Cleaning history before a PR

**Scenario:** Your branch has commits like "wip", "fix typo", "oops".

```bash
git rebase -i origin/main
# mark commits as: pick / squash / fixup / reword
git push --force-with-lease
```

**Rule:** Rewrite only history that is not shared. Never rebase `main` or branches others have based work on.

---

### Use case 7: Undoing a mistake

| Situation | Fix |
|---|---|
| Committed to the wrong branch (not pushed) | `git reset --soft HEAD~1`, `git switch correct-branch`, `git commit` |
| Bad commit already pushed to `main` | `git revert <sha>` then push |
| Deleted a branch or did a bad `reset --hard` | `git reflog` then `git switch -c recovered <sha>` |
| Staged the wrong file | `git restore --staged file` |
| Typo in last commit message (not pushed) | `git commit --amend -m "Correct message"` |

---

### Use case 8: Finding which commit introduced a bug

**Scenario:** Tests passed in `v2.0` and fail now, with 200 commits in between.

```bash
git bisect start
git bisect bad                  # current commit is broken
git bisect good v2.0            # this one worked
# Git checks out the midpoint; run tests, then mark:
git bisect good    # or: git bisect bad
# ...about 8 steps later Git names the culprit commit
git bisect reset
```

Automate it with `git bisect run npm test`.

---

### Use case 9: Contributing to open source (fork workflow)

```bash
# fork on GitHub, then:
git clone git@github.com:you/project.git
cd project
git remote add upstream git@github.com:original/project.git
git fetch upstream
git switch -c fix/typo-in-docs upstream/main
# edit, commit
git push -u origin fix/typo-in-docs
# open a PR from your fork to upstream
# keep your fork in sync:
git fetch upstream && git switch main && git merge upstream/main
```

---

### Use case 10: Release management and CI/CD

**Scenario:** Release pipeline is triggered by tags.

```bash
git switch main && git pull
git tag -a v3.0.0 -m "Release 3.0.0"
git push origin v3.0.0           # CI builds, tests, deploys on tag push
git log v2.9.0..v3.0.0 --oneline # generate release notes
```

**Common automation with hooks and CI**
- `pre-commit`: lint and format, block secrets.
- `commit-msg`: enforce Conventional Commits (`feat:`, `fix:`).
- `pre-push`: run unit tests.
- Server side: branch protection, required reviews, required status checks.

---

### Use case 11: Accidentally committed a secret

```bash
# 1. ROTATE THE SECRET FIRST. Assume it is compromised.
# 2. Remove from history (git-filter-repo is the modern tool)
git filter-repo --path secrets.env --invert-paths
git push --force --all
# 3. Add to .gitignore; add secret scanning (e.g. gitleaks) to pre-commit/CI
```

---

### Use case 12: Monorepo and large-repo performance

```bash
git clone --filter=blob:none --sparse <url>     # partial clone
git sparse-checkout set services/payments       # only the folder you need
git config core.fsmonitor true                  # faster status on big repos
git maintenance start                           # background optimization
```

---

## 7. Commit Message Best Practices

```
<type>(<scope>): <short summary, imperative, ≤ 50 chars>

<body: what and why, wrapped at ~72 chars>

Refs: #1234
```

Types in Conventional Commits: `feat`, `fix`, `docs`, `refactor`, `test`, `chore`, `perf`, `ci`.

Good: `fix(auth): reject expired refresh tokens`
Bad: `fixed stuff`

---

## 8. Merge vs Rebase vs Squash

| | Merge | Rebase | Squash merge |
|---|---|---|---|
| History | Preserves true history, adds merge commits | Linear | One commit per PR |
| Rewrites commits | No | Yes | Yes (collapses them) |
| Safe on shared branches | Yes | No | Yes (at merge time) |
| Best for | Integrating long-lived branches | Updating your local feature branch | Keeping `main` clean |

---

## 9. Golden Rules

1. Commit small, commit often, one logical change per commit.
2. Never commit secrets, build output or dependencies. Use `.gitignore`.
3. Pull or rebase before you push.
4. Don't rewrite shared history. Use `revert` instead of `reset` on pushed commits.
5. Prefer `--force-with-lease` to `--force`.
6. Protect `main`: require PRs, reviews and passing CI.
7. Use `git status` and `git diff --staged` before every commit.
8. When in doubt, `git reflog` is your safety net.

---

## 10. Quick Cheat Sheet

```text
SETUP      git init | git clone <url> | git config
SNAPSHOT   git status | git add | git commit | git diff
BRANCH     git branch | git switch | git merge | git rebase
SHARE      git remote | git fetch | git pull | git push
INSPECT    git log | git show | git blame | git bisect | git reflog
UNDO       git restore | git reset | git revert | git commit --amend
SHELVE     git stash | git stash pop
RELEASE    git tag | git push --tags
```
