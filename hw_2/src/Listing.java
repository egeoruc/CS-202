public class Listing {
    public final int listingId;
    public final int sellerId;
    public final int productId;
    public final int price;
    public final int stock;

    public Listing(int listingId, int sellerId, int productId, int price, int stock) {
        this.listingId = listingId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.price = price;
        this.stock = stock;
    }

    public int getListingId() {
        return listingId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public int getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
