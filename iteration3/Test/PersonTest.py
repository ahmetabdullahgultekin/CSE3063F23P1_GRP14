import unittest

from iteration3.Advisor import Advisor


class PersonTest(unittest.TestCase):

    def setUp(self):
        self.person = Advisor(1, "name", "surname", "validUsername", "validPassword")

    def test_loginShouldReturnTrueWhenCredentialsAreValid(self):
        username = "validUsername"
        password = "validPassword"
        self.assertTrue(self.person.login(username, password))

    def test_loginShouldReturnFalseWhenCredentialsAreInvalid(self):
        username = "invalidUsername"
        password = "invalidPassword"
        self.assertFalse(self.person.login(username, password))


if __name__ == "__main__":
    unittest.main()
