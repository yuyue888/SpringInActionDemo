package core.enums;

/**
 * Created by ssc on 2017/7/20 0020.
 */
public enum CountModifyType {

    INCREASE("增加" , 0),
    DECREASE("减少" , 1);

    private final String label;
    private final int value;

    CountModifyType(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }
}
