package akatsuki.immunizationsystem.utils;

public class DbConnection {
    private String username, password, url, driver;

    public DbConnection(String username, String password, String url, String driver) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
