package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sample.models.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ApiSession {

    private static final String url = "http://127.0.0.1:8080";


    public void createCompany(Company company) {
        HttpClass.PostRequest(url + "/companies", company.toJSON());
    }

    public Company getCompaniesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/companies/"+id);
        if (answer != null) {
            return companyFromJson(JsonParser.parseString(answer).getAsJsonObject());
        }
        return null;
    }
    public List<Company> getCompaniesByName(String name) {
        String answer = HttpClass.GetRequest(url + "/companies/n_"+name.replace(" ", "%20"));
        return getCompanyList(answer);
    }

    private List<Company> getCompanyList(String answer) {
        List<Company> result = new ArrayList<>();
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
        String answer = HttpClass.GetRequest(url + "/companies");
        return getCompanyList(answer);

    }

    public Company companyFromJson(JsonObject currentCompany){
        String name = currentCompany.get("name").getAsString();
        String account = currentCompany.get("account").getAsString();
        Integer money = currentCompany.get("money").getAsInt();
        Long id = currentCompany.get("id").getAsLong();
        return new Company(id, name, account, money);
    }


    public void updateCompany(Company company) {
        long id = company.getId();
        String jsonString = company.toJSON();
        HttpClass.PutRequest(url + "/companies/" + id, jsonString);
    }

    public boolean deleteCompany(Company company) {
        long id = company.getId();
        return HttpClass.DeleteRequest(url + "/companies/" + id);
    }


    public void createEmployee(Employee employee) {
        HttpClass.PostRequest(url + "/employees", employee.toJSON());
    }

    public List<Employee> getEmployees() {
        String answer = HttpClass.GetRequest(url + "/employees");
        return getEmployeeList(answer);
    }

    private List<Employee> getEmployeeList(String answer) {
        List<Employee> result = new ArrayList<>();
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
        if (answer != null) {
            return employeeFromJson(JsonParser.parseString(answer).getAsJsonObject());
        }
        return null;
    }

    public List<Employee> getEmployeesByCompanyName(String companyName) {
        String answer = HttpClass.GetRequest(url + "/employees/comp_"+companyName.replace(" ", "%20"));
        return getEmployeeList(answer);
    }
    public Integer countEmployeesByCompanyId(Long id) {
        String answer = HttpClass.GetRequest(url + "/employees/comp_id_"+id);
        return JsonParser.parseString(answer).getAsInt();
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
        Company company = companyFromJson(currentEmployee.get("company").getAsJsonObject());
        JsonArray coursesRaw =  currentEmployee.get("courses").getAsJsonArray();
        Set<Course> courses = new HashSet<>();
        for (int i = 0; i < coursesRaw.size(); i++) {
            JsonObject currentCourse = coursesRaw.get(i).getAsJsonObject();
            courses.add(courseFromJson(currentCourse));
        }
        Employee employee = (Employee) personFromJson(currentEmployee, "s");
        employee.setCompany(company);
        employee.setCourses(courses);
        return employee;
    }

    private Person personFromJson(JsonObject currentPerson, String type){
        String firstName = currentPerson.get("firstName").getAsString();
        String lastName = currentPerson.get("lastName").getAsString();
        String password = currentPerson.get("password").getAsString();
        String email = currentPerson.get("email").getAsString();
        LocalDate birthday = DateUtil.parse(currentPerson.get("birthday").getAsString());
        Long id = currentPerson.get("id").getAsLong();
        if (type.equals("s")){
            return new Employee(id, firstName, lastName, password, email, birthday);
        }
        if (type.equals("f")){
            return new Faculty(id, firstName, lastName, password, email, birthday);
        }
        if (type.equals("a")){
            return new Admin(id, firstName, lastName, password, email, birthday);
        }
        return null;
    }

    public void updateEmployee(Employee employee) {
        long id = employee.getId();
        String jsonString = employee.toJSON();
        HttpClass.PutRequest(url + "/employees/" + id, jsonString);
    }


    public boolean deleteEmployee(Employee employee) {
        long id = employee.getId();
        return HttpClass.DeleteRequest(url + "/employees/" + id);
    }


    public void createPlace(Place place) {
        HttpClass.PostRequest(url + "/places", place.toJSON());
    }

    public Place getPlacesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/places/"+id);
        if (answer != null) {
            return placeFromJson(JsonParser.parseString(answer).getAsJsonObject());
        }
        return null;
    }
    public List<Place> getPlacesByCity(String city) {
        String answer = HttpClass.GetRequest(url + "/places/city_"+city.replace(" ", "%20"));
        return getPlaceList(answer);
    }

    private List<Place> getPlaceList(String answer) {
        List<Place> result = new ArrayList<>();
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
        String answer = HttpClass.GetRequest(url + "/places");
        return getPlaceList(answer);

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
        long id = place.getId();
        return HttpClass.DeleteRequest(url + "/places/" + id);
    }


    public void createFaculty(Faculty faculty) {
        HttpClass.PostRequest(url + "/faculties", faculty.toJSON());
    }

    public Faculty getFacultiesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/faculties/"+id);
        if (answer != null) {
            return facultyFromJson(JsonParser.parseString(answer).getAsJsonObject());
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
        return (Faculty) personFromJson(currentFaculty, "f");
    }


    public void updateFaculty(Faculty faculty) {
        long id = faculty.getId();
        String jsonString = faculty.toJSON();
        HttpClass.PutRequest(url + "/faculties/" + id, jsonString);
    }

    public boolean deleteFaculty(Faculty faculty) {
        long id = faculty.getId();
        return HttpClass.DeleteRequest(url + "/faculties/" + id);
    }

    public void createCourseInfo(CourseInfo courseInfo) {
        HttpClass.PostRequest(url + "/courseInfos", courseInfo.toJSON());
    }

    public CourseInfo getCourseInfosById(Long id) {
        String answer = HttpClass.GetRequest(url + "/courseInfos/"+id);
        if (answer != null) {
            return courseInfoFromJson(JsonParser.parseString(answer).getAsJsonObject());
        }
        return null;
    }
    public CourseInfo getCourseInfosByName(String name) {
        String answer = HttpClass.GetRequest(url + "/courseInfos/n_"+name.replace(" ", "%20"));
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
        long id = courseInfo.getId();
        return HttpClass.DeleteRequest(url + "/courseInfos/" + id);
    }

    public void createCourse(Course course) {
        HttpClass.PostRequest(url + "/courses", course.toJSON());
    }

    public Course getCoursesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/courses/"+id);
        if (answer != null) {
            return courseFromJson(JsonParser.parseString(answer).getAsJsonObject());
        }
        return null;
    }


    public List<Course> getCoursesByName(String courseInfo) {
        String answer = HttpClass.GetRequest(url + "/courses/ci_"+courseInfo.replace(" ", "%20"));
        return getCourseList(answer);

    }

    private List<Course> getCourseList(String answer) {
        List<Course> result = new ArrayList<>();
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
        String answer = HttpClass.GetRequest(url + "/courses");
        return getCourseList(answer);

    }

    public Course courseFromJson(JsonObject currentCourse){
        LocalDate startDate = DateUtil.parse(currentCourse.get("startDate").getAsString());
        LocalDate finishDate = DateUtil.parse(currentCourse.get("finishDate").getAsString());
        CourseInfo courseInfo = courseInfoFromJson(currentCourse.get("courseInfo").getAsJsonObject());
        Faculty faculty = facultyFromJson(currentCourse.get("faculty").getAsJsonObject());
        Integer price = currentCourse.get("price").getAsInt();
        Long id = currentCourse.get("id").getAsLong();
        JsonArray employeesRaw =  currentCourse.get("employees").getAsJsonArray();
        Set<Employee> employees = new HashSet<>();
        for (int i = 0; i < employeesRaw.size(); i++) {
            JsonObject currentEmployee = employeesRaw.get(i).getAsJsonObject();
            employees.add(employeeFromJson(currentEmployee));
        }
        Course course =  new Course(id, startDate, finishDate, price, courseInfo, faculty);
        course.setEmployees(employees);
        return course;
    }


    public void updateCourse(Course course) {
        long id = course.getId();
        String jsonString = course.toJSON();
        HttpClass.PutRequest(url + "/courses/" + id, jsonString);
    }

    public boolean deleteCourse(Course course) {
        long id = course.getId();
        return HttpClass.DeleteRequest(url + "/courses/" + id);
    }

    public void createTimetable(Timetable timetable) {
        HttpClass.PostRequest(url + "/timetables", timetable.toJSON());
    }

    public Timetable getTimetablesById(Long id) {
        String answer = HttpClass.GetRequest(url + "/timetables/"+id);
        if (answer != null) {
            return timetableFromJson(JsonParser.parseString(answer).getAsJsonObject());
        }
        return null;
    }
    public List<Timetable> getTimetablesByCourseName(String name) {
        String answer = HttpClass.GetRequest(url + "/timetables/cn_"+name.replace(" ", "%20"));
        return getTimetableList(answer);
    }

    private List<Timetable> getTimetableList(String answer) {
        List<Timetable> result = new ArrayList<>();
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
        String answer = HttpClass.GetRequest(url + "/timetables/date_"+date);
        return getTimetableList(answer);
    }

    public List<Timetable> getTimetablesByCourseNameAndDate(String name, LocalDate date) {
        String answer = HttpClass.GetRequest(url + "/timetables/cn_"+name.replace(" ", "%20")+"/date_"+date);
        return getTimetableList(answer);
    }

    public List<Timetable> getTimetables() {
        String answer = HttpClass.GetRequest(url + "/timetables");
        return getTimetableList(answer);

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
        long id = timetable.getId();
        return HttpClass.DeleteRequest(url + "/timetables/" + id);
    }

    public void createAdmin(Admin admin) {
        HttpClass.PostRequest(url + "/admins", admin.toJSON());
    }

    public Admin getAdminsById(Long id) {
        String answer = HttpClass.GetRequest(url + "/admins/"+id);
        if (answer != null) {
            return adminFromJson(JsonParser.parseString(answer).getAsJsonObject());
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
        return (Admin) personFromJson(currentAdmin, "a");
    }


    public void updateAdmin(Admin admin) {
        long id = admin.getId();
        String jsonString = admin.toJSON();
        HttpClass.PutRequest(url + "/admins/" + id, jsonString);
    }

    public boolean deleteAdmin(Admin admin) {
        long id = admin.getId();
        return HttpClass.DeleteRequest(url + "/admins/" + id);
    }
}
