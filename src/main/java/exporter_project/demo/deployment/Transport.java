package exporter_project.demo.deployment;

public class Transport {

    private boolean fakeServer;
    private String server;
    private int port;
    private String user;
    private String password;

    public boolean isFakeServer() {
        return fakeServer;
    }

    public void setFakeServer(boolean fakeServer) {
        this.fakeServer = fakeServer;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
