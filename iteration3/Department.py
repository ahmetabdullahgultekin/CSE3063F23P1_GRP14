class Department:

    def __init__(self, departmentName: str):
        self.__departmentName = departmentName
        self.__advisors = []
        self.__transcripts = []
        self.__courses = []
        self.__students = []
        self.__laboratorySections = []
        self.__lecturers = []
        self.__assistants = []
        self.__courseCodeCourseMap = {}
        self.__sectionCodeCourseMap = {}
        self.__labSectionCourseMap = {}
        self.__studentIDStudentMap = {}
        self.__advisorIDAdvisorMap = {}
        self.__maxCourseNumber = 10

    def getDepartmentName(self):
        return self.__departmentName

    def getAdvisors(self):
        return self.__advisors

    def getTranscripts(self):
        return self.__transcripts

    def getCourses(self):
        return self.__courses

    def getStudents(self):
        return self.__students

    def getLaboratorySections(self):
        return self.__laboratorySections

    def getLecturers(self):
        return self.__lecturers

    def getAssistants(self):
        return self.__assistants

    def getCourseCodeCourseMap(self):
        return self.__courseCodeCourseMap

    def getSectionCodeCourseMap(self):
        return self.__sectionCodeCourseMap

    def getLabSectionCourseMap(self):
        return self.__labSectionCourseMap

    def getStudentIDStudentMap(self):
        return self.__studentIDStudentMap

    def getAdvisorIDAdvisorMap(self):
        return self.__advisorIDAdvisorMap

    def getMaxCourseNumber(self):
        return self.__maxCourseNumber
