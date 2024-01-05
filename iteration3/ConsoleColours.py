class ConsoleColours:
    ANSI_RESET = "\u001B[0m"
    ANSI_WHITE_BRIGHT = "\u001B[97m"
    ANSI_BLACK = "\u001B[30m"
    ANSI_RED_BACKGROUND = "\u001B[41m"
    ANSI_GREEN_BACKGROUND = "\u001B[42m"
    ANSI_BLUE_BACKGROUND = "\u001B[44m"
    ANSI_YELLOW_BACKGROUND = "\u001B[43m"
    ANSI_PURPLE_BACKGROUND = "\u001B[45m"
    ANSI_WHITE_BRIGHT_BACKGROUND = "\u001B[107m"

    @staticmethod
    def resetColour():
        print(ConsoleColours.ANSI_RESET, end="")

    @staticmethod
    def paintBlueMenu():
        print(ConsoleColours.ANSI_BLUE_BACKGROUND + ConsoleColours.ANSI_WHITE_BRIGHT, end="")

    @staticmethod
    def paintRedMenu():
        print(ConsoleColours.ANSI_RED_BACKGROUND + ConsoleColours.ANSI_WHITE_BRIGHT, end="")

    @staticmethod
    def paintYellowMenu():
        print(ConsoleColours.ANSI_YELLOW_BACKGROUND + ConsoleColours.ANSI_WHITE_BRIGHT, end="")

    @staticmethod
    def paintGreenMenu():
        print(ConsoleColours.ANSI_GREEN_BACKGROUND + ConsoleColours.ANSI_WHITE_BRIGHT, end="")

    @staticmethod
    def paintPurpleMenu():
        print(ConsoleColours.ANSI_PURPLE_BACKGROUND + ConsoleColours.ANSI_WHITE_BRIGHT, end="")

    @staticmethod
    def paintWhiteMenu():
        print(ConsoleColours.ANSI_BLACK + ConsoleColours.ANSI_WHITE_BRIGHT_BACKGROUND, end="")
