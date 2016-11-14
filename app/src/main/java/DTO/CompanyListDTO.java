package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 30-08-2016.
 */
public class CompanyListDTO {

    String Title = "";
    String Email = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id = "";

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    String Status = "";

    public List<CompanyListDTO> getCompList() {
        return compList;
    }

    public void setCompList(List<CompanyListDTO> compList) {
        this.compList = compList;
    }

    public List<CompanyListDTO> compList = new ArrayList<>();
}
