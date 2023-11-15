package christmas.domain.menu.category;

import christmas.core.ErrorMessage;

public enum Appetizer {

  양송이수프(6_000),
  타파스(5_500),
  시저샐러드(8_000);

  private final int prize;

  Appetizer(int prize) {
    this.prize = prize;
  }

  public static boolean contains(String menu) {
    for (Appetizer appetizer : Appetizer.values()) {
      if (appetizer.name().equals(menu)) {
        return true;
      }
    }
    return false;
  }

  public static int getPrize(String menu) {
    for (Appetizer appetizer : Appetizer.values()) {
      if (appetizer.name().equals(menu)) {
        return appetizer.prize;
      }
    }
    throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_FORMAT + menu);
  }
}
