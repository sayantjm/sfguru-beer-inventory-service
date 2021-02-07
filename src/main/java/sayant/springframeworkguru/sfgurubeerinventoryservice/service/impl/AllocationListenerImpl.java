package sayant.springframeworkguru.sfgurubeerinventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import sayant.springframeworkguru.brewery.model.events.AllocateOrderRequest;
import sayant.springframeworkguru.brewery.model.events.AllocateOrderResult;
import sayant.springframeworkguru.sfgurubeerinventoryservice.config.JmsConfig;
import sayant.springframeworkguru.sfgurubeerinventoryservice.service.AllocationService;

/**
 * Created by sayantjm on 7/2/21
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationListenerImpl {
    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest request) {
        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
        builder.beerOrderDto(request.getBeerOrderDto());

        try {
            Boolean allocationResult = allocationService.allocateOrder(request.getBeerOrderDto());
            if (allocationResult) {
                builder.pendingInventory(false);
            } else {
                builder.pendingInventory(true);
            }
        } catch (Exception e) {
            log.error("Allocation failed for OrderId:{}", request.getBeerOrderDto().getId());
            builder.allocationError(true);
        }



        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }
}
