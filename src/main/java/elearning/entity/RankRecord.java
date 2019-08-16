package elearning.entity;

public class RankRecord {
    private String workName;
    private String userName;
    private String point;

    public RankRecord(String workName, String userName, String point) {
        this.workName = workName;
        this.userName = userName;
        this.point = point;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
