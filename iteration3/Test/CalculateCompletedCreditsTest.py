import unittest
from iteration3.Transcript import Transcript
from iteration3.Course import Course
from iteration3.Grade import Grade


class CalculateCompletedCreditsTest(unittest.TestCase):

    def setUp(self):
        # Create necessary objects
        from iteration3.Student import Student
        self.student = Student(150120005, "name", "surname", "username", "password", 3)
        self.transcript = Transcript(self.student)
        self.course1 = Course("courseName1", "CSE100", "Mandatory", 3, 3, 120, 8, "Monday")
        self.course2 = Course("courseName2", "CSE101", "Mandatory", 4, 3, 120, 9, "Monday")
        self.course3 = Course("courseName3", "CSE102", "Mandatory", 5, 3, 120, 10, "Monday")
        self.course4 = Course("courseName4", "CSE103", "Mandatory", 7, 3, 120, 11, "Monday")

        self.grade1 = Grade("FF")
        self.grade2 = Grade("FD")
        self.grade3 = Grade("AA")
        self.grade4 = Grade("DC")

        self.transcript.setTakenCredits(19)
        # Add the courses to the Transcript's studentCourses list
        self.transcript.getStudentCourses().append(self.course1)
        self.transcript.getStudentCourses().append(self.course2)
        self.transcript.getStudentCourses().append(self.course3)
        self.transcript.getStudentCourses().append(self.course4)

        # Create a sample courseGradeMap
        self.course_grade_map = {}

        # Add grades for the courses
        self.addGradeToCourse(self.course1, self.grade1)
        self.addGradeToCourse(self.course2, self.grade2)
        self.addGradeToCourse(self.course3, self.grade3)
        self.addGradeToCourse(self.course4, self.grade4)

        # Set the courseGradeMap in the Transcript object
        self.transcript.setCourseGradeMap(self.course_grade_map)

    def addGradeToCourse(self, course, grade):
        if course in self.course_grade_map:
            self.course_grade_map[course].append(grade)
        else:
            self.course_grade_map[course] = [grade]

    def testCalculateCompletedCredits(self):
        self.assertEqual(12, self.transcript.calculateCompletedCredits())
