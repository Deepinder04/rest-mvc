package project.first.spring.cas.enums;

public enum InvestmentTypeEnum {
    MUTUALFUND("mutualfund"),
    GOLD("gold"),
    XTRA("p2p"),
    EPFO("epfo"),
    BANK_ACCOUNT("bankAccount"),
    FD("fd"),
    IDD("idd"),
    CONSOLIDATED("consolidated");

    private final String value;

    InvestmentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static InvestmentTypeEnum fromValue(String input) {
        for (InvestmentTypeEnum b : InvestmentTypeEnum.values()) {
            if (b.value.equals(input)) {
                return b;
            }
        }
        return null;
    }
}
