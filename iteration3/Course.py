class Course:
    def __init__(self, courseName, courseCode, courseType, courseCredit, semester, capacity, hour, day):
        self.__courseName = courseName
        self.__courseCode = courseCode
        self.__courseType = courseType
        self.__capacity = capacity
        self.__courseCredit = courseCredit
        self.__semester = semester
        self.__hour = hour
        self.__day = day
        self.__laboratorySections = []
        self.__preRequisiteCourses = []
        self.__numberOfStudents = 0
        self.__lecturer = None

    def getCourseName(self):
        return self.__courseName

    def getPreRequisiteCourses(self):
        return self.__preRequisiteCourses

    def getCourseCode(self):
        return self.__courseCode

    def getCourseType(self):
        return self.__courseType

    def getCourseCredit(self):
        return self.__courseCredit

    def getHour(self):
        return self.__hour

    def getDay(self):
        return self.__day

    def getLaboratorySections(self):
        return self.__laboratorySections

    def getNumberOfStudents(self):
        return self.__numberOfStudents

    def setNumberOfStudents(self, numberOfStudents):
        self.__numberOfStudents = numberOfStudents

    def setLecturer(self, lecturer):
        self.__lecturer = lecturer

    def hasCapacity(self):
        return self.__numberOfStudents < self.__capacity

    def getSemester(self):
        return self.__semester