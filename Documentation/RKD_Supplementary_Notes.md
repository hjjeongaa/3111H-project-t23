## Supplementary Notes for Task 1, Task 5, Settings, and the UI

Author: Ryder Khoi Daniel

#### Task 1: Top N Names

Here is the how task 1 is used. You can select which year you would like to investigate using a slider, and a label on top of the table on the right also tells you which year you are looking at. There is also a brief description regarding who held first and second place the most in the range of years you specified.

![Task 1 Demo Usage](http://placekitten.com/200/300 "Task 1 in use")

### Task 5: Soulmate Name

The way this task is meant to be used is you input your name and preferences, year of birth, etc., and then when you press the find soulmate button, the four tables are populated with names of potential soulmates based off of four algorithms: NK-T5, Closest Name, Probably Your Classmate, and Chance Encounter. There are details on the algorithms on the application, you just have to click the title of the algorithm you are interested in reading about.
For the NK-T5, you can also specify how you want rank to be defined (ordinal ranking by default). The program supports Ordinal Ranking, Standard Competition Ranking, Modified Competition ranking, and Dense Ranking. (More info about what these rankings mean can be found [here](https://en.wikipedia.org/wiki/Ranking).)

![Task 5 Demo Usage](http://placekitten.com/200/300 "Task 5 in use")

After the names have been generated, the table on the right is populated with all the names seen from the tables on the left. Choose one you would like to go on a journey through time with...

#### The WOW Factor

The journey through time is the WOW factor of this task. It is designed to be an educational journey to learn more about history, and maybe some neat patterns between you and your potential soulmate.

![Task 5 WOW Demo](http://placekitten.com/200/300 "Task 5 WOW in use")

One limitation of this WOW factor is the difficulty in testing. Due to the nature of the animations, getting JUnit to do tests was very difficult. I have tested lots of cases by hand, however this is not refelected in the coverage report, thus this is the limitation of the WOW factor, and my testing ability.

### Settings

The settings page is where you can go to try out different data sets scraped by our team. When you are done selecting the nation of interest, pages for task 1,2,4,5 will automatically load from that data set.

### The User Interface

This is another WOW factor for this project. The UI attempts to mimic a swing style interface. This was to achieve a smooth clean appearance which I believe this program pulled off.
The way that the user interface works is by having a parent controller and fxml file, this is the main interface. Within this main interface, other fxml files and controllers are hosted within. Allowing you, the user to seamlessly traverse between tasks, have the tasks be persistent on their own page.
![UI](http://placekitten.com/200/300 "UI showcase")
