package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sample.models.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ApiSession {

    private static final String url = "http://127.0.0.1:8080";


    public void createCompany(Company company) {
        HttpClass.PostRequest(url + "/companies", company.toJSON());
    }

    public Company getCompaniesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/companies/"+id);
        JsonObject currentCompany = JsonParser.parseString(answer).getAsJsonObject();
        if (currentCompany != null){
            return companyFromJson(currentCompany);
        }
        return null;
    }
    public List<Company> getCompaniesByName(String name) {
        List<Company> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/companies/n_"+name);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentCompany = jsonAnswer.get(i).getAsJsonObject();
                result.add(companyFromJson(currentCompany));
            }
        }
        return result;
    }

    public List<Company> getCompanies() {
        List<Company> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/companies");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentCompany = jsonAnswer.get(i).getAsJsonObject();
                result.add(companyFromJson(currentCompany));
            }
        }
        return result;

    }

    public Company companyFromJson(JsonObject currentCompany){
        String name = currentCompany.get("name").getAsString();
        String account = currentCompany.get("account").getAsString();
        Long id = currentCompany.get("id").getAsLong();
        return new Company(id, name, account);
    }


    public void updateCompany(Company company) {
        long id = company.getId();
        String jsonString = company.toJSON();
        HttpClass.PutRequest(url + "/companies/" + id, jsonString);
    }

    public boolean deleteCompany(Company company) {
        Long id = company.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/companies/" + id);
    }


    public void createEmployee(Employee employee) {
        HttpClass.PostRequest(url + "/employees", employee.toJSON());
    }

    public List<Employee> getEmployees() {
        List<Employee> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/employees");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentEmployee = jsonAnswer.get(i).getAsJsonObject();
                result.add(employeeFromJson(currentEmployee));
            }
        }
        return result;
    }

    public Employee getEmployeesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/employees/"+id);
        JsonObject currentEmployee = JsonParser.parseString(answer).getAsJsonObject();
        if (currentEmployee != null){
            return employeeFromJson(currentEmployee);
        }
        return null;
    }

    public List<Employee> getEmployeesByCompanyName(String companyName) {
        List<Employee> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/employees/comp_"+companyName);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentEmployee = jsonAnswer.get(i).getAsJsonObject();
                result.add(employeeFromJson(currentEmployee));
            }
        }
        return result;
    }
    public Employee getEmployeesByEmail(String email) {
        String answer = HttpClass.GetRequest(url + "/employees/email_"+email);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            JsonObject currentEmployee = jsonAnswer.get(0).getAsJsonObject();
            return employeeFromJson(currentEmployee);
        }
        return null;
    }


    public Employee employeeFromJson(JsonObject currentEmployee){
        String firstName = currentEmployee.get("firstName").getAsString();
        String lastName = currentEmployee.get("lastName").getAsString();
        String password = currentEmployee.get("password").getAsString();
        String email = currentEmployee.get("email").getAsString();
        LocalDate birthday = DateUtil.parse(currentEmployee.get("birthday").getAsString());
        Company company = companyFromJson(currentEmployee.get("company").getAsJsonObject());
        Long id = currentEmployee.get("id").getAsLong();
        return new Employee(id, firstName, lastName, password, email, birthday, company);
    }

    public void updateEmployee(Employee employee) {
        Long id = employee.getId();
        String jsonString = employee.toJSON();
        HttpClass.PutRequest(url + "/employees/" + id, jsonString);
    }

    public boolean deleteEmployee(Employee employee) {
        Long id = employee.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/employees/" + id);
    }


    public void createPlace(Place place) {
        HttpClass.PostRequest(url + "/places", place.toJSON());
    }

    public Place getPlacesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/places/"+id);
        JsonObject currentPlace = JsonParser.parseString(answer).getAsJsonObject();
        if (currentPlace != null){
            return placeFromJson(currentPlace);
        }
        return null;
    }
    public List<Place> getPlacesByCity(String city) {
        List<Place> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/places/city_"+city);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentPlace = jsonAnswer.get(i).getAsJsonObject();
                result.add(placeFromJson(currentPlace));
            }
        }
        return result;
    }

    public List<Place> getPlaces() {
        List<Place> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/places");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentPlace = jsonAnswer.get(i).getAsJsonObject();
                result.add(placeFromJson(currentPlace));
            }
        }
        return result;

    }

    public Place placeFromJson(JsonObject currentPlace){
        String city = currentPlace.get("city").getAsString();
        String street = currentPlace.get("street").getAsString();
        String building = currentPlace.get("building").getAsString();
        int room = currentPlace.get("room").getAsInt();
        Long id = currentPlace.get("id").getAsLong();
        return new Place(id, city, street, building, room);
    }


    public void updatePlace(Place place) {
        long id = place.getId();
        String jsonString = place.toJSON();
        HttpClass.PutRequest(url + "/places/" + id, jsonString);
    }

    public boolean deletePlace(Place place) {
        Long id = place.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/places/" + id);
    }


    public void createFaculty(Faculty faculty) {
        HttpClass.PostRequest(url + "/faculties", faculty.toJSON());
    }

    public Faculty getFacultiesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/faculties/"+id);
        JsonObject currentFaculty = JsonParser.parseString(answer).getAsJsonObject();
        if (currentFaculty != null){
            return facultyFromJson(currentFaculty);
        }
        return null;
    }

    public Faculty getFacultiesByEmail(String email) {
        String answer = HttpClass.GetRequest(url + "/faculties/email_"+email);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            JsonObject currentFaculty = jsonAnswer.get(0).getAsJsonObject();
            return facultyFromJson(currentFaculty);
        }
        return null;
    }


    public List<Faculty> getFaculties() {
        List<Faculty> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/faculties");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentFaculty = jsonAnswer.get(i).getAsJsonObject();
                result.add(facultyFromJson(currentFaculty));
            }
        }
        return result;

    }

    public Faculty facultyFromJson(JsonObject currentFaculty){
        String firstName = currentFaculty.get("firstName").getAsString();
        String lastName = currentFaculty.get("lastName").getAsString();
        String password = currentFaculty.get("password").getAsString();
        String email = currentFaculty.get("email").getAsString();
        LocalDate birthday = DateUtil.parse(currentFaculty.get("birthday").getAsString());
        Long id = currentFaculty.get("id").getAsLong();
        return new Faculty(id, firstName, lastName, password, email, birthday);
    }


    public void updateFaculty(Faculty faculty) {
        long id = faculty.getId();
        String jsonString = faculty.toJSON();
        HttpClass.PutRequest(url + "/faculties/" + id, jsonString);
    }

    public boolean deleteFaculty(Faculty faculty) {
        Long id = faculty.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/faculties/" + id);
    }

    public void createCourseInfo(CourseInfo courseInfo) {
        HttpClass.PostRequest(url + "/courseInfos", courseInfo.toJSON());
    }

    public CourseInfo getCourseInfosById(Long id) {
        String answer = HttpClass.GetRequest(url + "/courseInfos/"+id);
        JsonObject currentCourseInfo = JsonParser.parseString(answer).getAsJsonObject();
        if (currentCourseInfo != null){
            return courseInfoFromJson(currentCourseInfo);
        }
        return null;
    }
    public CourseInfo getCourseInfosByName(String name) {
        String answer = HttpClass.GetRequest(url + "/courseInfos/n_"+name.replace(" ", "-"));
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            JsonObject currentCourseInfo = jsonAnswer.get(0).getAsJsonObject();
            return courseInfoFromJson(currentCourseInfo);
        }
        return null;
    }

    public List<CourseInfo> getCourseInfos() {
        List<CourseInfo> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/courseInfos");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentCourseInfo = jsonAnswer.get(i).getAsJsonObject();
                result.add(courseInfoFromJson(currentCourseInfo));
            }
        }
        return result;

    }

    public CourseInfo courseInfoFromJson(JsonObject currentCourseInfo){
        String name = currentCourseInfo.get("name").getAsString();
        Long id = currentCourseInfo.get("id").getAsLong();
        return new CourseInfo(id, name);
    }


    public void updateCourseInfo(CourseInfo courseInfo) {
        long id = courseInfo.getId();
        String jsonString = courseInfo.toJSON();
        HttpClass.PutRequest(url + "/courseInfos/" + id, jsonString);
    }

    public boolean deleteCourseInfo(CourseInfo courseInfo) {
        Long id = courseInfo.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/courseInfos/" + id);
    }

    public void createCourse(Course course) {
        HttpClass.PostRequest(url + "/courses", course.toJSON());
    }

    public Course getCoursesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/courses/"+id);
        JsonObject currentCourse = JsonParser.parseString(answer).getAsJsonObject();
        if (currentCourse != null){
            return courseFromJson(currentCourse);
        }
        return null;
    }


    public List<Course> getCoursesByName(String courseInfo) {
        List<Course> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/courses/ci_"+courseInfo);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentCourse = jsonAnswer.get(i).getAsJsonObject();
                result.add(courseFromJson(currentCourse));
            }
        }
        return result;

    }

    public List<Course> getCourses() {
        List<Course> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/courses");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentCourse = jsonAnswer.get(i).getAsJsonObject();
                result.add(courseFromJson(currentCourse));
            }
        }
        return result;

    }

    public Course courseFromJson(JsonObject currentCourse){
        LocalDate startDate = DateUtil.parse(currentCourse.get("startDate").getAsString());
        LocalDate finishDate = DateUtil.parse(currentCourse.get("finishDate").getAsString());
        CourseInfo courseInfo = courseInfoFromJson(currentCourse.get("courseInfo").getAsJsonObject());
        Faculty faculty = facultyFromJson(currentCourse.get("faculty").getAsJsonObject());
        Long id = currentCourse.get("id").getAsLong();
        return new Course(id, startDate, finishDate, courseInfo, faculty);
    }


    public void updateCourse(Course course) {
        long id = course.getId();
        String jsonString = course.toJSON();
        HttpClass.PutRequest(url + "/courses/" + id, jsonString);
    }

    public boolean deleteCourse(Course course) {
        Long id = course.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/courses/" + id);
    }

    public void createTimetable(Timetable timetable) {
        HttpClass.PostRequest(url + "/timetables", timetable.toJSON());
    }

    public Timetable getTimetablesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/timetables/"+id);
        JsonObject currentTimetable = JsonParser.parseString(answer).getAsJsonObject();
        if (currentTimetable != null){
            return timetableFromJson(currentTimetable);
        }
        return null;
    }
    public List<Timetable> getTimetablesByCourseName(String name) {
        List<Timetable> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/timetables/cn_"+name);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentTimetable = jsonAnswer.get(i).getAsJsonObject();
                result.add(timetableFromJson(currentTimetable));
            }
        }
        return result;
    }

    public List<Timetable> getTimetablesByDate(LocalDate date) {
        List<Timetable> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/timetables/date_"+date);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentTimetable = jsonAnswer.get(i).getAsJsonObject();
                result.add(timetableFromJson(currentTimetable));
            }
        }
        return result;
    }

    public List<Timetable> getTimetablesByCourseNameAndDate(String name, LocalDate date) {
        List<Timetable> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/timetables/cn_"+name+"/date_"+date);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentTimetable = jsonAnswer.get(i).getAsJsonObject();
                result.add(timetableFromJson(currentTimetable));
            }
        }
        return result;
    }

    public List<Timetable> getTimetables() {
        List<Timetable> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/timetables");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentTimetable = jsonAnswer.get(i).getAsJsonObject();
                result.add(timetableFromJson(currentTimetable));
            }
        }
        return result;

    }

    public Timetable timetableFromJson(JsonObject currentTimetable){
        LocalDate date = DateUtil.parse(currentTimetable.get("date").getAsString());
        LocalTime time = TimeUtil.parse(currentTimetable.get("time").getAsString());
        Course course = courseFromJson(currentTimetable.get("course").getAsJsonObject());
        Place place = placeFromJson(currentTimetable.get("place").getAsJsonObject());
        Long id = currentTimetable.get("id").getAsLong();
        return new Timetable(id, course, place, time, date);
    }


    public void updateTimetable(Timetable timetable) {
        long id = timetable.getId();
        String jsonString = timetable.toJSON();
        HttpClass.PutRequest(url + "/timetables/" + id, jsonString);
    }

    public boolean deleteTimetable(Timetable timetable) {
        Long id = timetable.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/timetables/" + id);
    }

    public void createAdmin(Admin admin) {
        HttpClass.PostRequest(url + "/admins", admin.toJSON());
    }

    public Admin getAdminsById(Long id) {
        String answer = HttpClass.GetRequest(url + "/admins/"+id);
        JsonObject currentAdmin = JsonParser.parseString(answer).getAsJsonObject();
        if (currentAdmin != null){
            return adminFromJson(currentAdmin);
        }
        return null;
    }

    public Admin getAdminsByEmail(String email) {
        String answer = HttpClass.GetRequest(url + "/admins/email_"+email);
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
            JsonObject currentAdmin = jsonAnswer.get(0).getAsJsonObject();
            return adminFromJson(currentAdmin);
        }
        return null;
    }


    public List<Admin> getAdmins() {
        List<Admin> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/admins");
        if (answer != null) {
            JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

            for (int i = 0; i < jsonAnswer.size(); i++) {
                JsonObject currentAdmin = jsonAnswer.get(i).getAsJsonObject();
                result.add(adminFromJson(currentAdmin));
            }
        }
        return result;

    }

    public Admin adminFromJson(JsonObject currentAdmin){
        String firstName = currentAdmin.get("firstName").getAsString();
        String lastName = currentAdmin.get("lastName").getAsString();
        String password = currentAdmin.get("password").getAsString();
        String email = currentAdmin.get("email").getAsString();
        LocalDate birthday = DateUtil.parse(currentAdmin.get("birthday").getAsString());
        Long id = currentAdmin.get("id").getAsLong();
        return new Admin(id, firstName, lastName, password, email, birthday);
    }


    public void updateAdmin(Admin admin) {
        long id = admin.getId();
        String jsonString = admin.toJSON();
        HttpClass.PutRequest(url + "/admins/" + id, jsonString);
    }

    public boolean deleteAdmin(Admin admin) {
        Long id = admin.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(url + "/admins/" + id);
    }
}
