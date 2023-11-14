package christmas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import christmas.domain.badge.Badge;
import persistence.InMemoryBenefitRepository;

class BenefitServiceTest {

  InMemoryBenefitRepository inMemoryBenefitRepository = new InMemoryBenefitRepository();
  BenefitService benefitService = new BenefitService(inMemoryBenefitRepository);


  @Test
  @DisplayName("이벤트 뱃지 조회 테스트")
  void testGetEventBadge() {
    Badge result = benefitService.getEventBadge(15_000);

    assertThat(result).isEqualTo(Badge.TREE);
  }
}
