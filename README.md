# Mastermind
The objective of this program is to be able to break codes through the use of combinatorics.
The program will try to guess any code with n positions and m colors. It does this by recursively generating every combination of codes with these parameters. The program is also given two clues to work with, the number of correct positions and the number of incorrect positions but correct color, allowing it to eliminate codes in the sample space that are inconsistent with these clues given. It will continue requesting clues until it is able to eliminate the sample space down to one code. 

This program has one class, MasterMind.java.
