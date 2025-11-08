package repository;

import entities.TemporaryMessage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemporaryMessageRepository implements PanacheRepository<TemporaryMessage> {

}
