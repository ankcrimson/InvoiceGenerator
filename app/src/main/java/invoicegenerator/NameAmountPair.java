package invoicegenerator;

public class NameAmountPair {
    private String name;
    private String amount;

    public NameAmountPair(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }
}
