package ng.com.dpros.customermanager;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ng.com.dpros.customermanager");

        noClasses()
            .that()
                .resideInAnyPackage("ng.com.dpros.customermanager.service..")
            .or()
                .resideInAnyPackage("ng.com.dpros.customermanager.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..ng.com.dpros.customermanager.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
