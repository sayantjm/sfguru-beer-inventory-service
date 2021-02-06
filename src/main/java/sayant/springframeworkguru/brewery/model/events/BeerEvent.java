package sayant.springframeworkguru.brewery.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sayantjm on 23/1/21
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent {

     static final long serialVersionUID = 6796250213368943508L;

    private BeerDto beerDto;
}
