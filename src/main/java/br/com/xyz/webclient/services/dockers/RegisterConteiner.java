package br.com.xyz.webclient.services.dockers;

public class RegisterConteiner {


    private String imageDocker;
    private String nameConteiner;
    private Integer portConteiner;
    private String usernameDatabase;
    private String passwordDatabase;
    private String nameDatabase;
    private Boolean active;

    public RegisterConteiner() {
    }

    public RegisterConteiner(String imageDocker, String nameConteiner, Integer portConteiner, String usernameDatabase, String passwordDatabase, String nameDatabase, Boolean active) {
        this.setImageDocker(imageDocker);
        this.setNameConteiner(nameConteiner);
        this.setPortConteiner(portConteiner);
        this.setUsernameDatabase(usernameDatabase);
        this.setPasswordDatabase(passwordDatabase);
        this.setNameDatabase(nameDatabase);
        this.setActive(active);
    }

    public String getImageDocker() {
        return imageDocker;
    }

    public void setImageDocker(String imageDocker) {
        this.imageDocker = imageDocker;
    }

    public String getNameConteiner() {
        return nameConteiner;
    }

    public void setNameConteiner(String nameConteiner) {
        this.nameConteiner = nameConteiner;
    }

    public Integer getPortConteiner() {
        return portConteiner;
    }

    public void setPortConteiner(Integer portConteiner) {
        this.portConteiner = portConteiner;
    }

    public String getUsernameDatabase() {
        return usernameDatabase;
    }

    public void setUsernameDatabase(String usernameDatabase) {
        this.usernameDatabase = usernameDatabase;
    }

    public String getPasswordDatabase() {
        return passwordDatabase;
    }

    public void setPasswordDatabase(String passwordDatabase) {
        this.passwordDatabase = passwordDatabase;
    }

    public String getNameDatabase() {
        return nameDatabase;
    }

    public void setNameDatabase(String nameDatabase) {
        this.nameDatabase = nameDatabase;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "RegisterConteiner{" +
                "imageDocker='" + imageDocker + '\'' +
                ", nameConteiner='" + nameConteiner + '\'' +
                ", portConteiner=" + portConteiner +
                ", usernameDatabase='" + usernameDatabase + '\'' +
                ", passwordDatabase='" + passwordDatabase + '\'' +
                ", nameDatabase='" + nameDatabase + '\'' +
                ", active=" + active +
                '}';
    }
}