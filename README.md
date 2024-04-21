# Singleton Pattern Telegram Bot

## Project Description

This project is a simple Telegram bot designed to demonstrate the implementation of the Singleton pattern in a practical context. One such example is the `Dispatcher` class, which is responsible for routing user requests. This class is created as a Singleton to ensure that there is only one Dispatcher instance throughout the application since it manages request routing centrally. The source code includes comments that explain the functionality of each class. In the directory `src/main/java/com/polishchuk/singleton`, you can find classes that do not carry any logic for the project itself but are implemented strictly according to a flowchart to demonstrate what is essential for implementing the Singleton pattern.

## How to Run the Project

To run this project, open it in IntelliJ IDEA, which will automatically download all the dependencies. You can then run the project directly from the IDE. The bot supports two commands:
- `/start` to initiate interaction.
- `/generate "min number" "max number"` to generate a random number within a specified range, for example, `/generate 4 92`. If no range is specified, it will generate a random number from 0 to 100.

## src/main/java/com/polishchuk/singleton

This directory contains classes structured to demonstrate the implementation specifics of the Singleton pattern, following a clear and educational schema.

## Requirements

This project is built using Java and requires an environment capable of running Java applications, such as IntelliJ IDEA for ease of setup and execution.
