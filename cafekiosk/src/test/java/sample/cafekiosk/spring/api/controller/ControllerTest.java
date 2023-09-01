package sample.cafekiosk.spring.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.order.OrderController;
import sample.cafekiosk.spring.api.controller.product.ProductController;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.product.ProductService;

/**
 * @WebMvcTest(controllers = ProductController.class)
 * Controller 관련 빈들만 불러오는 가벼운 테스트
 * @MockBean(JpaMetamodelMappingContext.class) MetamodelMappingContext 빈을 모킹하여 사용하도록 Spring Test 컨텍스트에 지시합니다.
 * 이렇게 함으로써 실제 데이터베이스 연결 없이도 JPA Metamodel과 관련된 테스트를 수행할 수 있습니다.
 * 실제 데이터베이스를 사용하지 않고도 JPA와 관련된 테스트를 좀 더 가볍게 실행할 수 있게 해주는 기능입니다.
 * <p>
 * 결국 BaseEntity의 Auditing 기능을 해당 테스트 환경에서도 사용하기 위한 객체를 생성하는 느낌인듯.
 * @MockBean 컨테이너에 ProductService Mock 객체를 등록.
 * ProductController에 해당 Mock 객체가 주입.
 * WebMvcTest ProductController 테스트를 할 때 아래 MockBean이 없으면 ProductService 객체를 찾을 수 없다는 에러가 뜬다.
 */
@WebMvcTest(controllers = {
        ProductController.class,
        OrderController.class
})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected OrderService orderService;
}
