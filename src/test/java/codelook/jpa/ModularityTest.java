package codelook.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import java.util.Set;
import java.util.stream.Collectors;

public class ModularityTest {
    private ApplicationModules modules = ApplicationModules.of(JpaApplication.class);

    @Test
    void writeDocumentationSnippets() {

        //var modules = ApplicationModules.of(JpaApplication.class).verify();

        //new Documenter(modules)
        //        .writeModulesAsPlantUml()
        //        .writeIndividualModulesAsPlantUml();

        Set<String> violations = modules.detectViolations().getMessages().stream()
                .filter(message -> !message.contains("uses field injection"))
                .collect(Collectors.toSet());

        Assertions.assertEquals(0, violations.size(), "There are violations:\n" + String.join("\n", violations));
    }

    @Test
    void createApplicationModuleModel(){
        ApplicationModules modules = ApplicationModules.of(JpaApplication.class);
        modules.forEach(System.out::println);
    }
}
