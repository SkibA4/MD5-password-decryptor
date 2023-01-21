public class User {

    private String userId;
    private String hashedPassword;
    private String userEmail;
    private String username;
    private boolean hacked = false;

    public User(String userId, String hashedPassword, String userEmail, String username) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
        this.userEmail = userEmail;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getHacked() { return hacked; }

    public void setHacked(boolean hacked) { this.hacked = hacked; }
}
