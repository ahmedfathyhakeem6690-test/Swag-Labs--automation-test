package utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;


public class DataDriven {

    private static final String PATH = "src/main/resources/testdata/testData.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // نفس الدالة القديمة، بس بتقرأ من loginData
    public static Object[][] jsonReader() throws IOException {
        JsonNode root = mapper.readTree(new File(PATH));
        List<Map<String, String>> list = mapper.convertValue(
                root.get("loginData"),
                new TypeReference<List<Map<String, String>>>() {});

        Object[][] data = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i);
        }
        return data;
    }

    // الدالة الجديدة للمنتجات
    public static List<String> getCartProducts() throws IOException {
        JsonNode root = mapper.readTree(new File(PATH));
        return mapper.convertValue(
                root.get("cartProducts"),
                new TypeReference<List<String>>() {});
    }
    // get valid user
    public static Map<String, String> getValidUser() throws IOException {
        JsonNode root = mapper.readTree(new File(PATH));
        for (JsonNode n : root.get("loginData")) {
            if ("success".equals(n.get("expected").asText())) {
                return mapper.convertValue(n, new TypeReference<Map<String, String>>() {});
            }
        }
        throw new IllegalStateException("No user with expected=success in loginData");
    }
}

