package entities.ahmed;

public class Staff {

    public int numStaff;
    public String nameStaff;
    public int phoneStaff;
    public String jobStaff;
    public boolean status;
    public int scoreStaff;

    public int service;

    public  Staff() {
    }
    public Staff(int numStaff, String nameStaff, int phoneStaff, String jobStaff, boolean status, int scoreStaff) {
        this.numStaff = numStaff;
        this.nameStaff = nameStaff;
        this.phoneStaff = phoneStaff;
        this.jobStaff = jobStaff;
        this.status = status;
        this.scoreStaff=scoreStaff;


    }

    public Staff(int numStaff, String nameStaff, int phoneStaff, String jobStaff, boolean status, int scoreStaff, int service) {
        this.numStaff = numStaff;
        this.nameStaff = nameStaff;
        this.phoneStaff = phoneStaff;
        this.jobStaff = jobStaff;
        this.status = status;
        this.scoreStaff = scoreStaff;
        this.service = service;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getNumStaff() {
        return numStaff;
    }

    public void setNumStaff(int numStaff) {
        this.numStaff = numStaff;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public void setNameStaff(String nameStaff) {
        this.nameStaff = nameStaff;
    }

    public int getPhoneStaff() {
        return phoneStaff;
    }

    public void setPhoneStaff(int phoneStaff) {
        this.phoneStaff = phoneStaff;
    }

    public String getJobStaff() {
        return jobStaff;
    }

    public void setJobStaff(String jobStaff) {
        this.jobStaff = jobStaff;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getScoreStaff() {
        return scoreStaff;
    }

    public void setScoreStaff(int scoreStaff) {
        this.scoreStaff = scoreStaff;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "numStaff=" + numStaff +
                ", nameStaff='" + nameStaff + '\'' +
                ", phoneStaff=" + phoneStaff +
                ", jobStaff='" + jobStaff + '\'' +
                ", status=" + status +
                ", scoreStaff=" + scoreStaff +
                ", service=" + service +
                '}';
    }
}
