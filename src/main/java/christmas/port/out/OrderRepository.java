package christmas.port.out;

import java.util.Map;

public interface OrderRepository {

  void save(String menu, int menuCount);

  Map<String, Integer> findAll();
}
