import java.util.Date;

public class Order {

    public final int orderId;
    public final int listingId;
    public final int userId;
    public final Date date;

    public Order(int orderId, int listingId, int userId, Date date) {
        this.orderId = orderId;
        this.listingId = listingId;
        this.userId = userId;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getListingId() {
        return listingId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }
}
