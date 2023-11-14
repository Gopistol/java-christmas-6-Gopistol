package christmas.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import persistence.InMemoryBenefitRepository;
import persistence.InMemoryOrderRepository;
import christmas.service.DiscountService;
import christmas.domain.policy.DiscountPolicy;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountServiceTest {

  private InMemoryBenefitRepository inMemoryBenefitRepository;
  private InMemoryOrderRepository inMemoryOrderRepository;
  private DiscountService discountService;

  @BeforeEach
  void setUp() {
    inMemoryBenefitRepository = new InMemoryBenefitRepository();
    inMemoryOrderRepository = new InMemoryOrderRepository();
    discountService = new DiscountService(inMemoryBenefitRepository, inMemoryOrderRepository);
  }

  @Test
  @DisplayName("혜택 저장 테스트")
  void testSaveBenefit() {
    inMemoryOrderRepository.save("제로콜라", 3_000);
    inMemoryOrderRepository.save("레드와인", 60_000);
    inMemoryOrderRepository.save("샴페인", 25_000);

    discountService.saveDate(10);
    discountService.saveBenefit(100_000);

    Map<String, Integer> benefitBoard = inMemoryBenefitRepository.findAll();
    assertThat(benefitBoard).isNotEmpty();
    // 각각의 혜택이 정상적으로 적용되었는지 확인
    assertThat(
        benefitBoard.get(DiscountPolicy.CHRISTMAS_D_DAY_DISCOUNT.getDescription())).isEqualTo(
        1_200);
    assertThat(benefitBoard.get(DiscountPolicy.WEEKDAY_DISCOUNT.getDescription())).isEqualTo(
        4_046);
    assertThat(benefitBoard.get(DiscountPolicy.SPECIAL_DISCOUNT.getDescription())).isEqualTo(
        1_000);
    assertThat(benefitBoard.get(DiscountPolicy.GIVEAWAY_EVENT.getDescription())).isEqualTo(25_000);
  }

  @Test
  @DisplayName("예상 결제 금액 계산 테스트")
  void testExpectedPayment() {
    inMemoryOrderRepository.save("제로콜라", 3_000);
    inMemoryOrderRepository.save("레드와인", 60_000);
    inMemoryOrderRepository.save("샴페인", 25_000);

    discountService.saveDate(10);
    discountService.saveBenefit(100_000);

    int result = discountService.expectedPayment(100_000);
    assertThat(result).isGreaterThanOrEqualTo(0);
  }
}
