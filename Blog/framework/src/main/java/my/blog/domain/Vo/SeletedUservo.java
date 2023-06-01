package my.blog.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeletedUservo {
    private List<Long> roleIds;
    private List<Rolevo> roles;
    Uservo user;

}
