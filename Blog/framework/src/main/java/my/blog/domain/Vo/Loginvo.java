package my.blog.domain.Vo;

import com.qiniu.rtc.model.RoomResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loginvo {
    private String token;
    private UserInfoVo userInfo;
}
