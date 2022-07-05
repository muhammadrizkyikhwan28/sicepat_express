package helpers;

public class ComboBoxitem {
    private int value;
    private String text;

    public ComboBoxitem(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
