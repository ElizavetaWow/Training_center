package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sample.models.Company;
import sample.models.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiSession {

    private static final String url = "http://127.0.0.1:8080";


    public void createCompany(Company company) {
        HttpClass.PostRequest(url + "/companies", company.toJSON());
    }

    public Company getCompanies(Long id) {
        String answer = HttpClass.GetRequest(url + "/companies/"+id);
        JsonObject currentCompany = JsonParser.parseString(answer).getAsJsonObject();
        if (currentCompany != null){
            return companyFromJson(currentCompany);
        }
        return null;
    }

    public List<Company> getCompanies() {
        List<Company> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/companies");
        JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

        for (int i = 0; i < jsonAnswer.size(); i++) {
            JsonObject currentCompany = jsonAnswer.get(i).getAsJsonObject();
            result.add(companyFromJson(currentCompany));
        }
        return result;
    }

    public Company companyFromJson(JsonObject currentCompany){
        String name = currentCompany.get("name").getAsString();
        String account = currentCompany.get("account").getAsString();
        Long id = currentCompany.get("id").getAsLong();
        return new Company(id, name, account);
    }

    public List<Company> getCompanies(String field, String value) {
        List<Company> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/companies");
        JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

        for (int i = 0; i < jsonAnswer.size(); i++) {
            JsonObject currentCompany = jsonAnswer.get(i).getAsJsonObject();
            if (currentCompany.get(field).getAsString().equals(value)){
                result.add(companyFromJson(currentCompany));
            }
        }
        return result;
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
        JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
        for (int i = 0; i < jsonAnswer.size(); i++) {
            JsonObject currentEmployee = jsonAnswer.get(i).getAsJsonObject();
            result.add(employeeFromJson(currentEmployee));
        }
        return result;
    }

    public Employee getEmployees(Long id) {
        String answer = HttpClass.GetRequest(url + "/employees/"+id);
        JsonObject currentEmployee = JsonParser.parseString(answer).getAsJsonObject();
        if (currentEmployee != null){
            return employeeFromJson(currentEmployee);
        }
        return null;
    }

    public List<Employee> getEmployees(String field, String value) {
        List<Employee> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/employees");
        JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

        for (int i = 0; i < jsonAnswer.size(); i++) {
            JsonObject currentEmployee = jsonAnswer.get(i).getAsJsonObject();
            if (field.equals("company")){
                if (currentEmployee.get("company").getAsJsonObject().get("id").getAsString().equals(value)){
                    result.add(employeeFromJson(currentEmployee));
                }
            }
            else{
                if (currentEmployee.get(field).getAsString().equals(value)){
                    result.add(employeeFromJson(currentEmployee));
                }
            }
        }
        return result;
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
}
