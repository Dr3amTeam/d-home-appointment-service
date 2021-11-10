package appointments.command.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerDniRepository extends JpaRepository<CustomerDni, String> {
    Optional<CustomerDni> getDniByCustomerId(String customerId);

    @Query(value = "SELECT * FROM customer_dni WHERE customer_id <> :customerId AND dni = :dni LIMIT 1", nativeQuery = true)
    Optional<CustomerDni> getByDniForDistinctCustomerId(String dni, String customerId);
}