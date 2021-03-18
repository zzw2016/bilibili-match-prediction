package top.misec.task;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import top.misec.apiquery.ApiList;
import top.misec.pojo.userinfobean.Data;
import top.misec.utils.HelpUtil;
import top.misec.utils.HttpUtil;

import static top.misec.task.TaskInfoHolder.STATUS_CODE_STR;


/**
 * 登录检查
 *
 * @author @JunzhouLiu @Kurenai
 * @since 2020-11-22 4:57
 */
@Log4j2
public class UserCheck implements Task {

    private final String taskName = "登录检查";

    @Override
    public void run() {
        String requestPram = "";
        JsonObject userJson = HttpUtil.doGet(ApiList.LOGIN + requestPram);
        JsonObject matchInfo= HttpUtil.doGet(ApiList.queryMatchInfo)
                .get("data").getAsJsonObject()
                .get("guess").getAsJsonObject();
        if (userJson == null) {
            log.info("用户信息请求失败，如果是412错误，请在config.json中更换UA，412问题仅影响用户信息确认，不影响任务");
        } else {
            userJson = HttpUtil.doGet(ApiList.LOGIN);
            //判断Cookies是否有效
            if (userJson.get(STATUS_CODE_STR).getAsInt() == 0
                    && userJson.get("data").getAsJsonObject().get("isLogin").getAsBoolean()) {
                log.info("Cookies有效，获取预测信息成功");
                log.info("预测总场数:{}",matchInfo.get("total_success").getAsInt());
                log.info("预测胜场数:{}",matchInfo.get("total_success").getAsInt());
                log.info("预测胜率:{}%",matchInfo.get("success_rate").getAsDouble());
                log.info("预测总投入:{}硬币",matchInfo.get("total_stake").getAsInt());
                log.info("预测累计收入:{}硬币",matchInfo.get("total_income").getAsDouble());
            } else {
                log.debug(String.valueOf(userJson));
                log.warn("Cookies可能失效了,请仔细检查Github Secrets中DEDEUSERID SESSDATA BILI_JCT三项的值是否正确、过期");
            }

        }

    }

    @Override
    public String getName() {
        return taskName;
    }
}
