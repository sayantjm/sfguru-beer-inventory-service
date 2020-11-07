package sayant.springframeworkguru.sfgurubeerinventoryservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sayant.springframeworkguru.sfgurubeerinventoryservice.mapper.BeerInventoryMapper;
import sayant.springframeworkguru.sfgurubeerinventoryservice.model.BeerInventoryDto;
import sayant.springframeworkguru.sfgurubeerinventoryservice.repository.BeerInventoryRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by sayantjm on 7/11/20
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerInventoryController {
    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @GetMapping("api/v1/beer/{beerId}/inventory")
    List<BeerInventoryDto> listBeersById(@PathVariable UUID beerId){
        log.debug("Finding Inventory for beerId:" + beerId);

        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .collect(Collectors.toList());
    }
}
