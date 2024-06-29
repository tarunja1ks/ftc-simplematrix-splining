# FTC SimpleMatrix Splining
This repository contains code for visualizing Quintic Bezier Splines. 

- The QSplinePath class generates a single spline, using matrices to solve for the coefficents of the polynomial.
- The App class contains the code that generates some splines that are then visualized. 
  - The parameters to the splines can be tweaked to adjust the splines with parameters being the initial time,position,velocity,acceleration then final time, position, velocity, acceleration
- The GraphPanel class simply displays the splines in a window over the field for the 2023-2024 FTC game
- The Vector2 class simply stores an x and y value
