package top.misec.config;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import top.misec.utils.LoadFileResource;

@Data
@Log4j2
public class Config {
    private boolean skipDailyTask;

    public int getPredictNumberOfCoins() {
        if (predictNumberOfCoins > 10) {
            predictNumberOfCoins = 10;
        }
        return predictNumberOfCoins;
    }

    private int predictNumberOfCoins;
    private int minimumNumberOfCoins;
    private static Config CONFIG = new Config();

    public static Config getInstance() {
        return CONFIG;
    }

    @Override
    public String toString() {
        return "Config{" +
                "skipDailyTask=" + skipDailyTask +
                ", predictNumberOfCoins=" + predictNumberOfCoins +
                ", minimumNumberOfCoins=" + minimumNumberOfCoins +
                '}';
    }

    public void ConfigInit() {
        String configJson = null;
        String outConfig = LoadFileResource.loadConfigJsonFromFile();
        if (outConfig != null) {
            configJson = outConfig;
            log.info("读取外部配置文件成功");
        } else {
            String temp = LoadFileResource.loadJsonFromAsset("config.json");
            configJson = temp;
            log.info("读取配置文件成功");
        }

        Config.CONFIG = new Gson().fromJson(configJson, Config.class);
        log.info(Config.getInstance().toString());
    }
}
