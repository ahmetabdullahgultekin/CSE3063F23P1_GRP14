import unittest

from iteration3.Advisor import Advisor
from iteration3.Course import Course
from iteration3.Registration import Registration
from iteration3.Student import Student
from iteration3.Transcript import Transcript


class TestRegistration(unittest.TestCase):
    def setUp(self):
        self.student = Student(150120000, "name", "surname", "username", "password", 3)
        self.course1 = Course("courseName1", "CSE100", "Mandatory", 3, 3, 120, 8, "Monday")
        self.course2 = Course("courseName2", "CSE101", "Mandatory", 4, 3, 120, 9, "Monday")
        self.courses = [self.course1, self.course2]
        self.registration = Registration(self.student, self.courses)

    def test_rejectRequest(self):
        self.registration.rejectRequest()
        self.assertIs(self.student.isHasRequest(), False)
        self.assertEqual(len(self.student.getDraft()), 0)

    def test_approveRequest(self):
        transcript = Transcript(self.student)
        self.student.setTranscript(transcript)
        self.registration.approveRequest()
        self.assertEqual(len(self.student.getTranscript().getStudentCourses()), 2)
        self.assertIs(self.student.isHasRequest(), False)
        self.assertEqual(len(self.student.getDraft()), 0)

    def test_addRequest(self):
        advisor = Advisor(1234567, "name", "surname", "username", "password")
        self.registration.addRequest(advisor)
        self.assertIn(self.registration, advisor.getRequestList())
        self.assertIs(self.student.isHasRequest(), True)


if __name__ == "__main__":
    unittest.main()
