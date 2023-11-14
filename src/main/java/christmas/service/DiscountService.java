package christmas.service;

import christmas.domain.menu.MenuManager;
import christmas.domain.policy.DiscountPolicy;
import christmas.domain.policy.PlannerPolicy;
import christmas.domain.menu.category.Dessert;
import christmas.domain.menu.category.Main;
import christmas.port.in.DiscountUseCase;
import java.util.LinkedHashMap;
import java.util.Map;
import christmas.persistence.InMemoryBenefitRepository;
import christmas.persistence.InMemoryOrderRepository;

public class DiscountService extends PlannerPolicy implements DiscountUseCase {

  private final InMemoryBenefitRepository inMemoryBenefitRepository;
  private final InMemoryOrderRepository inMemoryOrderRepository;
  private int date;

  public DiscountService(InMemoryBenefitRepository inMemoryBenefitRepository,
      InMemoryOrderRepository inMemoryOrderRepository) {
    this.inMemoryBenefitRepository = inMemoryBenefitRepository;
    this.inMemoryOrderRepository = inMemoryOrderRepository;
  }

  @Override
  public void saveDate(int date) {
    this.date = date;
  }

  @Override
  public void saveBenefit(int totalOrderPrice) {

    if (totalOrderPrice < DISCOUNT_POSSIBLE_PRICE) {
      return;
    }

    Map<String, Integer> benefitBoard = new LinkedHashMap<>();
    benefitBoard.put(DiscountPolicy.CHRISTMAS_D_DAY_DISCOUNT.getDescription(),
        saveDDayDiscount(date));

    String weekDiscountCategory = getWeekDiscountCategory(date);
    int discount = getWeekDiscount(weekDiscountCategory);

    benefitBoard.put(weekDiscountCategory, discount);
    benefitBoard.put(DiscountPolicy.SPECIAL_DISCOUNT.getDescription(), saveSpecialDiscount(date));
    benefitBoard.put(DiscountPolicy.GIVEAWAY_EVENT.getDescription(),
        saveGiveAwayDiscount(totalOrderPrice));

    inMemoryBenefitRepository.saveAll(benefitBoard);
  }

  @Override
  public int expectedPayment(int totalPrice) {
    // 할인 전 총주문 금액 - 디데이, 평일 / 주말, 특별 할인 금액의 합
    Map<String, Integer> discountBoard = inMemoryBenefitRepository.findAll();
    int totalAmount = 0;

    for (String category : discountBoard.keySet()) {
      if (!category.equals(DiscountPolicy.GIVEAWAY_EVENT.getDescription())) {
        totalAmount += discountBoard.get(category);
      }
    }

    return totalPrice - totalAmount;
  }

  public int saveDDayDiscount(int date) {
    // 25일이 넘으면 적용 X
    if (date > D_DAY) {
      return ZERO;
    }

    return BASIC_DISCOUNT + ((date - 1) * D_DAY_DISCOUNT_PER_ONE_DAY);
  }

  public String getWeekDiscountCategory(int date) {
    // date % 7 계산으로 주말, 주중 구분
    int separator = date % 7;

    if (PlannerPolicy.WEEKEND_SEPARATOR.contains(separator)) {
      return DiscountPolicy.WEEKEND_DISCOUNT.getDescription();
    }

    return DiscountPolicy.WEEKDAY_DISCOUNT.getDescription();
  }

  public int getWeekDiscount(String category) {
    Map<String, Integer> orderBoard = inMemoryOrderRepository.findAll();

    if (category.equals(DiscountPolicy.WEEKDAY_DISCOUNT.getDescription())) {
      return weekdayDiscount(orderBoard);
    }

    return weekendDiscount(orderBoard);
  }

  public int saveSpecialDiscount(int date) {
    // STAR_DATE에 포함되면 할인 적용
    if (STAR_DATE.contains(date)) {
      return SPECIAL_DISCOUNT_AMOUNT;
    }

    return ZERO;
  }

  public int saveGiveAwayDiscount(int orderPrice) {

    if (orderPrice >= GIVEAWAY_CONDITION) {
      return MenuManager.getPriceByMenu(GIVEAWAY_MENU);
    }

    return ZERO;
  }

  private int weekdayDiscount(Map<String, Integer> orderBoard) {
    // 디저트 메뉴 개수 구하기
    int dessertCount = 0;

    for (String menu : orderBoard.keySet()) {
      if (Dessert.contains(menu)) {
        dessertCount = plusMenuCount(dessertCount, orderBoard.get(menu));
      }
    }

    return dessertCount * WEEK_DISCOUNT_PER_ONE_MENU;
  }

  private int weekendDiscount(Map<String, Integer> orderBoard) {
    // 메인 메뉴 개수 구하기
    int mainCount = 0;

    for (String menu : orderBoard.keySet()) {
      if (Main.contains(menu)) {
        mainCount = plusMenuCount(mainCount, orderBoard.get(menu));
      }
    }

    return mainCount * WEEK_DISCOUNT_PER_ONE_MENU;
  }

  private int plusMenuCount(int count, int menuCount) {
    for (int i = 0; i < menuCount; i++) {
      count++;
    }
    return count;
  }
}
