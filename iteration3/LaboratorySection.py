class LaboratorySection:

    def __init__(self, laboratorySectionCode, capacity, hour, day):
        self.__laboratorySectionCode = laboratorySectionCode
        self.__capacity = capacity
        self.__hour = hour
        self.__day = day
        self.__assistant = None
        self.__numberOfStudents = 0

    def getLaboratorySectionCode(self):
        return self.__laboratorySectionCode

    def setAssistant(self, assistant):
        self.__assistant = assistant

    def getDay(self):
        return self.__day

    def getHour(self):
        return self.__hour

    def getCapacity(self):
        return self.__capacity

    def getNumberOfStudents(self):
        return self.__numberOfStudents

    def setNumberOfStudents(self, numberOfStudents):
        self.__numberOfStudents = numberOfStudents
