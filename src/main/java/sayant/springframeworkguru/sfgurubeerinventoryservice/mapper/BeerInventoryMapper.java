package sayant.springframeworkguru.sfgurubeerinventoryservice.mapper;

import org.mapstruct.Mapper;
import sayant.springframeworkguru.sfgurubeerinventoryservice.domain.BeerInventory;
import sayant.springframeworkguru.brewery.model.BeerInventoryDto;

/**
 * Created by sayantjm on 1/7/20
 */
@Mapper(uses = {DateMapper.class})
public interface BeerInventoryMapper {

    BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto beerInventoryDTO);

    BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory beerInventory);
}
