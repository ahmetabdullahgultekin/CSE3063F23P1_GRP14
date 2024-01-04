from iteration3.Person import Person


class Lecturer(Person):
    # For creating lecturer with person abstract class.
    def __init__(self, ID, name, surname, userName, password):
        super().__init__(ID, name, surname, userName, password)
        self.__lessonsTaught = []

    # Get the given courses lecturer gives.
    def getLessonsTaught(self):
        return self.__lessonsTaught

    # Advisor login process.
    def login(self, userName, password):
        return False
