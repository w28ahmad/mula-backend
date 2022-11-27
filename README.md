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

1. Fix the issue when 2 users connect there is a quick refresh of the questions page
2. Fix the refresh issue such that a user cannot refresh and add limitess users
3. Fix the refresh issue such that the questions don't change when a user refreshes a question page
4. Investigate the finish issue, where if user A finishes the screen for user B should not end
5. Add the indicator for question wrong
6. Create a timer for 10 sec and move the player limit back to 5 
7. If a user gets a question wrong, they redirected to another question
8. Add (YOU) outside the progress bar that belongs to you

- DB Migrations
  - Make them lexographic so the migrations can be run easily

- Add more questions
  - Maybe: Add a better way to input questions that is faster
  - Add support for diagrams using AWS buckets key value store