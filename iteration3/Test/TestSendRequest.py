import sys
import unittest
from io import StringIO
from unittest.mock import Mock

from iteration3.Advisor import Advisor
from iteration3.Course import Course
from iteration3.Registration import Registration
from iteration3.Student import Student


class TestSendRequest(unittest.TestCase):

    def setUp(self):
        self.advisor1 = Mock(spec=Advisor)
        self.student1 = Student(150121073, "name", "surname", "username", "password", 3)
        self.student2 = Student(150121091, "name", "surname", "username", "password", 3)
        self.student3 = Student(150120045, "name", "surname", "username", "password", 3)

        course1 = Course("courseName1", "CSE100", "Mandatory", 3, 3, 120, 8, "Monday")

        self.advisor1 = Advisor(130116002, "name", "surname", "username", "password")
        self.student1.setAdvisor(self.advisor1)
        self.student2.setAdvisor(self.advisor1)
        self.student3.setAdvisor(self.advisor1)
        draft1 = []
        draft2 = []
        draft3 = [course1]
        self.student1.setDraft(draft1)
        self.student2.setDraft(draft2)
        self.student3.setDraft(draft3)
        self.student3.setAdvisor(self.advisor1)

        registration1 = Registration(self.student3, draft3)
        registration1.addRequest(self.advisor1)
        self.student1.setHasRequest(True)
        self.student2.setHasRequest(False)
        self.student3.setHasRequest(False)

    def test_send_request_with_has_request(self):
        expected = "You already have a request waiting for approval."
        old_stdout = sys.stdout
        redirected_output = sys.stdout = StringIO()
        try:
            result = self.student1.sendRequest()
            self.assertIsNone(result)
            self.assertTrue(expected in redirected_output.getvalue().strip())
        finally:
            sys.stdout = old_stdout

    def test_send_request_with_draft_is_empty(self):
        expected = "Your draft is empty!"
        old_stdout = sys.stdout
        redirected_output = sys.stdout = StringIO()
        try:
            result = self.student2.sendRequest()
            self.assertIsNone(result, "Expected sendRequest to return None when draft is empty")
            self.assertIn(expected, redirected_output.getvalue().strip())
        finally:
            sys.stdout = old_stdout

    def test_send_request_with_valid_request(self):
        expected = "Request sent to advisor"
        old_stdout = sys.stdout
        redirected_output = sys.stdout = StringIO()
        try:
            result = self.student3.sendRequest()
            self.assertIsNone(result)
            self.assertTrue(expected in redirected_output.getvalue().strip())
        finally:
            sys.stdout = old_stdout


if __name__ == '__main__':
    unittest.main()
