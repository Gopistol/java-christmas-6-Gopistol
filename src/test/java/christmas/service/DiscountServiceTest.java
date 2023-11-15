package christmas.service;

import static christmas.domain.policy.DiscountPolicy.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.domain.policy.DiscountPolicy.GIVEAWAY_EVENT;
import static christmas.domain.policy.DiscountPolicy.SPECIAL_DISCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import christmas.domain.menu.MenuManager;
import christmas.domain.policy.DiscountPolicy;
import christmas.domain.policy.PlannerPolicy;
import christmas.persistence.InMemoryBenefitRepository;
import christmas.persistence.InMemoryOrderRepository;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DiscountServiceTest extends PlannerPolicy {

  private InMemoryBenefitRepository inMemoryBenefitRepository;
  private InMemoryOrderRepository inMemoryOrderRepository;
  private DiscountService discountService;

  @BeforeEach
  void setUp() {
    inMemoryOrderRepository = Mockito.mock(InMemoryOrderRepository.class);
    inMemoryBenefitRepository = Mockito.mock(InMemoryBenefitRepository.class);
    discountService = new DiscountService(inMemoryBenefitRepository, inMemoryOrderRepository);
  }

  // 기능 테스트
  @Test
  void testSaveDDayDiscount() {
    int date = 5; // 임의의 날짜 입력

    int result = discountService.saveDDayDiscount(date);

    assertEquals(
        PlannerPolicy.BASIC_DISCOUNT + ((date - 1) * PlannerPolicy.D_DAY_DISCOUNT_PER_ONE_DAY),
        result);
  }

  @Test
  void testGetWeekDiscountCategory() {
    int date = 5; // 임의의 날짜 입력

    String result = discountService.getWeekDiscountCategory(date);

    assertEquals(PlannerPolicy.WEEKEND_SEPARATOR.contains(date % 7)
        ? DiscountPolicy.WEEKEND_DISCOUNT.getDescription()
        : DiscountPolicy.WEEKDAY_DISCOUNT.getDescription(), result);
  }

  @Test
  void testExpectedPayment() {
    Map<String, Integer> discountBoard = new HashMap<>();
    discountBoard.put(CHRISTMAS_D_DAY_DISCOUNT.getDescription(), 1_000);
    discountBoard.put(SPECIAL_DISCOUNT.getDescription(), 2_000);
    discountBoard.put(GIVEAWAY_EVENT.getDescription(), 3_000);

    when(inMemoryBenefitRepository.findAll()).thenReturn(discountBoard);

    int expectedPayment = discountService.expectedPayment(10_000);

    assertEquals(7_000, expectedPayment);
  }
}
