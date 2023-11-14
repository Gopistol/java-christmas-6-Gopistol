package christmas.persistence;

import christmas.port.out.BenefitRepository;
import java.util.LinkedHashMap;
import java.util.Map;

public class InMemoryBenefitRepository implements BenefitRepository {

  private final Map<String, Integer> board = new LinkedHashMap<>();

  @Override
  public void save(String discountCategory, int discountAmount) {
    board.put(discountCategory, discountAmount);
  }

  @Override
  public void saveAll(Map<String, Integer> benefitBoard) {
    board.putAll(benefitBoard);
  }

  @Override
  public Map<String, Integer> findAll() {
    return board;
  }
}
