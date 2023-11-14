package christmas.domain.menu.category;

import core.ErrorMessage;

public enum Main {

  티본스테이크(55_000),
  바비큐립(54_000),
  해산물파스타(35_000),
  크리스마스파스타(25_000);

  private final int prize;

  Main(int prize) {
    this.prize = prize;
  }

  public static boolean contains(String menu) {
    for (Main main : Main.values()) {
      if (main.name().equals(menu)) {
        return true;
      }
    }
    return false;
  }

  public static int getPrize(String menu) {
    for (Main mainMenu : Main.values()) {
      if (mainMenu.name().equals(menu)) {
        return mainMenu.prize;
      }
    }
    throw new IllegalArgumentException(ErrorMessage.INVALID_MENU_NAME + menu);
  }
}
