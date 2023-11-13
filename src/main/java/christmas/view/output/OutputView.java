package christmas.view.output;

import christmas.domain.badge.Badge;
import core.GuideMessage;
import core.constant.TextConstants;
import java.text.NumberFormat;
import java.util.Map;

public class OutputView extends TextConstants implements Output {

  @Override
  public void printGuideMessage() {
    print(GuideMessage.INTRO);
  }

  @Override
  public void printDateRequest() {
    print(GuideMessage.EXPECTED_VISIT_DATE);
  }

  @Override
  public void printOrderRequest() {
    print(GuideMessage.ORDER_MENU_NAME_AMOUNT);
  }

  @Override
  public void printPreviewBenefit(int date) {
    print(String.format(GuideMessage.PREVIEW_EVENT_BENEFITS, date));
  }

  @Override
  public void printOrderMenu(Map<String, Integer> board) {
    print(GuideMessage.ORDER_MENU);
    StringBuilder sb = new StringBuilder();

    for (String menu : board.keySet()) {
      int menuCount = board.get(menu);
      sb.append(menu);
      sb.append(SPACE);
      sb.append(menuCount);
      sb.append(UNIT + NEW_LINE);
    }

    print(sb.toString());
  }

  @Override
  public void printTotalOrderAmount(int price) {
    print(NEW_LINE + GuideMessage.TOTAL_PRICE_BEFORE_DISCOUNT);
    print(priceFormatter(price) + MONEY_UNIT + NEW_LINE);
  }

  @Override
  public void printGiveAwayMenu(Map<String, Integer> board, String category, String menu,
      int count) {
    print(NEW_LINE + GuideMessage.GIVEAWAY_MENU);

    if (board.size() == 0) {
      print(NOTHING + NEW_LINE);
      return;
    }

    print(menu + SPACE + count + MENU_UNIT + NEW_LINE);
  }

  @Override
  public void printBenefitDetails(Map<String, Integer> board) {
    print(NEW_LINE + GuideMessage.BENEFIT_DETAILS);

    if (board.size() == 0) {
      print(NOTHING + NEW_LINE);
      return;
    }

    StringBuilder sb = new StringBuilder();

    for (String category : board.keySet()) {
      int discountAmount = board.get(category);
      sb.append(category);
      sb.append(COLON);
      sb.append(addMinus(discountAmount));
      sb.append(priceFormatter(discountAmount));
      sb.append(MONEY_UNIT);
      sb.append(NEW_LINE);
    }

    print(sb.toString());
  }

  @Override
  public void printTotalBenefitAmount(int price) {
    print(NEW_LINE + GuideMessage.TOTAL_BENEFIT_AMOUNT + addMinus(price) + priceFormatter(price)
        + MONEY_UNIT
        + NEW_LINE);
  }

  @Override
  public void printDiscountedPaymentAmount(int payment) {
    print(NEW_LINE + GuideMessage.EXPECTED_PAYMENT_AFTER_DISCOUNT + priceFormatter(payment)
        + MONEY_UNIT + NEW_LINE);
  }

  @Override
  public void printEventBadge(Badge badge) {
    print(NEW_LINE + GuideMessage.EVENT_BADGE + badge.getName());
  }

  @Override
  public void printErrorMessage(String message) {
    print(message);
  }

  private String priceFormatter(int price) {
    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    return numberFormat.format(price);
  }

  private String addMinus(int amount) {
    if (amount > 0) {
      return MINUS;
    }
    return "";
  }

  private void print(Object value) {
    System.out.print(value);
  }
}
