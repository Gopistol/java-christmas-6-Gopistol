package christmas.domain.policy;

public enum DiscountPolicy {

  CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인"),
  WEEKDAY_DISCOUNT("평일 할인"),
  WEEKEND_DISCOUNT("주말 할인"),
  SPECIAL_DISCOUNT("특별 할인"),
  GIVEAWAY_EVENT("증정 이벤트");

  private final String description;

  DiscountPolicy(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
