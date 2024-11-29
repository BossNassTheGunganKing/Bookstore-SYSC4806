@ApplicationModule(
        allowedDependencies = {"book", "user",  "order",  "image",  "view"}
)
package codelook.jpa.book;

import org.springframework.modulith.ApplicationModule;