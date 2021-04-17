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
        JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
        if (jsonAnswer.size() > 0){
            JsonObject currentCompany = jsonAnswer.get(0).getAsJsonObject();
            String name = currentCompany.get("name").getAsString();
            String account = currentCompany.get("account").getAsString();
            Long id = currentCompany.get("id").getAsLong();
            Company result = new Company(id, name, account);
            return result;
        }
        return null;
    }

    public List<Company> getCompanies() {
        List<Company> result = new ArrayList<>();
        String answer = HttpClass.GetRequest(url + "/companies");
        JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();

        for (int i = 0; i < jsonAnswer.size(); i++) {
            JsonObject currentCompany = jsonAnswer.get(i).getAsJsonObject();

            String name = currentCompany.get("name").getAsString();
            String account = currentCompany.get("account").getAsString();
            Long id = currentCompany.get("id").getAsLong();

            Company newCompany = new Company(id, name, account);
            result.add(newCompany);
        }
        return result;
    }

    public void updateCompany(Company company) {
        Long id = company.getId();
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

            String firstName = currentEmployee.get("firstName").getAsString();
            String lastName = currentEmployee.get("lastName").getAsString();
            String password = currentEmployee.get("password").getAsString();
            String email = currentEmployee.get("email").getAsString();
            LocalDate birthday = DateUtil.parse(currentEmployee.get("birthday").getAsString());
            Long companyId = currentEmployee.get("companyId").getAsLong();
            Long id = currentEmployee.get("id").getAsLong();

            Employee newEmployee = new Employee(id, firstName, lastName, password, email, birthday, companyId);
            result.add(newEmployee);
        }
        return result;
    }

    public Employee getEmployees(Long id) {
        String answer = HttpClass.GetRequest(url + "/employees/"+id);
        JsonArray jsonAnswer = JsonParser.parseString(answer).getAsJsonArray();
        if (jsonAnswer.size() > 0){
            JsonObject currentEmployee = jsonAnswer.get(i).getAsJsonObject();

            String firstName = currentEmployee.get("firstName").getAsString();
            String lastName = currentEmployee.get("lastName").getAsString();
            String password = currentEmployee.get("password").getAsString();
            String email = currentEmployee.get("email").getAsString();
            LocalDate birthday = DateUtil.parse(currentEmployee.get("birthday").getAsString());
            Long companyId = currentEmployee.get("companyId").getAsLong();
            Long id = currentEmployee.get("id").getAsLong();

            Employee result = new Employee(id, firstName, lastName, password, email, birthday, companyId);
            return result;
        }
        return null;
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
