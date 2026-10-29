# Sports Statistics Explorer

This is my CSC220 sports statistics project. I used Java Swing for the interface,
OkHttp for API requests, and Gson to read basketball, baseball, and soccer
statistics and compare teams.

## Historical coursework

I keep this repository to describe the project and preserve its main code. I'm
not maintaining it as a standalone application. It reflects the Java setup and
sports API access used for the class; dependencies, endpoints, and historical
season data may now be outdated, deprecated, or unavailable.

## Main code

- `src/main/java/org/example/GUI.java`: the desktop interface.
- `src/main/java/org/example/ApiSportsCalls.java`: API requests and team comparisons.
- The response classes in the same folder: statistics data models.
- `pom.xml`: the original Maven dependencies and Java configuration.

The code expects a `RAPIDAPI_KEY` environment variable and access to the referenced
sports services. This archive isn't a verified installation package for an
individual computer, and it doesn't include an offline data mode.

## Ownership

For details about my contributions and project attribution, see [CREDITS.md](CREDITS.md).
