package codelook.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModularityTest {
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
