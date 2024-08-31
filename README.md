# Stock Management

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/EltonC06/Stock-Management/blob/main/LICENSE)

## About the project

Stock Management is a back-end application developed for educational purposes.

The application allows users to manage their stocks, by recording initial investments and tracking the current value to calculate profits.

## Technologies Used

### Back-End
- Java
- MySQL

## How to Run the Project

### Prerequisites
- IDE de Java (Eclipse recommended)
- Java 17
- MySQL

### Step-by-Step

1. **Clone Repository**
   ```bash
   git clone git@github.com:EltonC06/Stock-Management.git
   ```

2. **Open the Project**
   - Open the project in a Java IDE (tested only in Eclipse).

3. **Configure Database**
   - Create a MySQL database and configure your credentials. (database URL, user, password) on project configuration file: db.properties.

4. **Run the Program**
   - Go to `src -> application -> Program` and run the program.

### Observations

   - The program was only tested on Windows
   - To modify the storage location of CSV records, go to: `src -> entities -> CsvLink` and change the `csvPathLink` method to the desired path.

## How You Can Contribute

- Improve data input validation to prevent errors.
- Improve functionality to export data to a CSV file.
- Implement a graphical interface to display the invested stocks and their profits.

## Author

Elton da Costa Oliveira

[LinkedIn](https://www.linkedin.com/in/elton-da-costa/)
