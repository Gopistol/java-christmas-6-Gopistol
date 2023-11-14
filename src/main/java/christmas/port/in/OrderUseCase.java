package christmas.port.in;

import java.util.Map;

public interface OrderUseCase {

  void saveOrder(String order);

  Map<String, Integer> listOrderBoard();

  int totalOrderPrice();
}
