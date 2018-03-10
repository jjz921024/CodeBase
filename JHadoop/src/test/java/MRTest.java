import com.google.common.collect.Lists;
import gdut.example.workage.WorkAgeMapper;
import gdut.example.workage.WorkAgeReducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Jun on 2018/2/27.
 */
public class MRTest {

    private MapDriver mapDriver;
    private ReduceDriver reduceDriver;
    private MapReduceDriver mapReduceDriver;

    @Before
    public void setUp(){
        WorkAgeMapper mapper = new WorkAgeMapper();
        mapDriver = MapDriver.newMapDriver(mapper);

        WorkAgeReducer reducer = new WorkAgeReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);

        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void testMapper() throws IOException, ParseException {
        Text text = new Text("10001,1953-09-02,Georgi,Facello,M,1986-06-26");

        mapDriver.withInput(new LongWritable(), text)
                .withOutput(new Text("M"), new Text("33"))
                .runTest();

        List<Pair> expectedOutputList = mapDriver.getExpectedOutputs();
        for(Pair pair : expectedOutputList){
            System.out.println(pair.getFirst() + " --- " + pair.getSecond());
        }
    }

    @Test
    public void testReducer() throws IOException {
        List<Text> textList = Lists.newArrayList();
        textList.add(new Text("33"));
        textList.add(new Text("34"));
        textList.add(new Text("35"));

        reduceDriver.withInput(new Text("M"), textList)
                .withOutput(new Text("M"), new Text("34"))
                .runTest();

        List<Pair> expectedOutputList = reduceDriver.getExpectedOutputs();
        for(Pair pair : expectedOutputList){
            System.out.println(pair.getFirst() + " --- " + pair.getSecond());
        }
    }

    @Test
    public void testMapperAndReducer() throws IOException {
        Text text = new Text("10001,1953-09-02,Georgi,Facello,M,1986-06-26");

        mapReduceDriver.withInput(new LongWritable(), text)
                .withOutput(new Text("M"), new Text("33"))
                .runTest();

        List<Pair> expectedOutputList = mapReduceDriver.getExpectedOutputs();
        for(Pair pair : expectedOutputList){
            System.out.println(pair.getFirst() + " --- " + pair.getSecond());
        }
    }
}
