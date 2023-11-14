package christmas.service;

import christmas.core.ErrorMessage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import christmas.persistence.InMemoryOrderRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

  private InMemoryOrderRepository inMemoryOrderRepository;
  private OrderService orderService;

  @BeforeEach
  void setUp() {
    inMemoryOrderRepository = Mockito.mock(InMemoryOrderRepository.class);
    orderService = new OrderService(inMemoryOrderRepository);
  }

  // 기능 테스트
  @Test
  void testSaveOrder() {
    String orders = "양송이수프-2,초코케이크-3,제로콜라-1";

    orderService.saveOrder(orders);

    Mockito.verify(inMemoryOrderRepository, Mockito.times(3))
        .save(any(String.class), any(Integer.class));
  }

  @Test
  void testTotalOrderPrice() {
    Map<String, Integer> mockData = new HashMap<>();
    mockData.put("양송이수프", 2);
    mockData.put("레드와인", 1);
    mockData.put("샴페인", 1);
    when(inMemoryOrderRepository.findAll()).thenReturn(mockData);

    int expected = 6_000 * 2 + 60_000 + 25_000;
    int result = orderService.totalOrderPrice();

    assertEquals(expected, result);
  }

  // 예외 테스트
  @DisplayName("중복된 메뉴를 입력하면 예외가 발생한다.")
  @Test
  void testSaveOrderWithDuplicatedMenu() {
    Map<String, Integer> mockData = new HashMap<>();
    mockData.put("Menu1", 2);
    when(inMemoryOrderRepository.findAll()).thenReturn(mockData);

    String orders = "Menu1-2";
    assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(orders));
  }

  @DisplayName("입력한 메뉴의 총 개수가 20개가 넘어가면 예외가 발생한다.")
  @Test
  void testSaveOrderWithExceededMenuCount() {
    Map<String, Integer> mockData = new HashMap<>();
    for (int i = 0; i < 21; i++) {
      mockData.put("Menu" + i, 1);
    }
    when(inMemoryOrderRepository.findAll()).thenReturn(mockData);

    assertThrows(IllegalStateException.class, () -> orderService.listOrderBoard());
  }

  @Test
  @DisplayName("음료만 주문한 경우의 테스트")
  void testValidateOnlyDrinkMenu() {
    Map<String, Integer> board = new HashMap<>();
    board.put("제로콜라", 3_000);
    board.put("레드와인", 60_000);
    board.put("샴페인", 25_000);

    assertThatThrownBy(() -> orderService.listOrderBoard())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining(ErrorMessage.ERROR_MESSAGE_PREFIX + ErrorMessage.INVALID_ORDER_KIND);
  }
}
