package fyp.canteen.fypapi.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import fyp.canteen.fypapi.service.ordermgmt.OnsiteOrderService;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.pojo.payment.KhaltiTransactionVerificationRequestPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KhaltiServiceImpl implements KhatiService{

    private final ObjectMapper objectMapper;
    private final OnsiteOrderService onsiteOrderService;

    @Override
    public Object verifyTransaction(KhaltiTransactionVerificationRequestPojo requestPojo) {
        String apiUrl = "https://khalti.com/api/v2/payment/verify/";
        String secretKey = "test_secret_key_d9488c8d7386435eb607f5b260472454";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Key " + secretKey);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("amount", String.valueOf(requestPojo.getAmount()));
        requestBody.add("token", requestPojo.getToken());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        if(responseEntity.getStatusCode() == HttpStatusCode.valueOf(200)) {
            try {
                onsiteOrderService.saveOnsiteOrder(requestPojo.getOnsiteOrder());
                return objectMapper.readValue(responseEntity.getBody(), Map.class);

            } catch (Exception e) {
                throw new AppException(e.getMessage(), e);
            }
        }else{
            throw new AppException("Transaction verification failed.");
        }
    }
}
