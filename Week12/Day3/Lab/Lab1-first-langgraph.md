```
import os
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI

# Load environment variables from the .env file
load_dotenv()

# Verify that the OpenAI API key is present
if not os.environ.get("OPENAI_API_KEY"):
    raise RuntimeError("OPENAI_API_KEY is missing. Add it to your .env file.")

# Initialize the OpenAI Language Model
llm = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0
)

# Invoke the model and print response
response = llm.invoke("Explain LangGraph in one sentence")
print(response.content)
```
