package christmas;

import christmas.domain.badge.Badge;
import christmas.domain.policy.DiscountPolicy;
import christmas.domain.policy.PlannerPolicy;
import christmas.port.in.BenefitUseCase;
import christmas.port.in.DiscountUseCase;
import christmas.port.in.OrderUseCase;
import christmas.view.input.InputView;
import christmas.view.output.OutputView;

public class EventPlannerApplication implements Runnable {

  private final InputView inputView;
  private final OutputView outputView;
  private final OrderUseCase orderUseCase;
  private final BenefitUseCase benefitUseCase;
  private final DiscountUseCase discountUseCase;

  public EventPlannerApplication(InputView inputView, OutputView outputView,
      BenefitUseCase benefitUseCase, DiscountUseCase discountUseCase, OrderUseCase orderUseCase) {
    this.inputView = inputView;
    this.outputView = outputView;
    this.benefitUseCase = benefitUseCase;
    this.discountUseCase = discountUseCase;
    this.orderUseCase = orderUseCase;
  }

  @Override
  public void run() {
    try {
      // 1. 방문 일자, 주문 메뉴 입력받기
      getReservationInfo();
      // 2. 주문 메뉴, 내역, 할인 전 총 주문 금액 출력 (OrderService)
      listOrderSheet();
      // 3. 증정 메뉴, 혜택 내역 (DiscountService, BenefitService)
      listBenefit();
      // 4. 총 혜택 금액 출력 (BenefitService)
      getTotalBenefitAmount();
      // 5. 할인 후 예상 결제 금액 출력 (DiscountService)
      getExpectedPayment();
      // 6. 이벤트 배지 출력
      getEventBadge();
    } catch (Exception e) {
      outputView.printErrorMessage(e.getMessage());
    }
  }

  private void getReservationInfo() {
    outputView.printGuideMessage();
    outputView.printDateRequest();
    var date = inputView.readDate();

    outputView.printOrderRequest();
    var order = inputView.readOrder();

    discountUseCase.saveDate(date);
    orderUseCase.saveOrder(order);

    outputView.printPreviewBenefit(date);
  }

  private void listOrderSheet() {
    var board = orderUseCase.listOrderBoard();
    outputView.printOrderMenu(board);

    var totalPrice = orderUseCase.totalOrderPrice();
    outputView.printTotalOrderAmount(totalPrice);
  }

  private void listBenefit() {
    // 혜택 목록 저장
    var totalOrderPrice = orderUseCase.totalOrderPrice();
    discountUseCase.saveBenefit(totalOrderPrice);

    var benefitBoard = benefitUseCase.listBenefit();
    var category = DiscountPolicy.GIVEAWAY_EVENT.getDescription();
    var menu = PlannerPolicy.GIVEAWAY_MENU;
    var count = PlannerPolicy.GIVEAWAY_MENU_COUNT;

    outputView.printGiveAwayMenu(benefitBoard, category, menu, count);
    outputView.printBenefitDetails(benefitBoard);
  }

  private void getTotalBenefitAmount() {
    var totalBenefit = benefitUseCase.totalBenefitAmount();
    outputView.printTotalBenefitAmount(totalBenefit);
  }

  private void getExpectedPayment() {
    var totalPrice = orderUseCase.totalOrderPrice();
    var payment = discountUseCase.expectedPayment(totalPrice);
    outputView.printDiscountedPaymentAmount(payment);
  }

  private void getEventBadge() {
    var totalBenefit = benefitUseCase.totalBenefitAmount();
    Badge badge = benefitUseCase.getEventBadge(totalBenefit);
    outputView.printEventBadge(badge);
  }
}
