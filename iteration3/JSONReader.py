import json
import logging
import sys

from iteration3.Advisor import Advisor
from iteration3.Assistant import Assistant
from iteration3.Course import Course
from iteration3.Department import Department
from iteration3.Grade import Grade
from iteration3.LaboratorySection import LaboratorySection
from iteration3.Lecturer import Lecturer
from iteration3.Registration import Registration
from iteration3.Student import Student
from iteration3.Transcript import Transcript


class JSONReader:

    def __init__(self):
        from iteration3.CourseRegistrationSystem import CourseRegistrationSystem
        self.__system = CourseRegistrationSystem()
        self.__coursePrerequisiteCourseCodesMap = {}
        self.__lecturerIDCoursesMap = {}
        self.__assistantIDAssistantMap = {}
        self.__advisorIDCoursesMap = {}
        self.__studentIDAdvisorIDMap = {}
        self.__studentRegistrationMap = {}
        self.__sectionCodeSectionMap = {}
        self.__university = None
        self.__department = None

    def readDepartments(self, university):
        with open("jsons/departments.json", 'r', encoding='utf-8') as f:
            data = f.read()

        self.__university = university
        # Parse the JSON file into a Python object.
        data = json.loads(data)

        for department in data:
            departmentName = department.get("departmentName")

            # Create a new department object with the retrieved details.
            newDepartment = Department(departmentName)
            logging.info(f"Department {departmentName} created.")
            university.getDepartments().append(newDepartment)

    def start(self, department):
        self.__department = department
        logging.info(f"Reading JSON data and synchronizing objects for department: {department.getDepartmentName()}")
        self.readJson()
        self.syncObjects()
        logging.info(f"JSON data read and objects synchronized for department: {department.getDepartmentName()}")

    def readJson(self):
        self.readCourses()
        self.readAssistants()
        self.readLabSections()
        self.readLecturers()
        self.readStudents()
        self.readRequests()
        self.readAdvisors()

    def readCourses(self):
        try:
            with open("jsons/courses.json", 'r', encoding='utf-8') as f:
                coursesArray = json.load(f)

            for course in coursesArray:
                # Retrieve the course name, course code, etc. from the JSON.
                courseName = course.get("courseName")
                courseCode = course.get("courseCode")
                courseType = course.get("courseType")
                courseCredit = course.get("courseCredit")
                semester = course.get("semester")
                capacity = course.get("capacity")
                hour = course.get("hour")
                day = course.get("day")

                newCourse = Course(courseName, courseCode, courseType, courseCredit,
                                   semester, capacity, hour, day)

                logging.info(f"Course {courseCode} created.")
                self.__department.getCourses().append(newCourse)
                self.__department.getCourseCodeCourseMap().update({courseCode: newCourse})

                # Get the array of prerequisite course codes from the course.
                preRequisiteCourseCodes = course.get("preRequisiteCourseCodes")
                self.__coursePrerequisiteCourseCodesMap[newCourse] = preRequisiteCourseCodes


        except Exception as e:
            print("There is a problem with the courses.json file.")
            logging.error("There is a problem with the courses.json file.")
            exit(0)

    def readAssistants(self):
        try:
            with open("jsons/assistants.json", 'r', encoding='utf-8') as f:
                data = f.read()

            assistantsArray = json.loads(data)

            for lecturer in assistantsArray:
                id = lecturer.get("assistantID")
                name = lecturer.get("name")
                surname = lecturer.get("surname")

                # Create a new Lecturer object with the retrieved details.
                newAssistant = Assistant(id, name, surname)
                logging.info(f"Assistant created with ID: {id}")

                self.__department.getAssistants().append(newAssistant)
                self.__assistantIDAssistantMap[id] = newAssistant
                newAssistant.setDepartment(self.__department)
        except Exception as e:
            print("There is a problem with the assistants.json file.")
            logging.error("There is a problem with the assistants.json file.")
            sys.exit(0)

    def readLabSections(self):
        try:
            with open("jsons/labSections.json", 'r', encoding='utf-8') as f:
                labSectionsArray = json.load(f)

            for labSection in labSectionsArray:
                # Parse the labSection fields.
                labSectionCode = labSection.get("labSectionCode")
                courseCode = labSection.get("courseCode")
                day = labSection.get("day")
                hour = labSection.get("hour")
                capacity = labSection.get("capacity")
                assistantID = labSection.get("assistantID")

                course = self.__department.getCourseCodeCourseMap().get(courseCode)

                newLabSection = LaboratorySection(labSectionCode, capacity, hour, day)

                logging.info(f"Lab section created with code: {labSectionCode}")

                newLabSection.setAssistant(self.__assistantIDAssistantMap.get(assistantID))
                self.__sectionCodeSectionMap[labSectionCode] = newLabSection

                self.__department.getLaboratorySections().append(newLabSection)
                self.__department.getSectionCodeCourseMap()[labSectionCode] = course
                self.__department.getLabSectionCourseMap()[newLabSection] = course

        except Exception as e:
            print("There is a problem with the labSections.json file.")
            logging.error("There is a problem with the labSections.json file.")
            exit(0)

    def readLecturers(self):
        try:
            with open("jsons/lecturers.json", 'r', encoding='utf-8') as f:
                lecturersArray = json.load(f)

            for lecturer in lecturersArray:
                id = lecturer.get("lecturerID")
                name = lecturer.get("name")
                surname = lecturer.get("surname")

                newLecturer = Lecturer(id, name, surname, None, None)
                logging.info(f"Lecturer created with ID: {id}")

                self.__department.getLecturers().append(newLecturer)
                newLecturer.setDepartment(self.__department)

                # Get the array of lessons taught from the lecturer.
                lessonsTaughtArray = lecturer.get("lessonsTaught")
                lessonsTaught = [lesson for lesson in lessonsTaughtArray]

                self.__lecturerIDCoursesMap[id] = lessonsTaught

        except Exception as e:
            print("There is a problem with the lecturers.json file.")
            logging.error("There is a problem with the lecturers.json file.")
            exit(0)

    def readStudents(self):
        try:
            with open('jsons/students.json', 'r', encoding='utf-8') as f:
                studentsArray = json.load(f)

            for student in studentsArray:
                id = student.get("studentID")
                name = student.get("name")
                surname = student.get("surname")
                userName = student.get("userName")
                password = student.get("password")
                semester = student.get("semester")

                newStudent = Student(id, name, surname, userName, password, semester)
                logging.info(f"Student created with ID: {id}")
                newStudent.setDepartment(self.__department)

                labSectionsArray = student.get("labSections")
                for labSectionCode in labSectionsArray:
                    newStudent.getLabSections().append(self.__sectionCodeSectionMap.get(labSectionCode))

                self.__department.getStudents().append(newStudent)
                self.__university.getUserNamePersonMap()[userName] = newStudent
                self.__department.getStudentIDStudentMap()[id] = newStudent

                advisorID = student.get("advisorID")
                self.__studentIDAdvisorIDMap[id] = advisorID

                self.readTranscript(newStudent)  # assuming the existence of `readTranscript` method

        except Exception as e:
            print("There is a problem with the students.json file.")
            logging.error("There is a problem with the students.json file.")
            exit(0)

    def readTranscript(self, student):
        try:
            with open(f"jsons/Transcripts/{student.getID()}.json", 'r', encoding='utf-8') as f:
                transcript = json.load(f)

            courseGradeMap = {}
            studentCourses = []
            courses = transcript.get("courses")
            for course in courses:
                letterGrade = course.get("letterGrade")
                courseCode = course.get("courseCode")
                course1 = self.__department.getCourseCodeCourseMap().get(courseCode)

                if course1 not in studentCourses:
                    studentCourses.append(course1)
                    grades = []

                    if letterGrade == "null":
                        self.__system.addToSchedule(course1, student)
                        course1.setNumberOfStudents(course1.getNumberOfStudents() + 1)
                        grades.append(None)
                        courseGradeMap[course1] = grades
                    else:
                        grades.append(Grade(letterGrade))
                        courseGradeMap[course1] = grades
                else:
                    if letterGrade == "null":
                        self.__system.addToSchedule(course1, student)
                        course1.setNumberOfStudents(course1.getNumberOfStudents() + 1)
                        courseGradeMap[course1].append(None)
                    else:
                        courseGradeMap[course1].append(Grade(letterGrade))

            newTranscript = Transcript(student)
            self.__department.getTranscripts().append(newTranscript)
            newTranscript.setStudentCourses(studentCourses)

            student.setTranscript(newTranscript)
            student.getTranscript().setCourseGradeMap(courseGradeMap)
            newTranscript.calculateValues()

            logging.info(f"Transcript created for student with ID: {student.getID()}")

        except Exception as e:
            print(f"There is a problem with the {student.getID()}.json file.")
            logging.error(f"There is a problem with the {student.getID()}.json file.")
            exit(0)

    def readRequests(self):
        try:
            with open("jsons/requests.json", 'r', encoding='utf-8') as f:
                requestsArray = json.load(f)

            for request in requestsArray:
                draftCourses = []
                studentID = request.get("studentID")
                courseCodesArray = request.get("courses")

                for courseCode in courseCodesArray:
                    course1 = self.__department.getCourseCodeCourseMap().get(courseCode)
                    draftCourses.append(course1)

                student = self.__department.getStudentIDStudentMap().get(studentID)
                student.setDraft(draftCourses)
                student.setHasRequest(True)
                self.__studentRegistrationMap[student] = Registration(student, draftCourses)

        except Exception as e:
            print("There is a problem with the requests.json file.")
            logging.error("There is a problem with the requests.json file.")
            exit(0)

    def readAdvisors(self):
        try:
            with open("jsons/advisors.json", 'r', encoding='utf-8') as f:
                advisorsArray = json.load(f)

            for advisor in advisorsArray:
                id = advisor.get("advisorID")
                name = advisor.get("name")
                surname = advisor.get("surname")
                userName = advisor.get("userName")
                password = advisor.get("password")

                newAdvisor = Advisor(id, name, surname, userName, password)
                logging.info(f"Advisor created with ID: {id}")

                self.__department.getAdvisors().append(newAdvisor)
                newAdvisor.setDepartment(self.__department)

                self.__department.getAdvisorIDAdvisorMap()[id] = newAdvisor
                self.__university.getUserNamePersonMap()[userName] = newAdvisor

                lessonsTaughtArray = advisor.get("lessonsTaught")
                lessonsTaught = [lesson for lesson in lessonsTaughtArray]

                self.__advisorIDCoursesMap[id] = lessonsTaught

        except Exception as e:
            print("There is a problem with the advisors.json file.")
            logging.error("There is a problem with the advisors.json file.")
            exit(0)

    def syncObjects(self):
        # Sync for courses
        # For each course in the department, add its prerequisite courses and course sections
        for course in self.__department.getCourses():
            for courseCode in self.__coursePrerequisiteCourseCodesMap[course]:
                course.getPreRequisiteCourses().append(self.__department.getCourseCodeCourseMap()[courseCode])

        # Sync for lab sections
        for labSection in self.__department.getLaboratorySections():
            course = self.__department.getLabSectionCourseMap()[labSection]
            course.getLaboratorySections().append(labSection)

        # Sync for lecturers
        # For each lecturer in the department, add the courses they teach and set them as the lecturer for each course
        for lecturer in self.__department.getLecturers():
            for courseCode in self.__lecturerIDCoursesMap[lecturer.getID()]:
                lecturer.getLessonsTaught().append(self.__department.getCourseCodeCourseMap()[courseCode])
            for course in lecturer.getLessonsTaught():
                course.setLecturer(lecturer)

        # Sync for advisors
        # For each student in the department, add them to their advisor's list of advised students and add their registration to the advisor's request list
        # For each advisor in the department, add the courses they teach
        for student in self.__department.getStudents():
            advisor = self.__department.getAdvisorIDAdvisorMap()[self.__studentIDAdvisorIDMap[student.getID()]]
            student.setAdvisor(advisor)
            advisor.getStudentsAdvised().append(student)
            if student in self.__studentRegistrationMap and self.__studentRegistrationMap[student] is not None:
                advisor.getRequestList().append(self.__studentRegistrationMap[student])
        for advisor in self.__department.getAdvisors():
            for courseCode in self.__advisorIDCoursesMap[advisor.getID()]:
                advisor.getLessonsTaught().append(self.__department.getCourseCodeCourseMap()[courseCode])
