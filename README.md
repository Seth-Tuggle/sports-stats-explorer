# Sports Statistics Explorer

This is my CSC220 sports statistics project. It's a Java Swing app for looking up
basketball, baseball, and soccer statistics and comparing teams. I use OkHttp for
API requests, Gson for the response data, and Maven to manage dependencies.

## Running the app

You'll need JDK 11 or newer, Maven, and your own RapidAPI access to the sports
APIs used in the code. Set `RAPIDAPI_KEY` in your environment before starting the
app; the GUI reads the key when it launches.

```bash
mvn compile
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=org.example.GUI
```

The league, team, and season choices reflect the original class project. Which
historical statistics you can access depends on your API provider and account.

## Ownership

For details about my contributions and project attribution, see [CREDITS.md](CREDITS.md).
