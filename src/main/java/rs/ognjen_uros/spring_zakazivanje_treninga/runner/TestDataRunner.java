package rs.ognjen_uros.spring_zakazivanje_treninga.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Manager;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Role;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.ManagerRepository;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.RoleRepository;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.UserRepository;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private ManagerRepository managerRepository;

    public TestDataRunner(ManagerRepository managerRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleUser = new Role("ROLE_USER", "User role");
        Role roleAdmin = new Role("ROLE_ADMIN", "Admin role");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
        //Insert admin
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        Manager manager = new Manager();
        manager.setUsernameManager("manager");
        manager.setPassword("manager");
        manager.setEmail("manager@gmail.com");

        admin.setRole(roleAdmin);
        userRepository.save(admin);
        managerRepository.save(manager);

    }
}
