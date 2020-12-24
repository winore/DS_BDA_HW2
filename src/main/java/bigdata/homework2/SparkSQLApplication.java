package bigdata.homework2;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

// Количество событий в syslog разного уровная логирования по часам.
 
@Slf4j
public class SparkSQLApplication {


    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Usage: java -jar SparkSQLApplication.jar input.file outputDirectory");
        }
// SparkSession - точка входа для создания dataframe
        log.info("Appliction started!");
        log.debug("Application started");
        SparkSession sc = SparkSession
                .builder()
                .master("local")
                .appName("SparkSQLApplication")
                .getOrCreate();

        Dataset<String> df = sc.read().text(args[0]).as(Encoders.STRING());
        log.info("===============COUNTING...================");
        JavaRDD<Row> result = LogLevelCounter.countLogLevelPerHour(df);
        log.info("============SAVING FILE TO " + args[1] + " directory============");
        result.saveAsTextFile(args[1]);
    }
}
