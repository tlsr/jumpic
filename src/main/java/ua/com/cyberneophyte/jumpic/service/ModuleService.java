package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.repos.ModuleRepo;

@Service
public class ModuleService {
    private ModuleRepo moduleRepo;

    public ModuleService(ModuleRepo moduleRepo) {
        this.moduleRepo = moduleRepo;
    }

    @Transactional
    public void deleteModule(Module module) {
        moduleRepo.deleteModuleById(module.getId());
    }

    public void saveModule(Module module){
        moduleRepo.save(module);
    }
}
