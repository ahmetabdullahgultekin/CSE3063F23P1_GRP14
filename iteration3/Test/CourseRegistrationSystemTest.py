import sys
import unittest
from io import StringIO
from iteration3.CourseRegistrationSystem import CourseRegistrationSystem


class CourseRegistrationSystemTest(unittest.TestCase):

    def setUp(self):
        self.courseRegistrationSystem = CourseRegistrationSystem()

    def test_getInputReturnsCorrectValueWhenInputIsValid(self):
        input = "1\n"
        sys.stdin = StringIO(input)
        choice = self.courseRegistrationSystem.getInput()

        self.assertEqual(1,
                         choice,
                         "The method should return the correct input value when the input is valid.")

    def test_getInputReturnsMinusOneWhenInputIsInvalid(self):
        input = "invalid\n"
        sys.stdin = StringIO(input)
        choice = self.courseRegistrationSystem.getInput()
        self.assertEqual(-1,
                         choice,
                         "The method should return -1 when the input is invalid.")