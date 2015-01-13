# Red Run
Current Version: 1.0

#Authors
* Troy Squillaci <zivia@unm.edu>
* Jake Nichol <jjaken@unm.edu>
* Jayson Grace <jaysong@unm.edu>
* Adam Mitchell <adam@unm.edu>
* Jordan Medlock <medlock@unm.edu>

#Project Description

RedRun is a first-person multiplayer game in which a group of victims attempt to make it to the end of a deadly obstacle course littered with traps. Players choose the role of good or evil in the form of victims and villains. Victims are part of team blue and try to trick the villains into springing the traps at the wrong time. Villains are part of team red and try to outwit the victims by springing the traps at the right time. If even a single victim makes it to the end of the obstacle course unscathed the blue team wins. If the villains eliminate all of the victims then the red team wins.

#How do I get set up?

##For instructors/graders:

1. Create a new project in Eclipse
2. Unzip the submitted archive and import the files into the project you created in step 1
3. Be sure to set up your ip address appropriately in Main.java, if you are using external machines
4. All of the dependencies are in the lib folder and need to be put in the build path
5. In order to configure LWJGL, be sure to set up your native path as per these instructions:
Go to your LWJGL folder that contains the folders named "jar", "res", "doc", and "native". You need to go into Eclipse (assuming you use eclipse), open your project in the Project Explorer on the left side of your screen.
Right click on the "JRE System Library" of your project, and click "Build Path" -> "Configure Build Path".
Include the LWJGL native libraries to your project in the Build Path Configurer by clicking the "Native library location" which can be seen in the JRE System Library dropdown menu.
Click on "Edit...", which will be the only button clickable in that general area.
A file explorer will pop up. Navigate to the location of your LWJGL native folder (The location should be something like "C:\Users\YOURUSERNAMEHERE\Desktop\Java\eclipse\lwjgl-2.9.0\native" if you are using Windows) and include the folder named [Your OS here].
6. Start Server.java
7. Start Main.java on any client machines you want to connect

# Game Instructions #
* The first player to join is the villain, the rest are the victims
* Press the Esc key to view the menu which will help you get familiar with the controls for the game
* There can be up to 5 players total

# Miscellaneous - Database #

## Migrate Database ##
* Ruby must be installed for this to work!

1. cd to the redrun parent folder

* If database already exists and you want to clear all tables:
rake db:migrate --VERSION=0 

* If you want to create all tables and the database does not currently exist:
rake db:migrate

## Populate Database with Map Input File##

1. Build text file formatted similarly to the one found at  res/maps/IceWorld.txt

* The first line must be the map, followed by a blank line, then followed by the list of mapobjects

* Run MapObjectTextToDB.java with the text file created to populate database