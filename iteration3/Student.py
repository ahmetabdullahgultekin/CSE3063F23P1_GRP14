import logging
from iteration3.ConsoleColours import ConsoleColours
from iteration3.Course import Course
from iteration3.IDisplayMenu import IDisplayMenu
from iteration3.Person import Person


class Student(Person, IDisplayMenu):
    def __init__(self, studentID, name, surname, userName, password, semester):
        super().__init__(studentID, name, surname, userName, password)
        from iteration3.CourseRegistrationSystem import CourseRegistrationSystem
        self.__system = CourseRegistrationSystem()
        self.__draft = []
        self.__availableCoursesToAdd = []
        self.__availableCoursesToDrop = []
        self.__labSections = []
        self.__hasRequest = False
        self.__transcript = None
        self.__advisor = None
        self.__notification = None
        self.__semester = semester
        self.__schedule = [[None for _ in range(8)] for _ in range(5)]

    def sendRequest(self):
        """
        Sends a request for approval to the assigned advisor.

        If the student already has a request waiting for approval, it prints a warning message and does nothing.
        If the student's draft is empty, it prints a message and does nothing.
        Otherwise, it creates a new Registration object with the student and draft as parameters, adds the advisor to the request,
        and logs the event.

        Returns:
            None
        """
        ConsoleColours.paintRedMenu()
        if self.__hasRequest:
            print("You already have a request waiting for approval.")
            logging.warning(
                f"Student {self.getID()} already has a request waiting for {self.__advisor.getID()}'s approval.")
            ConsoleColours.resetColour()
        elif not self.__draft:
            print("Your draft is empty!")
            ConsoleColours.resetColour()
        else:
            from iteration3.Registration import Registration
            registration = Registration(self, self.__draft)
            registration.addRequest(self.__advisor)
            logging.info(f"Student {self.getID()} sent a request to advisor {self.__advisor.getID()}.")

    def printMenu(self, menuType):
        """
        Prints out the menu based on the given menuType.

        Parameters:
            menuType (str): The type of menu to be printed.

        Returns:
            None
        """
        match menuType:
            case "studentMenu":
                ConsoleColours.paintBlueMenu()
                print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                print(f"Welcome {self.getName()} {self.getSurname()}!")
                print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n")

                if self.__notification is not None:
                    ConsoleColours.paintYellowMenu()
                    print(self.__notification.message)
                    self.__notification = None
                    ConsoleColours.paintBlueMenu()

                print("Please select from the following options:")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                print("                         Exit System -> 0")
                print("                 Course Registration -> 1")
                print("                     View Transcript -> 2")
                print("                             Log out -> 3")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                ConsoleColours.paintGreenMenu()
                print("Enter your choice: ")

            case "courseRegistrationMenu":
                ConsoleColours.paintBlueMenu()
                print("Course Registration Menu")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n")
                print("Please select from the following options:")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                print("                   Back to Main Menu -> 0")
                print("                 Course Status Check -> 1")
                print("                 Add Course to Draft -> 2")
                print("            Delete Course from Draft -> 3")
                print("                 Show request status -> 4")
                print("                        Send Request -> 5")
                print("                             Log out -> 6")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                ConsoleColours.paintGreenMenu()
                print("Enter your choice: ")

            case "courseSelectionMenu":
                ConsoleColours.paintBlueMenu()
                print("Course Selection Menu")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n")
                print("Please select from the following options:")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                print("    Back to Course Registration Menu -> 0")
                print("                       Add Mandatory -> 1")
                print("              Add Technical Elective -> 2")
                print("          Add Non-Technical Elective -> 3")
                print("      Add Faculty Technical Elective -> 4")
                print("                         Drop Course -> 5")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                ConsoleColours.paintGreenMenu()
                print("Enter your choice: ")
            case "addCourse":
                ConsoleColours.paintBlueMenu()
                print("Add Course Menu")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n")
                print("      Back -> 0")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                print("Here is the available courses to add:")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                ConsoleColours.paintPurpleMenu()

            case "dropCourse":
                ConsoleColours.paintBlueMenu()
                print("Drop Course Menu")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n")
                print("       Back -> 0")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                print("Here is the available courses to drop:")
                print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                ConsoleColours.paintPurpleMenu()

    def addCourseToDraft(self):
        try:
            if self.__hasRequest:
                ConsoleColours.paintRedMenu()
                print("You can not add lecture because you have a request waiting for approval.")
                logging.warning(
                    f"Student {self.getID()} can not add lecture because he/she has a request waiting for {self.__advisor.getID()}'s approval.")
                return
            ConsoleColours.paintBlueMenu()
            self.printMenu("courseSelectionMenu")
            choice = self.__system.getInput()
            match choice:
                case 0:
                    return
                case 1:
                    self.addMandatoryCourse()
                case 2:
                    self.addTechnicalElective()
                case 3:
                    self.addNonTechnicalElective()
                case 4:
                    self.addFacultyTechnicalElective()
                case 5:
                    self.addCourseToDrop()
                case -1:
                    ConsoleColours.paintRedMenu()
                    print("Please enter valid number!")
                case _:
                    ConsoleColours.paintRedMenu()
                    print("Invalid input, please enter a valid number!")
            self.addCourseToDraft()
        except Exception as e:
            logging.error(f"An error occurred while adding course to draft: {e}")
            print("Something went wrong. Please try again..")
            self.addCourseToDraft()

    def addMandatoryCourse(self):
        if self.maxCoursesReached():
            return
        self.computeAvailableMandatoryCourses()

        if not self.__availableCoursesToAdd:
            ConsoleColours.paintRedMenu()
            print("You do not have any addable course!")
        else:
            self.printMenu("addCourse")
            for i, course in enumerate(self.__availableCoursesToAdd):
                print(
                    f"{i + 1}. {course.getCourseCode()} - {course.getCourseName()} - {course.getDay()} - {course.getHour()}.00")
                print("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´")

            ConsoleColours.paintGreenMenu()
            print(f"Choose number between 1 to {len(self.__availableCoursesToAdd)} to add course: \n")
            userNumberInput1 = self.__system.getInput()

            if 1 <= userNumberInput1 <= len(self.__availableCoursesToAdd):
                self.chooseLabSection(self.__availableCoursesToAdd[userNumberInput1 - 1])
                logging.info(
                    f"Student {self.getID()} added {self.__availableCoursesToAdd[userNumberInput1 - 1].getCourseCode()} to draft.")
                self.__draft.append(self.__availableCoursesToAdd[userNumberInput1 - 1])
                del self.__availableCoursesToAdd[userNumberInput1 - 1]
                if self.__availableCoursesToAdd:
                    self.addMandatoryCourse()
            elif userNumberInput1 > len(self.__availableCoursesToAdd) or userNumberInput1 < 0:
                ConsoleColours.paintRedMenu()
                logging.warning(f"Student {self.getID()} entered invalid input.")
                print("Invalid input, please enter a valid number")
                self.addMandatoryCourse()
            else:
                return

    def chooseLabSection(self, course):
        if not course.getLaboratorySections():
            return
        availableLabSections = []
        ConsoleColours.paintBlueMenu()
        print("Here is the available laboratory sections to add:")
        ConsoleColours.paintPurpleMenu()
        for labSection in course.getLaboratorySections():
            labCourse = Course(None, labSection.getLaboratorySectionCode(), None, 0, 0, 0, labSection.getHour(),
                               labSection.getDay())
            if self.hasCourseOverlap(labCourse, True) or labSection.getCapacity() <= labSection.getNumberOfStudents():
                continue
            else:
                availableLabSections.append(labSection)

        for i, labSection in enumerate(availableLabSections):
            print(
                f"{i + 1}. {labSection.getLaboratorySectionCode()} - {course.getCourseName()} (Lab) - {labSection.getDay()} - {labSection.getHour()}.00")
            print("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´")
        ConsoleColours.paintGreenMenu()
        print(f"Choose number between 1 to {len(availableLabSections)} to add laboratory section: \n")
        userNumberInput = self.__system.getInput()

        if 1 <= userNumberInput <= len(availableLabSections):
            self.__labSections.append(availableLabSections[userNumberInput - 1])
            availableLabSections[userNumberInput - 1].setNumberOfStudents(
                availableLabSections[userNumberInput - 1].getNumberOfStudents() + 1)
        elif userNumberInput > len(availableLabSections) or userNumberInput <= 0:
            ConsoleColours.paintRedMenu()
            print("Invalid input, please enter a valid number")
            ConsoleColours.resetColour()
            self.chooseLabSection(course)
        else:
            return

    def addTechnicalElective(self):
        if self.maxCoursesReached():
            return
        self.computeAvailableTEAndFTECourses("TE")

        if not self.__availableCoursesToAdd:
            ConsoleColours.paintRedMenu()
            print("You do not have any addable course!")
        else:
            self.printMenu("addCourse")
            for i, course in enumerate(self.__availableCoursesToAdd):
                print(
                    f"{i + 1}. {course.getCourseCode()} - {course.getCourseName()} - {course.getDay()} - {course.getHour()}.00")
                print("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´")

            ConsoleColours.paintGreenMenu()
            print(f"Choose number between 1 to {len(self.__availableCoursesToAdd)} to add course: \n")
            userNumberInput1 = self.__system.getInput()

            if 1 <= userNumberInput1 <= len(self.__availableCoursesToAdd):
                self.__draft.append(self.__availableCoursesToAdd[userNumberInput1 - 1])
                logging.info(
                    f"Student {self.getID()} added {self.__availableCoursesToAdd[userNumberInput1 - 1].getCourseCode()} to draft.")
                del self.__availableCoursesToAdd[userNumberInput1 - 1]
                if self.__availableCoursesToAdd:
                    self.addTechnicalElective()
            elif userNumberInput1 > len(self.__availableCoursesToAdd) or userNumberInput1 < 0:
                ConsoleColours.paintRedMenu()
                print("Invalid input, please enter a valid number")
                self.addTechnicalElective()
            else:
                return

    def computeAvailableTEAndFTECourses(self, courseType):
        self.__availableCoursesToAdd.clear()
        sum = 0
        if self.__transcript:
            mapGrade = self.__transcript.getCourseGradeMap()
            studentCourses = self.__transcript.getStudentCourses()
            if studentCourses:
                for course in studentCourses:
                    grades = mapGrade.get(course, [])
                    lastGrade = grades[-1] if grades else None
                    if (course.getSemester() > 6
                            or course.getCourseType() == "NTE"
                            or lastGrade is None
                            or lastGrade.getLetterGrade() is None
                            or lastGrade.getLetterGrade() > "DD"):
                        continue
                    sum += course.getCourseCredit()

        if sum >= 155:
            for course in self.getDepartment().getCourses():
                if (course.getCourseType() != courseType
                        or not course.hasCapacity()
                        or self.hasCourseOverlap(course, False)
                        or (self.__semester < course.getSemester() and self.__transcript.getCgpa() < 3)
                        or course in self.__draft
                        or not self.checkThePrerequisiteAndCourseThatWasTaken(course)
                        or (course.getSemester() % 2 != self.__semester % 2)):
                    continue
                else:
                    self.__availableCoursesToAdd.append(course)

    def addFacultyTechnicalElective(self):
        if self.maxCoursesReached():
            return
        self.computeAvailableTEAndFTECourses("FTE")

        if not self.__availableCoursesToAdd:
            ConsoleColours.paintRedMenu()
            print("You do not have any addable course!")
        else:
            self.printMenu("addCourse")
            for i, course in enumerate(self.__availableCoursesToAdd):
                print(
                    f"{i + 1}. {course.getCourseCode()} - {course.getCourseName()} - {course.getDay()} - {course.getHour()}.00")
                print("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´")

            ConsoleColours.paintGreenMenu()
            print(f"Choose number between 1 to {len(self.__availableCoursesToAdd)} to add course: \n")
            userNumberInput1 = self.__system.getInput()

            if 1 <= userNumberInput1 <= len(self.__availableCoursesToAdd):
                self.__draft.append(self.__availableCoursesToAdd[userNumberInput1 - 1])
                logging.info(
                    f"Student {self.getID()} added {self.__availableCoursesToAdd[userNumberInput1 - 1].getCourseCode()} to draft.")
                del self.__availableCoursesToAdd[userNumberInput1 - 1]
                if self.__availableCoursesToAdd:
                    self.addFacultyTechnicalElective()
            elif userNumberInput1 > len(self.__availableCoursesToAdd) or userNumberInput1 < 0:
                ConsoleColours.paintRedMenu()
                print("Invalid input, please enter a valid number")
                self.addFacultyTechnicalElective()
            else:
                return