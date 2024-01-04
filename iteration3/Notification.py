
class Notification:
    def __init__(self, recipient, message):
        self.recipient = recipient
        self.message = message

    def sendNotification(self):
        self.recipient.setNotification(self)

    def getMessage(self):
        return self.message
