package christmas.persistence;

import christmas.port.out.OrderRepository;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {

  private final Map<String, Integer> board = new LinkedHashMap<>();

  @Override
  public void save(String menu, int menuCount) {
    board.put(menu, menuCount);
  }

  @Override
  public Map<String, Integer> findAll() {
    return board;
  }
}
