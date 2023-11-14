package christmas.port.out;

import java.util.Map;

public interface BenefitRepository {

  void save(String discountCategory, int discountAmount);

  void saveAll(Map<String, Integer> benefitBoard);

  Map<String, Integer> findAll();
}
