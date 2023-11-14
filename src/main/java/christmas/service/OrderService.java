package christmas.service;

import christmas.domain.menu.category.Drink;
import christmas.domain.menu.MenuManager;
import christmas.port.in.OrderUseCase;
import core.ErrorMessage;
import core.constant.TextConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import persistence.InMemoryOrderRepository;

public class OrderService extends TextConstants implements OrderUseCase {

  private final InMemoryOrderRepository inMemoryOrderRepository;

  public OrderService(InMemoryOrderRepository inMemoryOrderRepository) {
    this.inMemoryOrderRepository = inMemoryOrderRepository;
  }

  @Override
  public void saveOrder(String orders) {
    StringTokenizer st = new StringTokenizer(orders, ORDER_UNIT_SEPARATOR);

    while (st.hasMoreTokens()) {
      String order = st.nextToken();
      List<String> orderList = splitOrderBySeparator(order);

      String menu = orderList.get(0);
      validateExistingMenu(menu);
      validateDuplicatedMenu(menu);
      int menuCount = Integer.parseInt(orderList.get(1));

      inMemoryOrderRepository.save(menu, menuCount);
    }
  }

  @Override
  public Map<String, Integer> listOrderBoard() {
    Map<String, Integer> board = inMemoryOrderRepository.findAll();
    validateMenuCount(board);
    validateOnlyDrinkMenu(board);
    return board;
  }

  @Override
  public int totalOrderPrice() {
    Map<String, Integer> board = inMemoryOrderRepository.findAll();
    int totalAmount = 0;

    for (String menu : board.keySet()) {
      int price = MenuManager.getPriceByMenu(menu);
      int count = board.get(menu);
      totalAmount += price * count;
    }

    return totalAmount;
  }

  public List<String> splitOrderBySeparator(String order) {
    StringTokenizer st = new StringTokenizer(order, SEPARATOR);
    List<String> orderList = new ArrayList<>(st.countTokens());

    orderList.add(st.nextToken());
    orderList.add(st.nextToken());

    return orderList;
  }

  private void validateDuplicatedMenu(String menu) {
    Map<String, Integer> board = inMemoryOrderRepository.findAll();

    if (board.containsKey(menu)) {
      throw new IllegalArgumentException(ErrorMessage.ERROR_MESSAGE_PREFIX +
          ErrorMessage.INVALID_ORDER_FORMAT);
    }
  }

  private void validateMenuCount(Map<String, Integer> board) {
    int totalMenuCount = 0;

    for (Integer value : board.values()) {
      totalMenuCount += value;
    }

    if (totalMenuCount > 20) {
      throw new IllegalStateException(ErrorMessage.ERROR_MESSAGE_PREFIX +
          ErrorMessage.INVALID_ORDER_AMOUNT);
    }
  }

  private void validateOnlyDrinkMenu(Map<String, Integer> board) {
    int drinkMenuCount = 0;

    for (String menu : board.keySet()) {
      if (Drink.contains(menu)) {
        drinkMenuCount++;
      }
    }

    if (board.size() == drinkMenuCount) {
      throw new IllegalStateException(ErrorMessage.ERROR_MESSAGE_PREFIX +
          ErrorMessage.INVALID_ORDER_KIND);
    }
  }

  private void validateExistingMenu(String menu) {
    MenuManager.getPriceByMenu(menu);
  }
}
