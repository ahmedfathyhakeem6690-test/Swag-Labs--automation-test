package utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;


public class DataDriven {
        public static Object[][] jsonReader() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/testdata/testData.json");
            List<Map<String, String>> list = mapper.readValue(
                    file, new TypeReference<List<Map<String, String>>>() {});
            Object[][] data = new Object[list.size()][1];
            for (int i = 0; i < list.size(); i++) {
                data[i][0] = list.get(i);
            }
            return data;
        }

}

