# Standard layout for real projects

This follows the official “Bigger Applications” pattern, with the common additions most teams make:

project/
├── app/
│   ├── __init__.py
│   ├── main.py            # creates the FastAPI app, includes routers
│   ├── core/
│   │   └── config.py      # settings, environment variables
│   ├── db/
│   │   └── database.py    # DB engine and session
│   ├── models/
│   │   └── book.py        # database tables (SQLAlchemy)
│   ├── schemas/
│   │   └── book.py        # Pydantic models (your BookIn)
│   ├── routers/
│   │   └── books.py       # endpoints (APIRouter)
│   ├── services/
│   │   └── book_service.py  # business logic
│   └── dependencies.py    # shared dependencies (auth, DB session)
├── tests/
│   └── test_books.py
├── requirements.txt
├── .env                   # secrets, never commit
├── .gitignore
└── README.md
