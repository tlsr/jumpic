package ua.com.cyberneophyte.jumpic.forms;

import javax.validation.constraints.Size;

public class ModuleForm {
    @Size(min = 3,max = 64,message = "{module.name.out.of.range}")
    private String moduleName;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
