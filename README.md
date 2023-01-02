# Mula

An application to gamify learning by using a corpus of subject specific question
grouped by difficulty to create a multiplayer game for people over the world.

### Initial Prototype

The initial prototype will make use of sockets to create a game between some number
players. Here are the proof of concept criterion:

- Connect react FE to Kotlin BE using sockets :check:
- Create a game between some set of individuals
- Display a question stored in DB on the FE
- Build a competing system for users compete and finish the game
- Make the test endpoints more descriptive

### Notes

1. During the initial socket connection we would want share some config info,
   this would allow us in the future to dynamically set the difficulty game size
   and so on and so forth.

# TODOs:

- Refactor code to have the timer with the session
- Ensure Player removal occurs on tab close
- Add indicator for question wrong
- If a user gets a question wrong, they redirected to another question
- Fix the page size/scroll issues when page content gets too large

- The entire session should be controlled by the backend,
  Closing session ... starting game, these should all be events
  propagated by the backend

- make question game debugging more modularized so if in the future,
  the question page is modularized to a different frontend, we can easily
  point to the debug game page

- Reduce the dependency on question id within the sql, ideally we want to be able
  to CRUD operations without resetting anything! So the question ID -- mapping to
  question number is becoming an issue

- Review why diagrams do not appear on prod

- export const HOST = "http://localhost:8080" <- this should be controlled by environment variable
- Review if the question page has a lot of duplicate code or if there is a better way
  to generate the json
- Java SockJS TaskScheduler to use for scheduling heartbeats tasks

- All responses should be instantiated within the controllers

# Queue Refactoring idea

- We want to store player stats
    - Stats need can contain numberCorrect & numberIncorrect
    - Then if user x is incorrect then:
        - check backupSize >= numberIncorrect: then no need to send backup
        - check backupSize <  numberIncorrect: reset backupSize & sendBackup question

# Good Deployment Instructions

youtube.com/watch?v=PH-Mcd0Rs1w&list=PLy_6D98if3ULEtXtNSY_2qN21VCKgoQAE&index=32
