package christmas.domain.policy;

import christmas.domain.menu.category.Drink;
import java.util.Arrays;
import java.util.List;

/*
  이벤트 플래너의 기본적인 정책에 관련된 클래스
 */
public class PlannerPolicy {

  public static final int INPUT_TOKEN_COUNT = 2;
  public static final String GIVEAWAY_MENU = String.valueOf(Drink.샴페인);
  public static final int GIVEAWAY_MENU_COUNT = 1;
  public static final int ZERO = 0;
  protected static final int DISCOUNT_POSSIBLE_PRICE = 10_000;
  protected static final List<Integer> WEEKEND_SEPARATOR = Arrays.asList(1, 2);
  protected static final int D_DAY = 25;
  protected static final int BASIC_DISCOUNT = 1_000;
  protected static final int D_DAY_DISCOUNT_PER_ONE_DAY = 100;
  protected static final int WEEK_DISCOUNT_PER_ONE_MENU = 2_023;
  protected static final List<Integer> STAR_DATE = Arrays.asList(3, 10, 17, 24, 25, 31);
  protected static final int SPECIAL_DISCOUNT_AMOUNT = 1_000;
  protected static final int GIVEAWAY_CONDITION = 120_000;
}
