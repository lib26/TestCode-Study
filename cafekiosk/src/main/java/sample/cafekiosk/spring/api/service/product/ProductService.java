package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * readonly를 잘 할용할 필요가 있다.
 * 1. CRUD 에서 CUD 동작 X / only Read
 * 2. JPA : CUD 스샷 저장, 변경감지 X (성능 항상)
 *
 * 3. CQRS(중요) - Command(쓰기) / Query(읽기) 책임을 분리할 수 있음(중요)
 * 도메인에 따라 다르겠지만 보통 Read 작업이 많음.
 * 따라서 service를 Command와 Query로 분리 할 수 있다. Service와 Provider ㅇㅇ
 * 보통 db를 마스터(write 전용) db 슬레이브(read 전용) 클러스터로 사용한다.
 * readonly true false 값을 보고 db 엔드포인트를 구분할 수 있다.
 * 결국 이렇게 db를 나눠서 추후 읽기로 인한 장애가 났을 때 쓰기작업에 대한 장애를 방지할 수 있다
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 동시성 이슈가 발생할 수 있음. 여러 관리자가 동시에 등록할 때는 어떡할 것인지.
     * 1. 상품 번호 필드에 유니크 인덱스 제약조건을 걸고, 누군가가 번호를 선점했을 때 3회 재시도하는 방법.
     * 2. 동시 요청하는 접속자가 많은 크리티컬한 상황이라면, uuid를 정책을 가져가는 방법도 있음. 상품 번호가 아예 유니크한 값이 나오니까.
     */
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }

    /**
     * 판매중인 상품 조회
     */
    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

}