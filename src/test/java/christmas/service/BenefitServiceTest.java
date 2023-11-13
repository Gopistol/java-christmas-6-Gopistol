package christmas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;
import christmas.domain.badge.Badge;
import christmas.domain.policy.DiscountPolicy;
import persistence.InMemoryBenefitRepository;

class BenefitServiceTest {

  InMemoryBenefitRepository inMemoryBenefitRepository = new InMemoryBenefitRepository();
  BenefitService benefitService = new BenefitService(inMemoryBenefitRepository);

  @Test
  @DisplayName("혜택 목록 조회 테스트")
  void testListBenefit() {
    Map<String, Integer> expectedBenefit = new HashMap<>();
    expectedBenefit.put("제로콜라", 3_000);
    expectedBenefit.put("레드와인", 60_000);
    expectedBenefit.put("샴페인", 25_000);
    inMemoryBenefitRepository.saveAll(expectedBenefit);

    Map<String, Integer> result = benefitService.listBenefit();

    assertThat(result).isEqualTo(expectedBenefit);
  }

  @Test
  @DisplayName("총 혜택 금액 계산 테스트")
  void testTotalBenefitAmount() {
    Map<String, Integer> discountBoard = new HashMap<>();
    discountBoard.put("제로콜라", 3_000);
    discountBoard.put("레드와인", 60_000);
    discountBoard.put("샴페인", 25_000);
    discountBoard.put(DiscountPolicy.GIVEAWAY_EVENT.getDescription(), 1_000);
    inMemoryBenefitRepository.saveAll(discountBoard);

    int result = benefitService.totalBenefitAmount();

    assertThat(result).isEqualTo(88_000);
  }

  @Test
  @DisplayName("이벤트 뱃지 조회 테스트")
  void testGetEventBadge() {
    Badge result = benefitService.getEventBadge(15_000);

    assertThat(result).isEqualTo(Badge.TREE);
  }
}
