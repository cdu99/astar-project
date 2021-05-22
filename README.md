# Astar project

### Prerequisite

Your .co and .gr file must be at the root of the project and they must be correct

### How to run

```java fr.uge.info2.main.Main <FILENAME> <SOURCE> <DESTINATION>```  
Source and Destination arguments are optional.

### Result

The program will return the number of steps the algorithm did to find the solution, the length of the path from Source to Destination and the path computed shortest path from Source to Destination.  
If no Source or Destination are given as argument, the program will run the algorithm with Source = 1 and Destination = Number of 

### Performance comparison against Dijkstra

Using Astar:  
```java fr.uge.info2.main.Main USA-road-d.NY  6,34s user 0,27s system 137% cpu 4,797 total```  
Using Dijkstra:  
``java fr.uge.info2.main.Main USA-road-d.NY  125,38s user 0,21s system 101% cpu 2:03,89 total``  
Astar is much faster