# TypingThrower
### Author
**Chawakorn Suphepre(@winChawakorn) and Vittunyuta Maeprasart(@aommoaGitHub)**

Documentation : <a href="https://winchawakorn.github.io/TypingThrower/">https://winchawakorn.github.io/TypingThrower/</a>

TypingThrower project is a game. This typing game can calculate and record your typing speed in WPM (words per minutes). 2 players will fight with each other by typing the apparent word.
Correct typing will damage to your opponent. You will win when your opponent lost all HP. This game can improve your typing skill.

## *** NEW GAME VERSION HAS RELEASED, PLEASE UPDATE THE GAME.  2017/06/05 ***
<a href="https://github.com/winChawakorn/TypingThrower/raw/master/TypingThrower.jar"> >>>Download this Game</a> Run `TypingThrower.jar` and have fun!</br></br>

## *** NEW SERVER VERSION HAS RELEASED. THE SERVER MUST BE THE SAME VERSION AS THE GAME.  2017/06/05 ***
If our server is closed or you would like to open your own server, you can  
<a href="https://github.com/winChawakorn/TypingThrower/raw/master/Server.jar"> >>>Download the server for Typing Thrower</a> and run `Server.jar` in the terminal, cmd, or etc.</br>
To change the server, you have to change the IP address in `MainFrame` to your server's IP address.</br>
### What's new
- Server and client will communicate each others by using `char` for most case. So that you can't play the online mode in the old version. This can increase the communication speed.<br>
- Server can show the time of every activities.

## UML Diagram for this project:
![UML Diagram](http://i.imgur.com/cDJSst7.jpg)


## How to play

1. The first page is a login page. You can press **Practice** button to play with a robot. (YOUR SCORE WILL NOT RECORDED)

**login page**<br>
![Login Page](http://i.imgur.com/zYyOD7T.png)<br>
play with bot by pressing **Practice** button.<br>
![Practice Page](http://i.imgur.com/JLtBk7y.png) <br>
![Score practice](http://i.imgur.com/DNrjRSY.png) <br>

2. If you don't have any account, you can press **Sign Up** button to create a new account.

**sign up page**<br>
![Sign up Page](http://i.imgur.com/1fMNHAG.png)


3. While logging in, please wait half a moment. After logging in is success, you will go to the home page that contains 4 buttons on the left of the screen.
* **Online:** Play in online mode with another player who is finding a game room like you. This mode will record your score in the end of the game.
* **Offline:** Play in offline mode with a robot and record the score.
* **Logout:** Logout from this account and go back to login page.
* **Quit:** Close the application.

**home page**<br>
![Home Page](http://i.imgur.com/7JvzDb8.png)


4. In online mode. You will see **Waiting** page while you are waiting for another player to join your game. When another player enter your game, the game will start!

**waiting page**<br>
![Waiting Page](http://i.imgur.com/TOuj2Uw.png)
**In online game**<br>
![Play Online](http://i.imgur.com/0JV0J86.png)
**Finish race**<br>
![Finish Race](http://i.imgur.com/Gu8YPeM.png)



5. After playing, your score will be updated.

The score is showed on the right side of the home page.<br>
![Home Score](http://i.imgur.com/68U8HOS.png)

## Interesting technology
### SQL Database
1. Google cloud platform (SQL)
2. Chrome MySQL Admin
3. ORMLite
### OCSF for game server
We use OCSF to send the object between server and client to update the detail in the game.

Picture from : <a href="http://www.gameart2d.com/">http://www.gameart2d.com/</a>
