package bigdata.homework2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogLevelHour {

    // Уровень логирования (их в общем 7 в centOS)
    private String logLevel;

    // Час, в который произошло событие (24h)
    private int hour;
}
