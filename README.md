# Battleship - Earnest Challenge
## Overview 
This program supports a basic battleship board that can be set to any size. Ships of various sizes can be manually
added to the board with the createShip API. Any attempt to add a ship outside the board bounds will fail silently. Any
attempt to add a ship in a position where another ship already exists will fail silently. The board can be attacked
with the attack API.

Attacking a position that does not contain a ship will result in a "Miss" signal.
Attacking a position that contains a ship will result in either a "Hit", "Sunk", or "Win" signal.
    - A "Hit" means a ship was hit, but not sunk
    - A "Sunk" means all of a ship's positions are hit, but other ship's still remain unsunk
    - A "Win" means the last ship has been sunk, and there are no ships remaining
Attacking a position that has been attacked previously will result in a "Already Taken" signal.
Attacking a position outside the board boundaries will result in a "Invalid Position" signal.
