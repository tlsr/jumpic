package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Module;

public interface ModuleRepo extends JpaRepository<Module,Long> {
    void deleteModuleById(Long id);
    Module findModuleById(Long id);
}
