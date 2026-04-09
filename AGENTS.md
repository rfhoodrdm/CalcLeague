

# App description
CalcLeague is an app to help with basic arithmetic practice.

## App features
- Offers

## Gui description
- Has the following Screens: 
  1) Splash screen. Displayed when first loaded. 
  2) High score screen
  3) Exercise screen, where the user solves math problems.
  4) Progress tracking screen, showings graphs. Options include history time to completion, number of questions correctly answered, and total score. 
  5) Summary screen, showing results of the latest exercise when the exercise is over. Displays time to completion, and accuracy in terms of number answered correctly.
- Exercise screen shows:
    1) Numbers for the current question being asked.
    2) Answer box. Focus goes to answer box so user doesn't have to click; only type their answer.
        a) The answer box is situated according to the type of exercise performed. E.g. vertically below the addends for addition. Similar layout for subtraction and multiplication. Division would have quotient in center, with divisor on left, and dividend above.
        b) Answer box is right-justified. 
    3) Question # out of current set. 
    4) Button to quit and return to the splash screen.
- You may choose a color scheme that is easy on the eyes.  
- Splash screen has:
    1) the logo image. I'll create a logo and store it in src/main/resources/images/logo.png 
    2) Selector for type of exercise.
    3) Selector for type of level.
    4) Start button
    5) Navigation to go to high score screen or historical progress screen.
- Historical progress screen has:
    1) Large graph that dominates top of screen and displays line graph data from the historic data file.
    2) Navigation to return to the splash screen or go to the high score screen.
- High score screen has:
    1) Large displayed list of high score records.
    2) Navigation to go to the historic data screen or the splash screen.
- Summary/results screen has:
    1) Widgets to display current selected activity, current selected level, time to completion, accuracy score, and total score
    2) Navigation to return to splash screen.


## Coding style and decisions
- Use Swing for the user interface.
- Stores high scores for each exercise type in a JSON file. Top ten scores in order. Each entry has the score, the user's name, and a date achieved.
- Stores a history log of level, exercise type, time, accuracy and score in a historic data file for the graph page.
- User is given one chance to answer a question. If wrong, then we move on to the next question. 
- Probably easiest to pick numbers for the whole division case: select factors as if multiplying, find the answer, then show the problem as the product divided by one of the factors.
- Sets of questions have 25 questions each.
- Exercises include the following, with multiple difficulty levels:
  1) Addition: Uses positive numbers only
    a) Level 1: 2 digit plus 2 digit
    b) Level 2: 3 digit plus 2 digit
    c) Level 3: 3 digit plus 3 digit
  2) Subtraction. All answers are nonnegative.
    a) Level 1: 2 digit minus 2 digit
    b) Level 2: 3 digit minus 3 digit
    c) Level 3: 3 digit minus 3 digit
  3) Multiplication: Uses positive numbers only.
    a) Level 1: 2 digit times 1 digit
    b) Level 2: 2 digit times 2 digit, but no factor greater than 20.
    c) Level 3: 2 or 3 digits times 2 digits.
  4) Whole division Uses positive numbers only.
    a) Level 1: 2 digit divided by 1 digit.
    b) Level 2: 3 digit divided by 1 or 2 digits.
    c) Level 3: 3 or 4 digits divided by 1 or 2 digits.
- Put configuration choices in config files in the config package.
- Put GUI interface classes in the gui package.
- file storage service goes in its own package, to get the high score and historic data files.
- We want to utilize as much screen space as available for the GUI, but not go full screen exclusive mode. 
- Score calculation service should also go into its own package. This is for calculating the total score awarded for a level. If there is no current score formula, you may indulge yourself, but I reserve the right to change it later. Your formula should take into the following:
    1) Points awarded for each correct answer.
    2) More points are awarded for correct answers for higher difficulty levels.
- Update the high score table and historic data file after each exercise is completed.

