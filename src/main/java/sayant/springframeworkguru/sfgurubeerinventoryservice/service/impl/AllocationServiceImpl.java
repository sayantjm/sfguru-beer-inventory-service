package sayant.springframeworkguru.sfgurubeerinventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sayant.springframeworkguru.brewery.model.BeerOrderDto;
import sayant.springframeworkguru.brewery.model.BeerOrderLineDto;
import sayant.springframeworkguru.sfgurubeerinventoryservice.domain.BeerInventory;
import sayant.springframeworkguru.sfgurubeerinventoryservice.repository.BeerInventoryRepository;
import sayant.springframeworkguru.sfgurubeerinventoryservice.service.AllocationService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sayantjm on 7/2/21
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.debug("Allocation OrderId:{}", beerOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
            if ((((beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0)
               - (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated():0)) > 0)){
                allocateBeerOrderLine(beerOrderLine);
            }
            totalOrdered.set(totalOrdered.get() + beerOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0));
        });

        log.debug("Total ordered: {} - Total allocated: {}", totalOrdered.get(), totalAllocated.get());
        return totalOrdered.get() == totalAllocated.get();
    }

    private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLine) {
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerOrderLine.getUpc());

        beerInventoryList.forEach(beerInventory -> {
            int inventory = (beerInventory.getQuantityOnHand() == null) ? 0: beerInventory.getQuantityOnHand();
            int orderQty = (beerOrderLine.getOrderQuantity() == null) ? 0: beerOrderLine.getOrderQuantity();
            int allocatedQty = (beerOrderLine.getQuantityAllocated() == null) ? 0: beerOrderLine.getQuantityAllocated();
            int qtyToAllocate = orderQty - allocatedQty;

            if (inventory >= qtyToAllocate) {  // Full allocation
                inventory = inventory - qtyToAllocate;
                beerOrderLine.setQuantityAllocated(orderQty);
                beerInventory.setQuantityOnHand(inventory);
                beerInventoryRepository.save(beerInventory);
            } else if(inventory > 0) { // partial allocation
                beerOrderLine.setQuantityAllocated(allocatedQty + inventory);
                beerInventory.setQuantityOnHand(0);
                beerInventoryRepository.delete(beerInventory);
            }
        });

    }
}
