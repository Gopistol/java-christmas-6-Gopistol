package christmas.domain.menu.category;

import christmas.core.ErrorMessage;

public enum Drink {

  제로콜라(3_000),
  레드와인(60_000),
  샴페인(25_000);

  private final int prize;

  Drink(int prize) {
    this.prize = prize;
  }

  public static boolean contains(String menu) {
    for (Drink drink : Drink.values()) {
      if (drink.name().equals(menu)) {
        return true;
      }
    }
    return false;
  }

  public static int getPrize(String menu) {
    for (Drink drink : Drink.values()) {
      if (drink.name().equals(menu)) {
        return drink.prize;
      }
    }
    throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_FORMAT + menu);
  }
}
