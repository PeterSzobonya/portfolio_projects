## Circle project

This assignment is about estimating the area of a unit circle inside a unit square. The way the area should be approximated is generating random points inside the unit square and check wheather the point is inside the circle or not. After the points are generated and checked the area is calculated by the proportion of points inside of the circle and the ones that are not. There are several stages of the excercise. 

The first stage is a single threaded program thats generates the points and checks if they are inside the circle. This is highly inefficient as we have to generate a lot of points to have the area as close to the real value as possible. 
In the next stage the program became multi-threaded, so it could execute the calculations simultaneously and this way the program executes faster. And can use the computers resources more efficiently. 
The third stage is about avoiding race condition and amploying a mutex to ensure that only one thread can acces the given workspace at the same time.
I created a fourth stage where I put several resources so the user can set the number of resources as well.

The picture below shows the amount of time it takes for the given stages to execute and estimate the area of the circle.

![screenshot of the circle project](../readme_pics/circle/progress.jpg) 