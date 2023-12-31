package christmas.domain.menu;

import christmas.domain.menu.category.Appetizer;
import christmas.domain.menu.category.Dessert;
import christmas.domain.menu.category.Drink;
import christmas.domain.menu.category.Main;
import christmas.core.ErrorMessage;

public class MenuManager {

  public static int getPriceByMenu(String menu) {
    if (Appetizer.contains(menu)) {
      return Appetizer.getPrize(menu);
    }
    if (Dessert.contains(menu)) {
      return Dessert.getPrize(menu);
    }
    if (Drink.contains(menu)) {
      return Drink.getPrize(menu);
    }
    if (Main.contains(menu)) {
      return Main.getPrize(menu);
    }
    return 0;
  }
}
