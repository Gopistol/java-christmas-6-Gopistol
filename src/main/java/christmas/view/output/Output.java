package christmas.view.output;

import christmas.domain.badge.Badge;
import java.util.Map;

public interface Output {

  void printGuideMessage();

  void printDateRequest();

  void printOrderRequest();

  void printPreviewBenefit(int date);

  void printOrderMenu(Map<String, Integer> board);

  void printTotalOrderAmount(int price);

  void printGiveAwayMenu(Map<String, Integer> board, String category, String menu, int count);

  void printBenefitDetails(Map<String, Integer> board);

  void printTotalBenefitAmount(int amount);

  void printDiscountedPaymentAmount(int payment);

  void printEventBadge(Badge badge);

  void printErrorMessage(String message);
}
