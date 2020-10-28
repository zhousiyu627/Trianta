# Blackjack Game

README file for Blackjack project.


## Table of Contents

- [Support](#support)
- [Usage](#usage)
- [Design](#design)
- [Example input](#example input)
- [Contributor](#contributor)
- [License](#license)

## Support

Muti-player supported

The cards are reshuffled once all the cards have been used up.

Players can choose whether to become dealers or not. There is always a dealer exsit

Rotating the Banker


## Usage

You can input simple lines on your command line to run the code.

```sh
javac Main.java
java Main
```

### Design

Class Blackjack : This is the main program for the blackjack game.
                  Main() is in this class. The start point of the project.
                  
Class BlackjackRound : This method executes a single round of play (for all players).

Class SinglePlayerBJ : 1 player blackjack game.

Class MultiPlayerBJ : n players blackjack game.

Class Card : This class represents one playing card.

Class Deck : This class represents the deck of cards from which cards are dealt to players.

Class Hand : This class represents the set of cards held by one player (or the dealer).

Class IO : Check if the input meets the requirements

Class Player : This class represents one blackjack player (or the dealer)

## Example input
There will be only one dealer.
```
You can choose Blackjack(1) or Trianta Ena(2) to play.(3 to quit)
2
Welcome to Card Game!
Welcome to Trianta Ena Game!
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Please enter the number of player(s): (2<=number<=9)5
--+--+--+--+--+--+--+--+--+--+--+--+--+--
What's everybody's bank value? (Please input integer, 0<x<100000)
100
Dealer's bank value will be 300
What's your name, player 1?
a
Do you want to be the dealer? (Y/N) 
y
--+--+--+--+--+--+--+--+--+--+--+--+--+--
What's your name, player 2?
b
Do you want to be the dealer? (Y/N) 
y
There is a dealer exist. Do you want to replace it?
y
--+--+--+--+--+--+--+--+--+--+--+--+--+--

```
...
```shell script
--+--+--+--+--+--+--+--+--+--+--+--+--+--
b is the dealer!
```


Ask player if they want to fold
```
a, do you want to fold this round? (Y/N) 
n
c, do you want to fold this round? (Y/N) 
y
d, do you want to fold this round? (Y/N) 
n
e, do you want to fold this round? (Y/N) 
n
Please input your bet, a, your current money is: 100: 30
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Please input your bet, d, your current money is: 100: 20
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Please input your bet, e, your current money is: 100: 30
--+--+--+--+--+--+--+--+--+--+--+--+--+--
```

Two options
```shell script
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Player a's hand 1:

Cards: 
Spades♠--6 
Clubs️️️️♣--5 
Clubs️️️️♣--8 
Clubs️️️️♣--10 

Current Value: 29

Please take an action from the following options, a's hand 1 (Enter the number): 
1.Hit 2.Stand
d
Invalid input! Please input again a valid int number: 3
--+--+--+--+--+--+--+--+--+--+--+--+--+--
You can only choose from these three actions! Please input again: 
1.Hit 2.Stand
2
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Player a stand!
```

Player can choose if they want to quit the game.
```shell script
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Player a's hand 1 wins!
Player a win money: 100 + 30 = 130
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Player d's hand 1 wins!
Player d win money: 100 + 20 = 120
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Player e loses!
Player e lose money: 100 - 30 = 70
--+--+--+--+--+--+--+--+--+--+--+--+--+--
Player a's bank: 130
Player c's bank: 100
Player d's bank: 120
Player e's bank: 70
Dealer b's bank: 280
--+--+--+--+--+--+--+--+--+--+--+--+--+--
a, do you want to play next round? (Y/N) 
n
a quit.
d, do you want to play next round? (Y/N) 
y
e, do you want to play next round? (Y/N) 
```


### Contributor

U82144100

siyuzhou@bu.edu

Siyu Zhou


## License

[BU](LICENSE) © Siyu Zhou
