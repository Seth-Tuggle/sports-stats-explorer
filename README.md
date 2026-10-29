# Sports Statistics Explorer

A CSC220 Java Swing desktop application for retrieving and comparing basketball,
baseball, and soccer team statistics. The project separates the GUI, API calls,
and response models and uses OkHttp and Gson through Maven.

## Setup

Requires JDK 11 or newer and Maven. Obtain your own RapidAPI access to the sports
APIs referenced by the source. Set `RAPIDAPI_KEY` in your process environment.
The GUI reads it at launch; no API credential is stored in this repository.

```bash
mvn compile
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=org.example.GUI
```

League, team, and season selections reflect the original coursework. API access
and historical-data availability depend on your provider account. GUI/API calls
were not exercised during recovery. Maven dependencies remain as recovered.
