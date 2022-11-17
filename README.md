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

### Notes

1. During the initial socket connection we would want share some config info, 
this would allow us in the future to dynamically set the difficulty game size
and so on and so forth.




# TODOs:
- Add more questions to the DB
- Batch query questions randomly and send them to the FE
- Have the FE track the correct and incorrect responses
- Keep score
- Add an indicator for incorrect responses