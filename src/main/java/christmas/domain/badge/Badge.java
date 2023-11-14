package christmas.domain.badge;

public enum Badge {

  NOTHING("없음"),
  STAR("별"),
  TREE("트리"),
  SANTA("산타");

  private final String name;

  Badge(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static Badge getBadgeByBenefitAmount(int benefitAmount) {
    if (benefitAmount < 5_000) {
      return NOTHING;
    }
    if (benefitAmount < 10_000) {
      return STAR;
    }
    if (benefitAmount < 20_000) {
      return TREE;
    }
    return SANTA;
  }
}
