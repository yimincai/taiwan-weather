package yimincai.net.taiwanweather.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 必需標註為 @Service 否則 Spring IoC 掃描時不會註冊此依賴 會導致 @Autowired 時找不到Service
 */
@Service
public class CityService {
    // 定義所有可選的城市清單
    public static List<String> getCityList() {
        return Arrays.asList("臺北市", "新北市", "桃園市", "臺中市", "臺南市", "高雄市", "宜蘭縣", "新竹縣", "苗栗縣", "彰化縣", "南投縣", "雲林縣", "嘉義縣", "屏東縣", "花蓮縣", "臺東縣", "澎湖縣", "基隆市", "新竹市", "嘉義市");
    }
}
