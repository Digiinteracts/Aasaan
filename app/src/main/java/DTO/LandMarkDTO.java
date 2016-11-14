package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 30-08-2016.
 */
public class LandMarkDTO {

    String id;
    String name;

    public List<LandMarkDTO> getLandMarkDTOList() {
        return landMarkDTOList;
    }

    public void setLandMarkDTOList(List<LandMarkDTO> landMarkDTOList) {
        this.landMarkDTOList = landMarkDTOList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    List<LandMarkDTO> landMarkDTOList = new ArrayList<>();
}
