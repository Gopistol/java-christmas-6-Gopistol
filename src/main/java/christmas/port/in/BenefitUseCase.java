package christmas.port.in;

import christmas.domain.badge.Badge;
import java.util.Map;

public interface BenefitUseCase {

  Map<String, Integer> listBenefit();

  int totalBenefitAmount();

  Badge getEventBadge(int totalBenefit);
}
