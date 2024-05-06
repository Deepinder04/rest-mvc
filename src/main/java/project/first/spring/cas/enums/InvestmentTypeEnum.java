package project.first.spring.cas.enums;

public enum InvestmentTypeEnum {
    mutualfund("mutualfund"),
    gold("gold"),
    p2p("p2p"),
    epfo("epfo"),
    bankAccount("bankAccount"),
    fd("fd"),
    idd("idd"),
    consolidated("consolidated");

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
