package sayant.springframeworkguru.sfgurubeerinventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sayant.springframeworkguru.brewery.model.events.NewInventoryEvent;
import sayant.springframeworkguru.sfgurubeerinventoryservice.config.JmsConfig;
import sayant.springframeworkguru.sfgurubeerinventoryservice.domain.BeerInventory;
import sayant.springframeworkguru.sfgurubeerinventoryservice.repository.BeerInventoryRepository;

/**
 * Created by sayantjm on 24/1/21
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event) {
        log.debug("Got inventory:{}", event.toString());

        beerInventoryRepository.save(
                BeerInventory.builder()
                        .beerId(event.getBeerDto().getId())
                        .upc(event.getBeerDto().getUpc())
                        .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                        .build());
    }
}
