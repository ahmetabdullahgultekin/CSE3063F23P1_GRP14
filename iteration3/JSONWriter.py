import json
import logging


class JSONWriter:
    def __init__(self):
        self.__department = None

    def start(self, university):
        for department in university.getDepartments():
            self.__department = department
            self.writeJson()

    def writeJson(self):
        logging.info("Writing JSON data for the department")
        self.writeStudents()
        self.writeRequests()
        self.writeTranscripts()
        logging.info("JSON data for the department has been written")

    def writeTranscripts(self):
        for student in self.__department.getStudents():
            filepath = f"jsons/Transcripts/{student.getID()}.json"
            try:
                with open(filepath, 'r+', encoding='utf-8') as f:
                    data = json.load(f)
                    data['takenCredits'] = student.getTranscript().getTakenCredits()
                    data['completedCredits'] = student.getTranscript().getCompletedCredits()
                    data['cgpa'] = round(student.getTranscript().getCgpa(), 2)
                    data['courses'] = [
                        {
                            "courseCode": course.getCourseCode(),
                            "letterGrade": grade.getLetterGrade() if grade else None
                        }
                        for course, grades in student.getTranscript().getCourseGradeMap().items()
                        for grade in grades
                    ]
                with open(filepath, "w", encoding='utf-8') as f:
                    json.dump(data, f, ensure_ascii=False, indent=2)
            except Exception as e:
                print(f"There is a problem with the {student.getID()}.json file")
                logging.error(f"There is a problem with the {student.getID()}.json file")
                exit(0)

    def writeRequests(self):
        try:
            requests_data = []
            for student in self.__department.getStudents():
                if student.isHasRequest():
                    request = {
                        "studentID": student.getID(),
                        "courses": [course.getCourseCode() for course in student.getDraft()]
                    }
                    requests_data.append(request)

            with open("jsons/requests.json", "w", encoding='utf-8') as f:
                json.dump(requests_data, f, ensure_ascii=False, indent=2)
        except Exception as e:
            print("There is a problem with the requests.json file")
            logging.error("There is a problem with the requests.json file")
            exit(0)

    def writeStudents(self):
        filepath = "jsons/students.json"
        try:
            # load existing data
            with open(filepath, 'r', encoding='utf-8') as f:
                existingData = json.load(f)

            for data in existingData:
                student = self.__department.getStudentIDStudentMap().get(data["studentID"])
                if student:
                    data["hasRequest"] = student.isHasRequest()
                    data["labSections"] = [labSection.getLaboratorySectionCode() for labSection in
                                           student.getLabSections()]

            # write back updated data
            with open(filepath, "w", encoding='utf-8') as f:
                json.dump(existingData, f, indent=2, ensure_ascii=False)
        except Exception as e:
            print("There is a problem with the students.json file")
            logging.error("There is a problem with the students.json file")
            exit(0)
