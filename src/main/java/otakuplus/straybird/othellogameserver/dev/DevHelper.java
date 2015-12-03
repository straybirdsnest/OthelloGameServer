package otakuplus.straybird.othellogameserver.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

public class DevHelper {

    private static final Logger logger = LoggerFactory.getLogger(DevHelper.class);

    public static void initDb(String[] args) {
        initDbWithScript(args, "/dev/init.sql");
    }

    public static void initDbWithoutTables(String[] args) {
        initDbWithScript(args, "/dev/recreate_database.sql");
    }

    private static void initDbWithScript(String[] args, String script) {
        try {
            String username, password, url, driver;
            InputStream is = DevHelper.class.getResourceAsStream("/application.properties");
            Properties props = new Properties();
            props.load(is);

            // find from application.properties
            username = props.getProperty("spring.datasource.username");
            password = props.getProperty("spring.datasource.password");
            url = props.getProperty("spring.datasource.url");
            driver = props.getProperty("spring.datasource.driverClassName");

            // find form system env
            String usernameEnv = System.getenv("spring.datasource.username");
            String passwordEnv = System.getenv("spring.datasource.password");
            String urlEnv = System.getenv("spring.datasource.url");
            String driverEnv = System.getenv("spring.datasource.driverClassName");
            if (usernameEnv != null) {
                username = usernameEnv;
            }
            if (passwordEnv != null) {
                password = passwordEnv;
            }
            if (urlEnv != null) {
                url = urlEnv;
            }
            if (driverEnv != null) {
                driver = driverEnv;
            }

            // find from command line arguments
            Optional<String> _username = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.username")).findFirst();
            if (_username.isPresent()) {
                username = _username.get().split("=")[1];
            }
            Optional<String> _password = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.password")).findFirst();
            if (_password.isPresent()) {
                password = _password.get().split("=")[1];
            }
            Optional<String> _url = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.url")).findFirst();
            if (_url.isPresent()) {
                url = _url.get().split("=")[1];
            }
            Optional<String> _driver = Arrays.stream(args)
                    .filter(e -> e.contains("spring.datasource.driverClassName")).findFirst();
            if (_driver.isPresent()) {
                driver = _driver.get().split("=")[1];
            }

            // connect to datebase
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);


            // run init.sql
            ScriptRunner runner = new ScriptRunner(conn, false, true);
            runner.runScript(new BufferedReader(new InputStreamReader(
                    DevHelper.class.getResourceAsStream(script)
            )));

            //检查系统是否windows，若是则去掉盘符前面的反斜杠
            boolean isWindows;
            isWindows = System.getProperty("os.name").startsWith("Windows");

            Statement stat = conn.createStatement();

            for(int id=1;id<13;id++){
                String sql = "INSERT INTO `othellogamedb`.`othello_game_table` (`game_table_id`) VALUES ('"+id+"');";
                stat.execute(sql);
            }
            stat.close();
            conn.close();

            System.out.println("=== 测试数据库初始化完毕 ===\n");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
