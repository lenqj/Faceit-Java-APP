package Main.repository;

import Main.model.SteamAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SteamAccountRepository extends CrudRepository<SteamAccount, Long> {
}
