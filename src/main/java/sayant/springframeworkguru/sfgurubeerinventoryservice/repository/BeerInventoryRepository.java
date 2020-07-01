package sayant.springframeworkguru.sfgurubeerinventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sayant.springframeworkguru.sfgurubeerinventoryservice.domain.BeerInventory;

import java.util.List;
import java.util.UUID;

/**
 * Created by sayantjm on 1/7/20
 */
public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

    List<BeerInventory> findAllByBeerId(UUID beerId);
}
