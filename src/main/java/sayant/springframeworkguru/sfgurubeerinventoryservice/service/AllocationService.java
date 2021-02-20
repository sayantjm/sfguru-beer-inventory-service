package sayant.springframeworkguru.sfgurubeerinventoryservice.service;

import sayant.springframeworkguru.brewery.model.BeerOrderDto;

/**
 * Created by sayantjm on 7/2/21
 */
public interface AllocationService {
    Boolean allocateOrder(BeerOrderDto beerOrderDto);

    void deallocateOrder(BeerOrderDto beerOrderDto);
}
