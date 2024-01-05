from iteration3.ConsoleColours import ConsoleColours


class Transcript:
    def __init__(self, student):
        self.__studentCourses = []
        self.__student = student
        self.__semester = student.getSemester()
        self.__cgpa = self.calculateCgpa()
        self.__takenCredits = self.calculateTakenCredits()
        self.__completedCredits = self.calculateCompletedCredits()
        self.__courseGradeMap = {}

    def calculateValues(self):
        self.__cgpa = self.calculateCgpa()
        self.__takenCredits = self.calculateTakenCredits()
        self.__completedCredits = self.calculateCompletedCredits()

