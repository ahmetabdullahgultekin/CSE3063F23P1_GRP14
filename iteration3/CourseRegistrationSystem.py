import logging
import sys

from iteration3.Advisor import Advisor
from iteration3.ConsoleColours import ConsoleColours
from iteration3.IDisplayMenu import IDisplayMenu
from iteration3.JSONReader import JSONReader
from iteration3.JSONWriter import JSONWriter
from iteration3.Student import Student
from iteration3.University import University


class CourseRegistrationSystem(IDisplayMenu):

    def __init__(self):
        self.__university = University("Marmara University")
        self.__input = ""
        self.__choice = 0

    def start(self):
        jsonReader = JSONReader()
        jsonReader.readDepartments(self.__university)
        for department in self.__university.getDepartments():
            jsonReader.start(department)
        self.mainMenu()

    def mainMenu(self):
        try:
            # Print the main menu options.
            self.printMenu("mainMenu")

            # Get the user's choice.
            self.__choice = self.getInput()

            match self.__choice:
                case 0:
                    logging.info("User exited the system.")
                    # If the user chooses to exit, print a message and terminate the program.
                    self.exitProgram()
                    # Terminate
                    return
                case 1:
                    logging.info("User navigated to the login page.")
                    # If the user chooses to navigate to the login page, call the loginMenu method.
                    self.loginMenu()
                case -1:
                    raise IndexError("Please enter valid number!")
                case _:
                    raise IndexError("Invalid choice! Please select again!")
            self.mainMenu()
        except IndexError as e:
            ConsoleColours.paintRedMenu()
            print(e.args[0])
            self.mainMenu()

    def loginMenu(self):
        try:
            ConsoleColours.paintBlueMenu()
            print("Login Page")
            print("¨¨¨¨¨¨¨¨¨¨\n")
            print(" Back -> 0")
            print("¨¨¨¨¨¨¨¨¨¨")

            ConsoleColours.paintGreenMenu()
            print("Please enter your username: ")
            userName = input()

            # User selected to go back
            if userName == "0":
                self.mainMenu()
                return

            print("Enter your password: ")
            password = input()
            ConsoleColours.paintBlueMenu()

            person = self.__university.getUserNamePersonMap().get(userName)
            if person is not None:  # Check if there is such user
                if person.login(userName, password):
                    logging.info(f"{person.getID()} {person.getName()} {person.getSurname()} logged in.")
                    if isinstance(person, Student):
                        self.studentMenu(person)
                    elif isinstance(person, Advisor):
                        self.advisorMenu(person)
                else:
                    ConsoleColours.paintRedMenu()
                    logging.warning(
                        f"User entered an invalid username or password. -> Username: {userName} Password: {password}")
                    print("Username or password is incorrect. Please try again!")
                    self.loginMenu()
            else:
                ConsoleColours.paintRedMenu()
                logging.warning(
                    f"User entered an invalid username or password. -> Username: {userName} Password: {password}")
                print("Username or password is incorrect. Please try again!")
                self.loginMenu()

        except KeyboardInterrupt:
            ConsoleColours.paintRedMenu()
            print("Program terminated by the user.")
            ConsoleColours.resetColour()
            exit(0)

        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in loginMenu method.")
            ConsoleColours.resetColour()
            self.loginMenu()

    def studentMenu(self, student):
        try:
            student.printMenu("studentMenu")
            self.__choice = self.getInput()

            match self.__choice:
                case 0:
                    logging.info(f"Student {student.getName()} {student.getSurname()} exited the system.")
                    self.exitProgram()
                    return
                case 1:
                    self.courseRegistrationMenu(student)
                case 2:
                    student.getTranscript().showTranscript()
                case 3:
                    logging.info(f"Student {student.getName()} {student.getSurname()} logged out.")
                    self.loginMenu()
                    return
                case -1:
                    raise IndexError("Please enter valid number!")
                case _:
                    raise IndexError("Invalid choice! Please select again!")
            self.studentMenu(student)

        except IndexError as e:
            ConsoleColours.paintRedMenu()
            print(e.args[0])
            self.studentMenu(student)

    def courseRegistrationMenu(self, student):
        try:
            student.printMenu("courseRegistrationMenu")
            self.__choice = self.getInput()

            match self.__choice:
                case 0:
                    return
                case 1:
                    student.getTranscript().courseStatusCheck()
                case 2:
                    student.addCourseToDraft()
                case 3:
                    student.removeCourseFromDraft()
                case 4:
                    student.showRequestStatus()
                case 5:
                    student.sendRequest()
                case 6:
                    logging.info(f"Student {student.getName()} {student.getSurname()} logged out.")
                    self.loginMenu()
                    return
                case -1:
                    raise IndexError("Please enter valid number!")
                case _:
                    raise IndexError("Invalid choice! Please select again!")
            self.courseRegistrationMenu(student)

        except IndexError as e:
            ConsoleColours.paintRedMenu()
            print(e.args[0])
            self.courseRegistrationMenu(student)

    def advisorMenu(self, advisor):
        try:
            advisor.printMenu("advisorMenu")
            self.__choice = self.getInput()

            match self.__choice:
                case 0:
                    logging.info(f"Advisor {advisor.getName()} {advisor.getSurname()} exited the system.")
                    self.exitProgram()
                    return
                case 1:
                    advisor.printRequests()
                case 2:
                    logging.info(f"Advisor {advisor.getName()} {advisor.getSurname()} logged out.")
                    self.loginMenu()
                    return
                case -1:
                    raise IndexError("Please enter valid number!")
                case _:
                    raise IndexError("Invalid choice! Please select again!")
            self.advisorMenu(advisor)

        except IndexError as e:
            ConsoleColours.paintRedMenu()
            print(e.args[0])
            self.advisorMenu(advisor)

    def getInput(self):
        user_input = None
        try:
            user_input = input()
            self.__choice = int(user_input)
            if user_input != str(self.__choice):
                raise ValueError
        except KeyboardInterrupt as e:
            ConsoleColours.paintRedMenu()
            print("Program terminated by the user.")
            ConsoleColours.resetColour()
            exit(0)
        except Exception as e:
            ConsoleColours.paintRedMenu()
            print("Invalid input, please do not enter a nonNumeric input!")
            logging.error(f"User entered an invalid input. -> Input: {user_input}")
            return -1
        return self.__choice

    def exitProgram(self):
        ConsoleColours.paintYellowMenu()
        print("Exiting from system...")

        # Create a new instance.
        # Assuming 'JSONWriter' is a custom class for JSON serialization,
        # replace it with appropriate Python json handling
        jsonWriter = JSONWriter()

        # Write the current state of the university to JSON files.
        jsonWriter.start(self.__university)

        logging.info("User exited the system.")

        # Terminate the program.
        sys.exit(0)

    def printMenu(self, menuType):
        ConsoleColours.paintBlueMenu()
        print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        print("  Marmara University Course Registration System  ")
        print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n")
        print("Please select from the following options:")
        print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
        print("                                Exit -> 0")
        print("                          Login Page -> 1")
        print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
        ConsoleColours.paintGreenMenu()
        print("Enter your choice: ")

    def addToSchedule(self, course, student):
        i, j = 0, 0
        day_to_index = {"Monday": 0, "Tuesday": 1, "Wednesday": 2, "Thursday": 3, "Friday": 4}
        hour_to_index = {8: 0, 9: 1, 10: 2, 11: 3, 13: 4, 14: 5, 15: 6, 16: 7}

        i = day_to_index.get(course.getDay())
        j = hour_to_index.get(int(course.getHour()))

        student.getSchedule()[i][j] = course

    def removeFromSchedule(self, course, student):
        i, j = 0, 0
        day_to_index = {"Monday": 0, "Tuesday": 1, "Wednesday": 2, "Thursday": 3, "Friday": 4}
        hour_to_index = {8: 0, 9: 1, 10: 2, 11: 3, 13: 4, 14: 5, 15: 6, 16: 7}

        i = day_to_index.get(course.getDay())
        j = hour_to_index.get(int(course.getHour()))

        student.getSchedule()[i][j] = None
