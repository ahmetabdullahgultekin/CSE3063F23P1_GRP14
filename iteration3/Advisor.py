import logging

from iteration3.IDisplayMenu import IDisplayMenu
from iteration3.Lecturer import Lecturer
from iteration3.ConsoleColours import ConsoleColours


class Advisor(Lecturer, IDisplayMenu):

    def __init__(self, ID, name, surname, userName, password):
        super().__init__(ID, name, surname, userName, password)
        from iteration3.CourseRegistrationSystem import CourseRegistrationSystem
        self.__system = CourseRegistrationSystem()
        self.__studentsAdvised = []
        self.__requestList = []
        self.__requestNumber = None
        self.__notification = None

    def printRequests(self):
        ConsoleColours.paintBlueMenu()
        print("Request Approval Menu:")
        print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n")
        print("\nBack -> 0\n")
        print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
        if len(self.__requestList) != 0:
            print("Request(s) Listed Below:")
            print("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
            ConsoleColours.paintPurpleMenu()
            for i in range(len(self.__requestList)):
                print(
                    f"{i + 1}. {self.__requestList[i].getStudent().getName()} {self.__requestList[i].getStudent().getSurname()}")
                print("``````````````````````````````````````````````")

                ConsoleColours.paintGreenMenu()
                print("Enter the request number you want to evaluate: ")

            requestNumber = self.__system.getInput()
            ConsoleColours.paintGreenMenu()
            if 1 <= requestNumber <= len(self.__requestList):
                self.__requestNumber = requestNumber
                request = self.__requestList[requestNumber - 1]
                print(
                    f"{request.getStudent().getName()} {request.getStudent().getSurname()} wants to take these courses:")
                ConsoleColours.paintPurpleMenu()
                self.checkAndPrintOverlap(request)
                ConsoleColours.paintBlueMenu()
                print()
                ConsoleColours.paintRedMenu()
                print(
                    f"{request.getStudent().getName()} {request.getStudent().getSurname()} wants to drop these courses:")
                ConsoleColours.paintPurpleMenu()
                for course in request.getCourses():
                    if request.getStudent().getTranscript().getCourseGradeMap().get(course) is not None \
                            and request.getStudent().getTranscript().getCourseGradeMap().get(course)[-1] is None:
                        print(f"{course.getCourseCode()} {course.getCourseName()}")
                self.replyRequests()
            elif requestNumber > len(self.__requestList) or requestNumber < 0:
                ConsoleColours.paintRedMenu()
                logging.warning("Advisor " + self.getID() + " entered invalid input.")
                print("Invalid choice! Please select again.")
                self.printRequests()
        else:
            ConsoleColours.paintYellowMenu()
            print("There is not a waiting request!")
            print("You are redirecting to the Advisor Main Menu...")
            ConsoleColours.resetColour()
            print()

    def checkAndPrintOverlap(self, request):
        sorted_courses = []
        nested_list = []
        for course in request.getCourses():
            sorted_courses.append(course.getCourseCode())
            sorted_courses.append(course.getCourseName())
            sorted_courses.append(course.getDay())
            sorted_courses.append(course.getHour())
            nested_list.append(sorted_courses)
            sorted_courses = []
            if not (request.getStudent().getTranscript().getCourseGradeMap().get(course) is not None
                    and request.getStudent().getTranscript().getCourseGradeMap().get(course)[-1] is None):
                print(
                    f"{course.getCourseCode()} {course.getCourseName()} - {course.getDay()} - {course.getHour()}.00")
                print()
        lanet = []
        for i in range(len(nested_list) - 1):
            conflict = 0
            if i in lanet:
                continue
            controlInput = 0
            for j in range(i + 1, len(nested_list)):
                if j in lanet:
                    continue
                first_list = nested_list[i]
                second_list = nested_list[j]

                es_Day = first_list[2]
                es_Hour = first_list[3]

                g_day = second_list[2]
                g_Hour = second_list[-1]
                ConsoleColours.paintRedMenu()
                if (es_Day == g_day) and (es_Hour == g_Hour) and controlInput == 0:
                    print(
                        f"{first_list[0]} - {first_list[1]} / {second_list[0]} - {second_list[1]} /", end='')
                    controlInput = 1
                    conflict = 1
                    lanet.append(j)
                    continue
                if (es_Day == g_day) and (
                        es_Hour == g_Hour) and controlInput == 1:
                    print(
                        f"{second_list[0]} - {second_list[1]} /",
                        end='')
                    lanet.append(j)
                    conflict = 1
            if conflict == 1:
                print(
                    f"------->{nested_list[i][2]} - {nested_list[i][3]}.00 -----> CLASS OVERLAP!!!")
