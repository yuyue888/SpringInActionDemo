package config.support;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * 自定义Jackson2 全局返回的时间格式
 * Created by ssc on 2017/7/19 0019.
 */
public class CustomDateMapper extends ObjectMapper {

    public CustomDateMapper() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        super.setDateFormat(sdf);
    }
}
