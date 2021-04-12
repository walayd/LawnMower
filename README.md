
# Lawnmower program

This is a potential solution for the Lawnmower program described here: [lawnmower_problem.pdf](lawnmower_problem.pdf)

- [Lawnmower program](#lawnmower-program)
   * [Design choices](#design-choices)
      + [System interface definition](#system-interface-definition)
      + [Data model and operations definition](#data-model-and-operations-definition)
      + [Parallel programming](#parallel-programming)
      + [Tests](#tests)
   * [Build](#build)
      + [Build the program](#build-the-program)
   * [Run](#run)
      + [Run the program](#run-the-program)
      + [Run the unit tests](#run-the-unit-tests)

## Design choices

### System interface definition
I started by defining what operations (APIs) are expected from the system. This will establish the exact contract expected from the system and ensure if we haven’t gotten any requirements wrong

1. My **input** is supposed to be a well formatted entry **(I do not handle file verification**), + a path to where we want to store our output result.


	  ```
	    5 5
	    1 2 N
	    LFLFLFLFF
	    3 3 E
	    FFRFFRFRRF
	```

2. My **output** is supposed to be a well formatted output txt file stored in the path given by input.
   The output is the different mowers final positions and orientation

     ```
   1 3 N
   5 1 E
   ```

### Data model and operations definition
Defining the data model in the early part clarified for me how data will flow between different components

**Domain**
-  Mower:
   - Data : `Orientation`, `Point` (position)
   - Operations : `moveForward`, `turnLeft`, `turnRight`  and  Knows `actualPosition` and `nextPosition` if moves forward
-  Lawn:
   - Data : Has a `Map` that contains each Point and its state (occupied or free) and a `Size`, represented by the furthest point
   - Operations : can `Occupy` and `Free` a given point, knows if a given point `isInsideLawn` or not.
-  Action:
   - Operations : can `execute()` a given action. It is a **Command Design Pattern**, behavioral design pattern that turns a request into a stand-alone object that contains all information about the request. It lets us pass requests as a method arguments, delay or queue a request’s execution


**Controllers**
-  ParserController: takes a file input, output path and gives:
   - `lawnMaxPoint` which represents the further point (size of the lawn)
   - `actionQueues` which is a list of Queues, and each Queue contains Actions of a given mower
   - `outputResults()` create a file with the output results
-  LawnController: takes a file input and outputs
   - `initializeMowersPositions()` occupy the first positions
   - `setActionsQueues()` set ActionQueues to be executed
   - `runSequentially()` execute Actions sequentially
   - `runInParallel()` divide workload (Queues of Actions) to separate Threads and execute Actions
   - `getState()` return mowers positions in the Lawn

**Why?**

The goal is to respect POO Design principles, have less coupling and separate my objects domain and the behaviour implemented. It helps to keep code simple, testable and maintainable.

### Parallel programming
Critical section is any piece of code that has the possibility of being executed concurrently by more than one thread of the application and exposes any shared data or resources used by the application for access. In my case, it was the `lawn map ressource` I used a mutex to lock when doing reading/writing to synchronize threads and avoid race conditions.

The tasks were divided by chunks (Queues of actions) to multiple threads, to execute actions.

It can be improved since we can share reading as well in case of a huge file.


### Tests
I believe in TDD (Test Driven Development) even if it is hard to apply, I started by defining my POJOs methods, then the tests skull and I came back to develop my POJOs methods. It protect my application from breaking in case of refactor or adding new features. I used `RepeatableTest` to check my multithreading behaviour, if it has idempotent property

## Build
This project comes with Maven Wrapper, an excellent choice for users that don't want to install Maven

### Build the program
Go to the root directory and run

**under Unix system**

    ./mvnw clean install


**under Windows**

    ./mvnw.cmd clean install


## Run


### Run the program

**under Unix system**

    ./mvnw clean compile exec:java mvn exec:java -Dexec.args="arg0 arg1 arg2"


**under Windows**

    mvnw.cmd clean compile exec:java mvn exec:java -Dexec.args="arg0 arg1 arg2"

eg 
`mvnw.cmd clean compile exec:java -Dexec.args="C:/Users/wli7/Documents/test.txt C:/Users/wli7/Documents/result2.txt 5"`

**options**
| option | Description |
|--|--|
| arg0  | If run in a workspace folder, build all the modules of all the frameworks of the current workspace, or if run in a framework folder, build all the modules of this framework. |
| arg1  | fully qualified name (path + filename) to the output  |
| arg2  | Number of thread that you wish to execute  |


### Run the unit tests

**under Unix system**

    ./mvnw test


**under Windows**

    ./mvnw.cmd test



