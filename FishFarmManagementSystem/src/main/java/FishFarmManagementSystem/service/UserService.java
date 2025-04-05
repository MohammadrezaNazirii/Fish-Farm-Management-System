package FishFarmManagementSystem.service;

import FishFarmManagementSystem.*;
import FishFarmManagementSystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

//    public User saveUser(User user) {
//        return repository.save(user);
//    }
    public void createSuperAdmin(String name, String username, String password) {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.setName(name);
        superAdmin.setUsername(username);
        superAdmin.setPassword(password);
        superAdmin.setRole("SuperAdmin");
        superAdmin.setBlocked(false);
        repository.save(superAdmin);
    }

    public void createAdmin(String name, String username, String password) {
        Admin admin = new Admin();
        admin.setName(name);
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRole("Admin");
        admin.setBlocked(false);
        repository.save(admin);
    }

    public void createFeedingSpecialist(String name, String username, String password) {
        FeedingSpecialist feedingSpecialist = new FeedingSpecialist();
        feedingSpecialist.setName(name);
        feedingSpecialist.setUsername(username);
        feedingSpecialist.setPassword(password);
        feedingSpecialist.setRole("FeedingSpecialist");
        feedingSpecialist.setBlocked(false);
        repository.save(feedingSpecialist);
    }

    public void createFacilitySpecialist(String name, String username, String password) {
        FacilitySpecialist facilitySpecialist = new FacilitySpecialist();
        facilitySpecialist.setName(name);
        facilitySpecialist.setUsername(username);
        facilitySpecialist.setPassword(password);
        facilitySpecialist.setRole("FacilitySpecialist");
        facilitySpecialist.setBlocked(false);
        repository.save(facilitySpecialist);
    }

    public void deleteUserById(int id) {
        repository.deleteById(id);
    }

    public User updateUser(int id, String name, String username, String password, String role, Boolean blocked) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setBlocked(blocked);
            return repository.save(user);
        }
        return null; // Handle this case appropriately
    }

    public List<User> findAllUsersByRole(String role){
        return repository.findAllByRole(role);
    }

    public User findUserByUsername(String username){
        if(repository.existsUserByUsername(username))
            return repository.findUserByUsername(username);
        else
            return null;
    }
}
