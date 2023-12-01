public class User {
    public final int userId;
    public final String name;
    public final String type;
    public final String address;

    public User(int userId, String name, String type, String address) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }
}
