# RegexTester
RegexTester is an Eclipse plugin which can be used to easily test regular expressions.

![Screenshot](https://raw.githubusercontent.com/sbrosinski/RegexTester/master/screenshots/regex2.png)

## Features

* Test and search for regular expression
* Matches are colorized, for an easy visual clue
* Support for pattern flags (e.g. Pattern.DOTALL)
* LiveEval evaluates your regular expression while you are typing it, gives feedback on possible errors and shows any matches automatically
* LiveEval is supported for changes of the regular expression, the search text and the pattern flags
* 4 distinct match modes:
  * Find a sequence of characters 
  * Match a complete text
  * Split text
  * Replace every occurence of the regex with a different string. Replacing supports back references ($1,$2,...)
* LiveEval for match mode changes
* Context sensitive "Regular Expression Assist"
* Selective evaluation of expressions
* Bracket Matching
* Generation of string literals based on the regexp, e.g. "\(x\)" becomes "\\(x\\)"
* De-escape patterns in your code, e.g. \\(x\\) becomes \(x\)
* Improved "Clear Menu", choose which parts of the view you would like to get cleared every time you press the clear button
* Easy movement through matches: Choose "Previous Match" or "Next Match" and cycle through all matches found.
* Polished and accessible user interface, everything is reachable via keyboard

## Installation

[Download the plugin jar](https://github.com/sbrosinski/RegexTester/downloads) and put it in your Eclipse plugin folder.
