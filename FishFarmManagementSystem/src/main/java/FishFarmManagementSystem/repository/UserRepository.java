package FishFarmManagementSystem.repository;

import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(String role);
    User findUserByUsername(String username);
    Boolean existsUserByUsername(String username);
}
