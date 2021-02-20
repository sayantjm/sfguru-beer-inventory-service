package sayant.springframeworkguru.sfgurubeerinventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sayant.springframeworkguru.brewery.model.events.DeallocateOrderRequest;
import sayant.springframeworkguru.sfgurubeerinventoryservice.config.JmsConfig;
import sayant.springframeworkguru.sfgurubeerinventoryservice.service.AllocationService;

/**
 * Created by sayantjm on 20/2/21
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationListenerImpl {
    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request) {
        allocationService.deallocateOrder(request.getBeerOrderDto());
    }
}
