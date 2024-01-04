from iteration3.Person import Person


class Assistant(Person):
    def __init__(self, ID, name, surname):
        super().__init__(ID, name, surname, None, None)

    def login(self, userName, password):
        return False
