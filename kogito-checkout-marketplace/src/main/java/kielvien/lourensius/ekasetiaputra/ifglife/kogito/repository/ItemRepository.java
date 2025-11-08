package kielvien.lourensius.ekasetiaputra.ifglife.kogito.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kielvien.lourensius.ekasetiaputra.ifglife.kogito.entities.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{

}
