package utils;

import consts.PropertiesNames;
import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

import static java.io.File.separator;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by n_lebedko on 20.02.2015.
 */
public class Utils {

    private static final String PNG = ".png";
    private static final String UNDERLINE = "_";
    private static final Logger LOG = getLogger(Utils.class);

    private static String currentSessionFolderPath = "NOT_SPECIFIED_YET";

    static {
        String pathToScreenshotsRoot = PropertyReader.getGlobalProperty(PropertiesNames.PATH_TO_SCREENSHOTS);
        if (!(new File(pathToScreenshotsRoot)).exists()) {
            LOG.warn("Folder does not exist - " + pathToScreenshotsRoot);
            (new File(pathToScreenshotsRoot)).mkdir();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy'T'HH-mm-ss");
        String currentTime = formatter.format(Calendar.getInstance().getTime());
        String dayFolderPath = pathToScreenshotsRoot + separator + (new SimpleDateFormat("MM")).format(new Date())
                + separator + currentTime.split("T")[0];
        File dayFolder = new File(dayFolderPath);
        if (!dayFolder.exists()) {
            dayFolder.mkdir();
        }
        currentSessionFolderPath = dayFolderPath + separator + currentTime.split("T")[1];
        File currentSessionFolder = new File(currentSessionFolderPath);
        currentSessionFolder.mkdir();
    }

    public static Map<String, String> convertToStringMap(Map<String, Object> map) {
        Map<String, String> converted = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            converted.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return converted;
    }

    public static int getRandomInt(int min, int max) {
        int randomNum = (new Random()).nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static String getEnvVariable() {
        return System.getenv("todoAddEnvPropIfNedeed");
    }

    public static boolean waitFor(long timeoutMillis, Callable<Boolean> function) {
        long endTime = System.currentTimeMillis() + timeoutMillis;
        try {
            while (!Thread.interrupted() && System.currentTimeMillis() < endTime) {
                try {
                    if (function.call()) {
                        return true;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static File doScreenshot(WebDriver driver) throws Exception {
        File file = null;
        try {
//            assertTrue("Game page is not opened!", driver.getCurrentUrl().contains("static"));
            file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Error e) {
            LOG.error("Cannot take screenshot, error found");
            LOG.error("DRIVER: " + driver.toString());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            LOG.error("Cannot take screenshot, exception found");
            LOG.error("DRIVER: " + driver.toString());
            e.printStackTrace();
            throw e;
        }
        return file;
    }


    public static void saveScreenshot(File screen, Scenario scenario) {
        if (screen == null) {
            LOG.error("Screenshots are empty!");
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
        String currentTime = formatter.format(Calendar.getInstance().getTime());
        String fileName = separator + currentTime + UNDERLINE + scenario.getName() + PNG;

        try {
            FileUtils.copyFile(screen, new File(currentSessionFolderPath + fileName));
            LOG.error("Created screenshots: \n" + currentSessionFolderPath + fileName);
        } catch (IOException io) {
            LOG.error("Cannot save screenshots: \n" + currentSessionFolderPath + fileName);
            io.printStackTrace();
        }
    }


    public static void saveScreenshot(File playerScreen, String fileName) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
        String currentTime = formatter.format(Calendar.getInstance().getTime());
        String playerFileName = separator + currentTime + UNDERLINE + UNDERLINE + fileName + PNG;
        try {
            FileUtils.copyFile(playerScreen, new File(currentSessionFolderPath + playerFileName));
            LOG.error("Created screenshots: \n" + currentSessionFolderPath + playerFileName);
        } catch (IOException io) {
            LOG.error("Cannot save screenshots: \n" + currentSessionFolderPath + playerFileName);
            io.printStackTrace();
        }
    }


    public static void saveBiProperty(String property, String nicknameOpponent) throws IOException {
        Map<String, String> properties = new HashMap<String, String>();
        String pathToProps = System.getProperty("user.home")
                + System.getProperty("file.separator") + "bi_test.properties";
        File file = new File(pathToProps);
        if (!file.exists()) {
            file.createNewFile();
        }
        InputStream input = new FileInputStream(pathToProps);
        OutputStream output = null;
        try {
            Properties newProp = new Properties();

            newProp.load(input);
            for (String name : newProp.stringPropertyNames()) {
                properties.put(name, newProp.getProperty(name));
            }
            properties.put(property, nicknameOpponent);
            newProp.putAll(properties);
            output = new FileOutputStream(pathToProps);
            newProp.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    public static String getBiProperty(String property) throws IOException {
        String pathToProps = System.getProperty("user.home")
                + System.getProperty("file.separator") + "bi_test.properties";
        File file = new File(pathToProps);
        if (!file.exists()) {
            assertNotNull("Bi property file not found, " + pathToProps, file.exists());
        }
        InputStream input = new FileInputStream(pathToProps);
        try {
            Properties newProp = new Properties();

            newProp.load(input);
            return newProp.getProperty(property);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return null;
    }

    public static String getDateTime() {
        SimpleDateFormat a = new SimpleDateFormat("yyyy_MM_dd_HHmm");
        return a.format(new Date());
    }

    public static void clearResults() {
        File resultsFolder = new File(currentSessionFolderPath);
        if (resultsFolder.list().length == 0) {
            resultsFolder.delete();
            LOG.info("All tests passed. Delete empty folder " + currentSessionFolderPath);
        }
    }

}
