public class CategoryStats {
    public final int categoryId;
    public final double averagePrice;

    public CategoryStats(int categoryId, double averagePrice) {
        this.categoryId = categoryId;
        this.averagePrice = averagePrice;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public double getAveragePrice() {
        return averagePrice;
    }
}
