package christmas.service;

import christmas.domain.badge.Badge;
import christmas.port.in.BenefitUseCase;
import java.util.Map;
import christmas.persistence.InMemoryBenefitRepository;

public class BenefitService implements BenefitUseCase {

  private final InMemoryBenefitRepository inMemoryBenefitRepository;

  public BenefitService(InMemoryBenefitRepository inMemoryBenefitRepository) {
    this.inMemoryBenefitRepository = inMemoryBenefitRepository;
  }

  @Override
  public Map<String, Integer> listBenefit() {
    return inMemoryBenefitRepository.findAll();
  }

  @Override
  public int totalBenefitAmount() {
    Map<String, Integer> discountBoard = inMemoryBenefitRepository.findAll();
    int totalAmount = 0;

    for (String category : discountBoard.keySet()) {
      totalAmount += discountBoard.get(category);
    }

    return totalAmount;
  }

  @Override
  public Badge getEventBadge(int totalPrice) {
    return Badge.getBadgeByBenefitAmount(totalPrice);
  }
}
