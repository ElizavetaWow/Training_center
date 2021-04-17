package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sample.models.Company;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiSession {

    private static final String url = "http://127.0.0.1:8080";


    public void createCompany(Company company) {
        HttpClass.PostRequest(url + "/companies", company.toJSON());
    }

    /**
     * Получение персоны по опеределенному id
     * Использет GET
     * @return
     */
    //public Company GetCompany(String id){

    //}


    public List<Company> getCompany() {
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
}
