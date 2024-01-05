from abc import ABC, abstractmethod


class Person(ABC):
    def __init__(self, ID, name, surname, userName, password, department=None):
        self.__ID = ID
        self.__name = name
        self.__surname = surname
        self.__userName = userName
        self.__password = password
        self.__department = department

    @abstractmethod
    def login(self, userName, password):
        pass

    def getUserName(self):
        return self.__userName

    def setUserName(self, userName):
        self.__userName = userName

    def getPassword(self):
        return self.__password

    def setPassword(self, password):
        self.__password = password

    def getDepartment(self):
        return self.__department

    def setDepartment(self, department):
        self.__department = department

    def getName(self):
        return self.__name

    def getSurname(self):
        return self.__surname

    def getID(self):
        return self.__ID
