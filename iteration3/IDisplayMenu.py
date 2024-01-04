from abc import ABC, abstractmethod


class IDisplayMenu(ABC):
    """
    This interface is used to display menus.
    """

    @abstractmethod
    def printMenu(self, menuType):
        """
        Prints the menu for the specified menu type.

        :param menuType: The type of menu to print.
        """
