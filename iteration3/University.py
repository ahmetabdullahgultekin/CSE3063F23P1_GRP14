class University:
    def __init__(self, name):
        self.__name = name
        self.__departments = []  # it holds departments list.
        self.__userNamePersonMap = {}  # it holds username and password of users.

    def getDepartments(self):
        return self.__departments

    def getUserNamePersonMap(self):
        return self.__userNamePersonMap
