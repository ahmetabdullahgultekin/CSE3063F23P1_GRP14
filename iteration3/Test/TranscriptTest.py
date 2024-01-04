import unittest
from collections import defaultdict

from iteration3.Course import Course
from iteration3.Grade import Grade
from iteration3.Student import Student
from iteration3.Transcript import Transcript


class TranscriptTest(unittest.TestCase):

    def setUp(self):
        self.student = Student(150120000, "name", "surname", "username", "password", 3)
        self.transcript = Transcript(self.student)

        self.course1 = Course("courseName1", "CSE100", "Mandatory", 3, 3, 120, 8, "Monday")
        self.course2 = Course("courseName2", "CSE101", "Mandatory", 4, 3, 120, 9, "Monday")
        self.course3 = Course("courseName3", "CSE102", "Mandatory", 5, 3, 120, 10, "Monday")

    def test_calculateCgpaWithNoCourses(self):
        self.assertEqual(0, self.transcript.calculateCgpa(), "CGPA should be 0 when no courses are taken.")

    def test_calculateCgpaWithCoursesNoGrades(self):
        self.transcript.getStudentCourses().extend([self.course1, self.course2, self.course3])
        self.transcript.courseGradeMap = defaultdict(list)
        self.transcript.courseGradeMap[self.course1].append(None)
        self.transcript.courseGradeMap[self.course2].append(None)
        self.transcript.courseGradeMap[self.course3].append(None)
        self.assertEqual(0, self.transcript.calculateCgpa(), "CGPA should be 0 when no grades are assigned.")

    def test_calculateCgpaWithCoursesAndGrades(self):
        self.transcript.getStudentCourses().extend([self.course1, self.course2, self.course3])
        self.transcript.courseGradeMap = {}
        self.transcript.getCourseGradeMap()[self.course1] = [Grade("AA")]
        self.transcript.getCourseGradeMap()[self.course2] = [(Grade("FD"))]
        self.transcript.getCourseGradeMap()[self.course3] = [(Grade("BA"))]
        expectedCgpa = ((4.0 * 3) + (0.5 * 4) + (3.5 * 5)) / 12
        self.assertEqual(expectedCgpa, self.transcript.calculateCgpa(),
                         "CGPA should be calculated correctly based on the grades and credits of the courses.")


if __name__ == '__main__':
    unittest.main()
