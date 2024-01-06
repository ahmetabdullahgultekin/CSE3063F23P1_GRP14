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
            ConsoleColours.paintRedMenu()
            logging.error(f"An error occurred while adding course to draft: {e}")
            logging.exception("An error occurred in addCourseToDraft method.")
            ConsoleColours.resetColour()

    def addMandatoryCourse(self):
        try:
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
        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in addMandatoryCourse method.")
            ConsoleColours.resetColour()

    def chooseLabSection(self, course):
        try:
            if not course.getLaboratorySections():
                return
            availableLabSections = []
            ConsoleColours.paintBlueMenu()
            print("Here is the available laboratory sections to add:")
            ConsoleColours.paintPurpleMenu()
            for labSection in course.getLaboratorySections():
                labCourse = Course(None, labSection.getLaboratorySectionCode(), None, 0, 0, 0, labSection.getHour(),
                                   labSection.getDay())
                if self.hasCourseOverlap(labCourse,
                                         True) or labSection.getCapacity() <= labSection.getNumberOfStudents():
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

        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in chooseLabSection method.")
            ConsoleColours.resetColour()

    def addTechnicalElective(self):
        try:
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
        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in addTechnicalElective method.")
            ConsoleColours.resetColour()

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
        try:
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
        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in addFacultyTechnicalElective method.")
            ConsoleColours.resetColour()

    def addNonTechnicalElective(self):
        try:
            if self.maxCoursesReached():
                return
            self.computeAvailableNTECourses()

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
                        self.addNonTechnicalElective()
                elif userNumberInput1 > len(self.__availableCoursesToAdd) or userNumberInput1 < 0:
                    ConsoleColours.paintRedMenu()
                    print("Invalid input, please enter a valid number")
                    self.addNonTechnicalElective()
                else:
                    return
        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in addNonTechnicalElective method.")
            ConsoleColours.resetColour()

    def computeAvailableNTECourses(self):
        self.__availableCoursesToAdd.clear()

        for course in self.getDepartment().getCourses():
            if (course.getCourseType() != "NTE"
                    or not course.hasCapacity()
                    or self.hasCourseOverlap(course, False)
                    or (self.__semester < course.getSemester() and self.__transcript.getCgpa() < 3)
                    or course in self.__draft
                    or not self.checkThePrerequisiteAndCourseThatWasTaken(course)
                    or (course.getSemester() % 2 != self.__semester % 2)):
                continue
            else:
                self.__availableCoursesToAdd.append(course)

    def computeAvailableMandatoryCourses(self):
        try:
            self.__availableCoursesToAdd.clear()

            for course in self.getDepartment().getCourses():
                if course.getCourseCode() == "CSE4297":
                    sum = 0
                    for studentCourse in self.__transcript.getStudentCourses():
                        if (studentCourse.getSemester() > 6 and
                                (course.getCourseType() in ["NTE", "TE", "FTE"])
                                or studentCourse.getCourseCode() in ["ISG121", "ISG122"]
                                or self.__transcript.getCourseGradeMap()[studentCourse][-1] is None
                                or self.__transcript.getCourseGradeMap()[studentCourse][-1].getLetterGrade() is None
                                or self.__transcript.getCourseGradeMap()[studentCourse][-1].getLetterGrade() > "DD"):
                            continue
                        sum += course.getCourseCredit()

                    if (sum < 165
                            or course.getCourseType() != "Mandatory"
                            or not course.hasCapacity()
                            or self.hasCourseOverlap(course, False)
                            or (self.__semester < course.getSemester() and self.__transcript.getCgpa() < 3)
                            or course in self.__draft
                            or not self.checkThePrerequisiteAndCourseThatWasTaken(course)
                            or (course.getSemester() % 2 != self.__semester % 2)):
                        continue
                    else:
                        self.__availableCoursesToAdd.append(course)
                else:
                    if (course.getCourseType() != "Mandatory"
                            or not course.hasCapacity()
                            or self.hasCourseOverlap(course, False)
                            or (self.__semester < course.getSemester() and self.__transcript.getCgpa() < 3)
                            or course in self.__draft
                            or not self.checkThePrerequisiteAndCourseThatWasTaken(course)
                            or (course.getSemester() % 2 != self.__semester % 2)):
                        continue
                    else:
                        self.__availableCoursesToAdd.append(course)

        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in computeAvailableMandatoryCourses method.")
            ConsoleColours.resetColour()

    def addCourseToDrop(self):
        self.computeAvailableCoursesToDrop()

        if not self.__availableCoursesToDrop:
            ConsoleColours.paintRedMenu()
            print("You do not have any droppable course!")
        else:
            self.printMenu("dropCourse")
            for i, course in enumerate(self.__availableCoursesToDrop):
                print(
                    f"{i + 1}. {course.getCourseCode()} - {course.getCourseName()} - {course.getDay()} - {course.getHour()}.00")
                print("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´")

            ConsoleColours.paintGreenMenu()
            print(f"Choose number between 1 to {len(self.__availableCoursesToDrop)} to add course: \n")
            userNumberInput = self.__system.getInput()

            if 1 <= userNumberInput <= len(self.__availableCoursesToDrop):
                self.__draft.append(self.__availableCoursesToDrop[userNumberInput - 1])
                logging.info(
                    f"Student {self.getID()} added {self.__availableCoursesToDrop[userNumberInput - 1].getCourseCode()} to draft.")
                del self.__availableCoursesToDrop[userNumberInput - 1]
                if self.__availableCoursesToDrop:
                    self.addCourseToDrop()
            elif userNumberInput > len(self.__availableCoursesToDrop) or userNumberInput < 0:
                ConsoleColours.paintRedMenu()
                print("Invalid input, please enter a valid number")
                self.addCourseToDrop()

    def checkThePrerequisiteAndCourseThatWasTaken(self, course):
        mapGrade = self.__transcript.getCourseGradeMap()
        if course not in mapGrade:
            for prerequisite in course.getPreRequisiteCourses():
                if (prerequisite not in self.__transcript.getStudentCourses()
                        or mapGrade[prerequisite][-1] is None
                        or mapGrade[prerequisite][-1].getLetterGrade() in ["FF", "FD"]):
                    return False
        elif mapGrade[course][-1].getLetterGrade() is None:
            return False
        elif not (mapGrade[course][-1] is not None and mapGrade[course][-1].getLetterGrade() > "CC"):
            return False
        return True

    def hasCourseOverlap(self, courseToAdd, isLab):
        try:
            day_to_index = {"Monday": 0, "Tuesday": 1, "Wednesday": 2, "Thursday": 3, "Friday": 4}
            hour_to_index = {8: 0, 9: 1, 10: 2, 11: 3, 13: 4, 14: 5, 15: 6, 16: 7}

            i = day_to_index[courseToAdd.getDay()]
            j = hour_to_index[int(courseToAdd.getHour())]

            courseInSchedule = self.__schedule[i][j]
            courseGradeMap = self.__transcript.getCourseGradeMap()

            if isLab:
                if courseInSchedule:
                    scheduleCourseGrades = courseGradeMap.get(courseInSchedule, [])
                    if scheduleCourseGrades and scheduleCourseGrades[-1] is None:
                        return True
                    elif len(scheduleCourseGrades) >= 2 and scheduleCourseGrades[-2].getLetterGrade() == "DZ":
                        return True
                for course in self.__draft:
                    grades = courseGradeMap.get(course, [])
                    if grades and grades[-1] is None:
                        continue
                    if course.getDay() == courseToAdd.getDay() and course.getHour() == courseToAdd.getHour():
                        if grades and grades[-1].getLetterGrade() == "DZ":
                            return True
                        if not grades:
                            return True
                for labSection in self.__labSections:
                    if labSection.getDay() == courseToAdd.getDay() and labSection.getHour() == courseToAdd.getHour():
                        return True
            else:
                if courseInSchedule:
                    scheduleCourseGrades = courseGradeMap.get(courseInSchedule, [])
                    if scheduleCourseGrades and scheduleCourseGrades[-1] is None:
                        return True
                    if (scheduleCourseGrades and
                            len(scheduleCourseGrades) >= 2
                            and scheduleCourseGrades[-2].getLetterGrade() == "DZ"):
                        courseToAddGrades = courseGradeMap.get(courseToAdd, [])
                        if (not courseToAddGrades or
                                courseToAddGrades[-1] and
                                courseToAddGrades[-1].getLetterGrade() == "DZ"):
                            return True
                for course in self.__draft:
                    grades = courseGradeMap.get(course, [])
                    if grades and grades[-1] is None:
                        continue
                    if course.getDay() == courseToAdd.getDay() and course.getHour() == courseToAdd.getHour():
                        if grades and grades[-1].getLetterGrade() == "DZ":
                            return True
                        courseToAddGrades = courseGradeMap.get(courseToAdd, [])
                        if not grades and (
                                not courseToAddGrades or
                                courseToAddGrades[-1].getLetterGrade() == "DZ"):
                            return True
                for labSection in self.__labSections:
                    if labSection.getDay() == courseToAdd.getDay() and labSection.getHour() == courseToAdd.getHour():
                        return True
            return False

        except KeyError as e:
            ConsoleColours.paintRedMenu()
            print(f"Invalid day or hour: {e}")
            logging.error("An error occurred in hasCourseOverlap method.")
            ConsoleColours.resetColour()
        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in hasCourseOverlap method.")
            ConsoleColours.resetColour()

    def maxCoursesReached(self):
        numberOfCourses = self.calculateNumberOfCourses()
        if numberOfCourses >= self.getDepartment().getMaxCourseNumber():
            ConsoleColours.paintRedMenu()
            logging.warning(f"Student {self.getID()} reached the maximum number of courses.")
            print(f"Limit Reached! You can take at most {self.getDepartment().getMaxCourseNumber()} courses.")
            return True
        return False

    def calculateNumberOfCourses(self):
        numberOfCourses = 0
        numberOfCourses += len(self.__draft)

        courseGradeMap = self.__transcript.getCourseGradeMap()
        for course in courseGradeMap.keys():
            grades = courseGradeMap.get(course, [])
            if grades and grades[-1] is None:
                numberOfCourses += 1
        return numberOfCourses

    def removeCourseFromDraft(self):
        try:
            if self.__hasRequest:
                ConsoleColours.paintRedMenu()
                logging.warning(
                    f"Student {self.getID()} can not remove lecture because he/she has a request waiting for {self.__advisor.getID()}'s approval.")
                print("You can not remove lecture because you have a request waiting for approval.")
                return
            if not self.__draft:
                ConsoleColours.paintRedMenu()
                print("Your draft is empty!")
                ConsoleColours.resetColour()
            else:
                ConsoleColours.paintBlueMenu()
                print(
                    "Remove Course from Draft\n"
                    "¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n"
                    "Back -> 0\n"
                    "¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n"
                    "Select the course you want to remove:\n"
                    "¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
                ConsoleColours.paintPurpleMenu()
                for i, course in enumerate(self.__draft):
                    print(
                        f"{i + 1}. {course.getCourseCode()} - {course.getCourseName()} - {course.getDay()} - {course.getHour()}.00\n"
                        f"´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´")
                ConsoleColours.paintGreenMenu()
                print(f"Choose number between 1 to {len(self.__draft)} to remove course: \n")
                userNumberInput = self.__system.getInput()
                if 1 <= userNumberInput <= len(self.__draft):
                    for labSection in self.__draft[userNumberInput - 1].getLaboratorySections():
                        if labSection in self.__labSections:
                            self.__labSections.remove(labSection)
                            labSection.setNumberOfStudents(labSection.getNumberOfStudents() - 1)
                            break
                    logging.info(
                        f"Student {self.getID()} removed {self.__draft[userNumberInput - 1].getCourseCode()} from draft.")
                    del self.__draft[userNumberInput - 1]
                    self.removeCourseFromDraft()
                elif userNumberInput > len(self.__draft) or userNumberInput < 0:
                    ConsoleColours.paintRedMenu()
                    logging.warning(f"Student {self.getID()} entered invalid input.")
                    print("Invalid input, please enter a number")
                    self.removeCourseFromDraft()

        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in removeCourseFromDraft method.")
            ConsoleColours.resetColour()

    def showRequestStatus(self):
        try:
            ConsoleColours.paintYellowMenu()
            if self.__hasRequest:
                logging.info(f"Student {self.getID()} has a request waiting for {self.__advisor.getID()}'s approval.")
                print("Your request is waiting for advisor approval.")
            else:
                print("There is no waiting request.")
                ConsoleColours.resetColour()
                return

            ConsoleColours.paintBlueMenu()
            print(":::::::::::::::::::::::Your draft:::::::::::::::::::::::")
            ConsoleColours.paintGreenMenu()
            print(
                "::::::Courses that have been requested to be added::::::\n"
                "::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n")

            if self.__draft and self.__transcript:
                courseGradeMap = self.__transcript.getCourseGradeMap()
                for course in self.__draft:
                    if course not in courseGradeMap or courseGradeMap[course][-1] is not None:
                        print(
                            f"{course.getCourseCode()}-{course.getCourseName()} - {course.getDay()} - {course.getHour()}.00")

            print("\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n")
            ConsoleColours.paintRedMenu()
            print(
                ":::::Courses that have been requested to be dropped:::::\n"
                "::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n")
            if self.__draft and self.__transcript:
                courseGradeMap = self.__transcript.getCourseGradeMap()
                for course in self.__draft:
                    if course in courseGradeMap and courseGradeMap[course] and courseGradeMap[course][-1] is None:
                        print(
                            f"{course.getCourseCode()}-{course.getCourseName()} - {course.getDay()} - {course.getHour()}.00")

            print("\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n")
            ConsoleColours.resetColour()
            print("\n")
        except Exception as e:
            ConsoleColours.paintRedMenu()
            print(f"An error occurred: {e}")
            logging.exception("An error occurred in showRequestStatus method.")
            ConsoleColours.resetColour()

    def computeAvailableCoursesToDrop(self):
        self.__availableCoursesToDrop.clear()
        if self.__transcript:
            mapGrade = self.__transcript.getCourseGradeMap()
            studentCourses = self.__transcript.getStudentCourses()
            if studentCourses:
                for course in studentCourses:
                    if course in self.__draft:
                        continue
                    grades = mapGrade.get(course, [])
                    if grades and grades[-1] is None:
                        self.__availableCoursesToDrop.append(course)

    def login(self, userName, password):
        return self.getUserName() == userName and self.getPassword() == password

    def getDepartmentName(self):
        return self.getDepartment().getDepartmentName()

    def setPassword(self, password):
        self.__password = password

    def setUserName(self, userName):
        self.__userName = userName

    def getAdvisor(self):
        return self.__advisor

    def setAdvisor(self, advisor):
        self.__advisor = advisor

    def getSemester(self):
        return self.__semester

    def getDraft(self):
        return self.__draft

    def setDraft(self, draft):
        self.__draft = draft

    def isHasRequest(self):
        return self.__hasRequest

    def setHasRequest(self, hasRequest):
        self.__hasRequest = hasRequest

    def setNotification(self, notification):
        self.__notification = notification

    def getTranscript(self):
        return self.__transcript

    def setTranscript(self, transcript):
        self.__transcript = transcript

    def getSchedule(self):
        return self.__schedule

    def getLabSections(self):
        return self.__labSections
