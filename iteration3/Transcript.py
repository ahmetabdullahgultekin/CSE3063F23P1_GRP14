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

    def calculateCgpa(self):
        if not self.__studentCourses:
            return 0
        totalGrade = 0
        totalCredits = 0
        for course in self.__studentCourses:
            grades = self.__courseGradeMap.get(course, [])
            if grades and len(grades) > 0:
                grade = grades[-1].getGradeOver4()
                credit = course.getCourseCredit()
                if grade is not None and credit is not None:
                    totalGrade += grade * credit
                    totalCredits += credit

        if totalCredits == 0:
            return 0

        self.__cgpa = totalGrade / totalCredits
        return self.__cgpa

    def calculateTakenCredits(self):
        totalCredits = sum(course.getCourseCredit() for course in self.__studentCourses)
        self.__takenCredits = totalCredits
        return totalCredits

