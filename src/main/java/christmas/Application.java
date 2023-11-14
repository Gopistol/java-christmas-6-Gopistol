package christmas;

import christmas.service.BenefitService;
import christmas.service.DiscountService;
import christmas.service.OrderService;
import christmas.view.input.InputView;
import christmas.view.output.OutputView;
import christmas.persistence.InMemoryBenefitRepository;
import christmas.persistence.InMemoryOrderRepository;

public class Application {

  public static void main(String[] args) {

    var inMemoryOrderRepository = new InMemoryOrderRepository();
    var inMemoryBenefitRepository = new InMemoryBenefitRepository();

    var inputView = new InputView();
    var outputView = new OutputView();
    var benefitUseCase = new BenefitService(inMemoryBenefitRepository);
    var discountUseCase = new DiscountService(inMemoryBenefitRepository,
        inMemoryOrderRepository);
    var orderUseCase = new OrderService(inMemoryOrderRepository);

    new EventPlannerApplication(inputView, outputView, benefitUseCase, discountUseCase, orderUseCase).run();
  }
}
