package christmas.domain.menu.category;

import core.ErrorMessage;

public enum Dessert {

  초코케이크(15_000),
  아이스크림(5_000);


  private final int prize;

  Dessert(int prize) {
    this.prize = prize;
  }

  public static boolean contains(String menu) {
    for (Dessert dessert : Dessert.values()) {
      if (dessert.name().equals(menu)) {
        return true;
      }
    }
    return false;
  }

  public static int getPrize(String menu) {
    for (Dessert dessert : Dessert.values()) {
      if (dessert.name().equals(menu)) {
        return dessert.prize;
      }
    }
    throw new IllegalArgumentException(ErrorMessage.INVALID_MENU_NAME + menu);
  }
}
