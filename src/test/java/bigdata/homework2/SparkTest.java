package bigdata.homework2;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static bigdata.homework2.LogLevelCounter.countLogLevelPerHour;

public class SparkTest {

    final String testString1 = "1,7,Oct 26 15:54:06,fccd8a5f3a42,rsyslogd:, [origin software=\"rsyslogd\" swVersion=\"8.16.0\" x-pid=\"1401\" x-info=\"http://www.rsyslog.com\"] start\n";
    final String testString2 = "2,2,Oct 26 14:54:06,fccd8a5f3a42,rsyslogd:, [origin software=\"rsyslogd\" swVersion=\"8.16.0\" x-pid=\"1401\" x-info=\"http://www.rsyslog.com\"] start\n";
    final String testString3 = "3,7,Oct 26 14:54:06,fccd8a5f3a42,rsyslogd:, [origin software=\"rsyslogd\" swVersion=\"8.16.0\" x-pid=\"1401\" x-info=\"http://www.rsyslog.com\"] start\n";

    SparkSession spess = SparkSession
            .builder()
            .master("local")
            .appName("SparkSQLApplication")
            .getOrCreate();

    @Test
    public void testOneLog() {

        JavaSparkContext sc = new JavaSparkContext(spess.sparkContext());
        JavaRDD<String> shrn = sc.parallelize(Arrays.asList(testString1));
        JavaRDD<Row> result = countLogLevelPerHour(spess.createDataset(shrn.rdd(), Encoders.STRING()));
        List<Row> rowList = result.collect();

        assert rowList.iterator().next().getInt(0) == 15;
        assert rowList.iterator().next().getString(1).equals("7");
        assert rowList.iterator().next().getLong(2) == 1;
    }

    @Test
    public void testTwoLogsSameTime(){


        JavaSparkContext sc = new JavaSparkContext(spess.sparkContext());
        JavaRDD<String> shrn = sc.parallelize(Arrays.asList(testString1, testString1));
        JavaRDD<Row> result = countLogLevelPerHour(spess.createDataset(shrn.rdd(), Encoders.STRING()));
        List<Row> rowList = result.collect();

        assert rowList.iterator().next().getInt(0) == 15;
        assert rowList.iterator().next().getString(1).equals("7");
        assert rowList.iterator().next().getLong(2) == 2;
    }

    @Test
    public void testTwoLogsDifferentTime(){

        JavaSparkContext sc = new JavaSparkContext(spess.sparkContext());
        JavaRDD<String> shrn = sc.parallelize(Arrays.asList(testString1, testString3));
        JavaRDD<Row> result = countLogLevelPerHour(spess.createDataset(shrn.rdd(), Encoders.STRING()));
        List<Row> rowList = result.collect();
        Row firstRow = rowList.get(0);
        Row secondRow = rowList.get(1);

        assert firstRow.getInt(0) == 15;
        assert firstRow.getString(1).equals("7");
        assert firstRow.getLong(2) == 1;

        assert secondRow.getInt(0) == 14;
        assert secondRow.getString(1).equals("7");
        assert secondRow.getLong(2) == 1;
    }

    @Test
    public void testThreeLogs(){

        JavaSparkContext sc = new JavaSparkContext(spess.sparkContext());
        JavaRDD<String> shrn = sc.parallelize(Arrays.asList(testString1, testString2, testString3));
        JavaRDD<Row> result = countLogLevelPerHour(spess.createDataset(shrn.rdd(), Encoders.STRING()));
        List<Row> rowList = result.collect();
        Row firstRow = rowList.get(0);
        Row secondRow = rowList.get(1);
        Row thirdRow = rowList.get(2);

        assert firstRow.getInt(0) == 15;
        assert firstRow.getString(1).equals("7");
        assert firstRow.getLong(2) == 1;

        assert secondRow.getInt(0) == 14;
        assert secondRow.getString(1).equals("2");
        assert secondRow.getLong(2) == 1;

        assert thirdRow.getInt(0) == 14;
        assert thirdRow.getString(1).equals("7");
        assert thirdRow.getLong(2) == 1;
    }

}
