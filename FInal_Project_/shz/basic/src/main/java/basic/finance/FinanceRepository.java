package basic.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Integer>{
    List<Finance> findByUserId(int userId);
}
