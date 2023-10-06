package FileGenerator.newEntity;

public class EntityImportGenerator {

    public static String generateEntityImportContent() {


        return """
                
                import com.fasterxml.jackson.annotation.JsonManagedReference;
                import jakarta.persistence.Entity;
                import jakarta.persistence.*;
                import org.antlr.v4.runtime.misc.NotNull;
                import org.springframework.format.annotation.DateTimeFormat;
                
                import java.time.LocalDate;
                import java.util.ArrayList;
                import java.util.Date;
                import java.util.List;""";
    }

}
