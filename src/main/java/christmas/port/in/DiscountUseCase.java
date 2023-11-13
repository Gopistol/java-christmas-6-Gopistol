package christmas.port.in;

public interface DiscountUseCase {

  void saveDate(int date);

  void saveBenefit(int totalOrderPrice);

  int expectedPayment(int totalPrice);
}