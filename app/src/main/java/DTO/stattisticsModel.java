package DTO;

import java.util.List;

/**
 * Created by DigiT-25 on 25-06-2016.
 */
public class stattisticsModel {


    String Activityname;
    String points;
    String count;
    String total;
    String status;
    List<stattisticsModel> advertiseDTOs = null;

    public String getActivityname() {
        return Activityname;
    }

    public void setActivityname(String activityname) {
        Activityname = activityname;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public List<stattisticsModel> getAdvertiseDTOs() {
        return advertiseDTOs;
    }

    public void setAdvertiseDTOs(List<stattisticsModel> advertiseDTOs) {
        this.advertiseDTOs = advertiseDTOs;
    }
}
