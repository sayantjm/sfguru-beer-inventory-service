package sayant.springframeworkguru.common.events;

import lombok.NoArgsConstructor;

/**
 * Created by sayantjm on 23/1/21
 */
@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
