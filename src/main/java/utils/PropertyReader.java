package utils;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by n_lebedko on 21.03.2016.
 */
public class PropertyReader {

    private static PropertyReader instance;
    private static final String CONFIG_FILE = "web.properties";
    private static final String GLOBAL_CONFIG = "config.properties";

    private static final Logger LOG = getLogger(PropertyReader.class);

    private Map<String, String> properties = new HashMap<>();
    private static Map<String, String> globalProperties = new HashMap<>();

    private PropertyReader(String resource) {
        loadResource(resource);
    }

    private PropertyReader() {
        loadResource(null);
    }

    static {
        try (InputStream resourceAsStream = PropertyReader.class.getClassLoader()
                .getResourceAsStream(GLOBAL_CONFIG)) {
            LOG.info("LOAD GLOBAL CONFIG - " + GLOBAL_CONFIG);
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(resourceAsStream,
                    Charset.forName("UTF-8")));
            Properties newProp = new Properties();
            newProp.load(reader);
            for (String name : newProp.stringPropertyNames()) {
                globalProperties.put(name, newProp.getProperty(name));
            }
            System.out.println(globalProperties);
        } catch (IOException e) {
            LOG.error("Failed to load global properties: " + CONFIG_FILE + "\n" + e);
            e.printStackTrace();
        }
    }

    public static PropertyReader getInstance(String resource) {
        if (instance == null) {
            instance = new PropertyReader(resource);
        }
        return instance;
    }

    public static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    private void loadResource(String resource) {
        String configFile = (resource == null ? CONFIG_FILE : resource);
        try (InputStream resourceAsStream = PropertyReader.class.getClassLoader().getResourceAsStream(configFile)) {
            load(resourceAsStream);
        } catch (IOException e) {
            LOG.error("Failed to load default properties: " + CONFIG_FILE + "\n" + e);
            e.printStackTrace();
        }
    }

    public void load(InputStream inputStream) throws IOException {
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        Properties newProp = new Properties();
        newProp.load(reader);
        for (String name : newProp.stringPropertyNames()) {
            properties.put(name, newProp.getProperty(name));
        }
    }

/*  TODO

    public String getPlayerLoginName() {
        return getProperty(PLAYER_LOGIN_NAME);
    }

    public static String getDbConnUrl(String staging) {
        return getGlobalProperty(staging);
    }
*/


    private String getProperty(String property) {
        String prop = properties.get(property);
        assertNotNull(property + " is null!", prop);
        return prop;
    }

    public static String getGlobalProperty(String property) {
        String prop = globalProperties.get(property);
        assertNotNull(property + " is null!", prop);
        return prop;
    }

}
