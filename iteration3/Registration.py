from iteration3.ConsoleColours import ConsoleColours
from iteration3.Notification import Notification


class Registration:

    def __init__(self, student, courses):
        from iteration3.CourseRegistrationSystem import CourseRegistrationSystem
        self.__system = CourseRegistrationSystem()
        self.__student = student
        self.__courses = courses

    def approveRequest(self):
        ConsoleColours.paintYellowMenu()
        print("Request approved.")
        ConsoleColours.resetColour()
        for course in self.__courses:
            if course not in self.getStudent().getTranscript().getStudentCourses():
                self.__system.addToSchedule(course, self.getStudent())
                self.getStudent().getTranscript().getCourseGradeMap()[course] = [None]
                self.getStudent().getTranscript().getStudentCourses().append(course)
                course.setNumberOfStudents(course.getNumberOfStudents() + 1)
            else:
                tempList = self.getStudent().getTranscript().getCourseGradeMap()[course]

                if len(tempList) == 1 and tempList[0] is None:
                    self.__system.removeFromSchedule(course, self.getStudent())
                    self.getStudent().getTranscript().getCourseGradeMap().pop(course, None)
                    self.getStudent().getTranscript().getStudentCourses().remove(course)
                    course.setNumberOfStudents(course.getNumberOfStudents() - 1)
                elif len(tempList) == 1 and tempList[0] is not None:
                    self.__system.addToSchedule(course, self.getStudent())
                    tempList.append(None)
                    course.setNumberOfStudents(course.getNumberOfStudents() + 1)
                elif len(tempList) > 1 and tempList[-1] is None:
                    self.__system.removeFromSchedule(course, self.getStudent())
                    tempList.remove(tempList[-1])
                    course.setNumberOfStudents(course.getNumberOfStudents() - 1)
                elif tempList and tempList[-1] is not None:
                    self.__system.addToSchedule(course, self.getStudent())
                    tempList.append(None)
                    course.setNumberOfStudents(course.getNumberOfStudents() + 1)

        self.getStudent().setHasRequest(False)
        self.getStudent().getDraft().clear()
        Notification(self.getStudent(), "Your request has been approved.").sendNotification()

    def rejectRequest(self):
        ConsoleColours.paintYellowMenu()
        print("Request rejected.")
        ConsoleColours.resetColour()
        self.getStudent().setHasRequest(False)
        self.getStudent().getDraft().clear()

        Notification(self.getStudent(), "Your request has been rejected.").sendNotification()

    def addRequest(self, advisor):
        advisor.getRequestList().append(self)
        self.getStudent().setHasRequest(True)
        ConsoleColours.paintYellowMenu()
        print("Request sent to advisor.")
        ConsoleColours.resetColour()
    def getStudent(self):
        return self.__student

    def getCourses(self):
        return self.__courses
