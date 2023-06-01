package my.blog.utils;


import my.blog.domain.Vo.Menutreevo;
import my.blog.domain.entity.Menu;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopy {

    private BeanCopy() {
    }

    //把source对象拷贝到clazz中，传入Class clazz字节码，通过反射出对象,并使用泛型，让传入对象决定返回对象
    public static <V> V copyBean(Object source,Class<V> clazz) {
        //通过反射创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回结果,未在作用域提升作用域
        return result;


    }
    //O为传入对像的泛型
    public static <O,V> List<V> copyBeanList(@NotNull List<O> list, Class<V> Clazz){
        return list.stream()
                .map(o -> copyBean(o, Clazz))
                .collect(Collectors.toList());

    }


}