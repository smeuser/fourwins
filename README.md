# four wins application

## why?

This is a simple four wins game which i made during my qualification to become a computer scientist (2004 - 2006).

It was a project in our java class, and we could figure out an idea on our own. This project was created with Java 1.2 and is very old.
It was also my first java application which original did run as a java applet in a browser. I tried to interpret the MVC concept for the very first time.

### features

The application offers
- base mechanics of a classic four wins game.
- option to play with 2 players in a hot seat session
- option to play with 2 players in a network session (did work as java applet in local area network)
- option to chat between both players in a network session
- option to play against an algorithm - artificial (fake) intelligence
- opton to get a hint by the algorithm while playing in single player mode
- option to save a single player session
- option to restore a single player session

### storing and saving game sessions

Its quite simple, serializing a session and storing into a file. Same for restoring.

### networking

The networking lets the initiating user create a server and the other player has to connect to it as a client.

The server is a thread running while contain an input and an output stream, which are used for network communication.
The communication transmit simple objects, these objects determine how to treat them.
Possible objects are
- String => Result in a chat message
- GameSetting => contains the configuration of the game session which has to be the same for both players
- Message => which represents a move of the current user and is sent through the output stream.
- Incoming => represents a move as well which has been sent by the opponent
- ShutdownSignal => indicates that the other user has closed the session


### algorithm (artificial intelligence)

my first approach to implement something like an artifical intelligence (nameing does not fit anymore these days)

#### what it does?

The base logic is like a lookup mechanic. The algorithm is going to rate a move.

#### how does the rating work?

The algorithm is going to create a numerical rating for each move, the higher the rating, the better the move.
It counts all potential options to make four in a row. The closer the option is to the final wanted result, the higher the rating of the option will be determined.
If a row to make four in a row is blocked by a token of the opponent or the edge of the board, it is no option anymore and will not boost the rating. 
It also counts the options of the opponent in the same manner, but it will create a negative rating instead.
All ratings of these options will get summed up in the end and lead to a final score of the board after applying a certain move.

This is done for every potential move which is currently possible. The 3 difficulties determine the depth of the rating algorithm, the harder the algorithm is set, more moves in conjunction will get rated.
The move which offers best choices for the current step of the algorithm is getting picked, in considerations of the options of the follow up moves of the algorithm and the opponent as well.

#### know issues of the rating algorithm

Sometimes the algorithm does not see direct options for the opponent to win the game, especially in the early game phase.
It would be easy to treat circumstances like this as a no-option-at-all rating, but i never did implement this.
Instead the algorithm gets a bit lost in counting and summarizing up all options and is not capable of preventing the unavoidable defeat.

Besides this, the algorithm works quite fine and makes it hard to beat for a human player on the highest level of difficulty.

#### what was necessary to make it work?

The base logic of the rating system was the simple idea. But to have the final rating make decisions which really result in good game play, did show up after the weighting of the different kind of options did get applied.

Compared to real artificial intelligences which flood the internet nowadays, it has a few similarities.
Current AI's get trained by themselves through an algorithm in a neural network. The decision-making gets improved by the weighting for each neuron, which gets transported through all neuron layers through back propagation.
The weighting process was only done once in this algorithm and now is static, which is sufficient for a game like this with finite possibilities.

## History

This project was created on 2005 with java 1.2. The intention was to just learn java and object-oriented programming through my qualification.
There are no modern software development concepts applied which are currently must haves like design patterns, SOLID or dependency injection.
The only concept I did try to follow was the MVC pattern by my personal understanding. To this time just learning java was the key, and I did try to do more than my qualification was about to teach me to this point.
