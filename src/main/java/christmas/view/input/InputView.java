package christmas.view.input;

import camp.nextstep.edu.missionutils.Console;
import christmas.core.ErrorMessage;
import christmas.domain.policy.PlannerPolicy;
import christmas.core.constant.TextConstants;
import java.util.StringTokenizer;


public class InputView extends TextConstants implements Input {

  @Override
  public int readDate() {
    String input = input();
    return validateDate(input);
  }

  @Override
  public String readOrder() {
    String order = input();
    validateOrder(order);
    return order;
  }

  private String input() {
    return Console.readLine();
  }

  private int validateDate(String input) {
    validateNumeric(input);
    int date = Integer.parseInt(input);

    if (date >= 1 && date <= 31) {
      return date;
    }

    throw new IllegalArgumentException(
        ErrorMessage.ERROR_MESSAGE_PREFIX + ErrorMessage.INVALID_DATE_FORMAT);
  }

  private void validateNumeric(String input) {
    try {
      Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          ErrorMessage.ERROR_MESSAGE_PREFIX + ErrorMessage.INVALID_DATE_FORMAT);
    }
  }

  private void validateOrder(String order) {
    // 메뉴 형식 검사
    StringTokenizer st = new StringTokenizer(order, ORDER_UNIT_SEPARATOR);
    int countTokens = st.countTokens();

    for (int i = 0; i < countTokens; i++) {
      String orderUnit = st.nextToken();
      StringTokenizer orderUnitTokens = new StringTokenizer(orderUnit, SEPARATOR);

      validateOrderFormat(orderUnitTokens.countTokens());

      String menu = orderUnitTokens.nextToken();
      String menuCount = orderUnitTokens.nextToken();
      validateOrderCountFormat(menuCount);
      validateOrderCount(Integer.parseInt(menuCount));
    }
  }

  private void validateOrderFormat(int countTokens) {

    if (countTokens == PlannerPolicy.INPUT_TOKEN_COUNT) {
      return;
    }

    throw new IllegalArgumentException(ErrorMessage.ERROR_MESSAGE_PREFIX +
        ErrorMessage.INVALID_ORDER_FORMAT);
  }

  private void validateOrderCount(int menuCount) {

    if (menuCount >= 1) {
      return;
    }

    throw new IllegalArgumentException(ErrorMessage.ERROR_MESSAGE_PREFIX +
        ErrorMessage.INVALID_ORDER_FORMAT);
  }

  private void validateOrderCountFormat(String input) {
    try {
      Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          ErrorMessage.ERROR_MESSAGE_PREFIX + ErrorMessage.INVALID_ORDER_FORMAT);
    }
  }
}
