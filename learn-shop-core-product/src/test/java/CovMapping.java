import cn.hutool.core.util.ReflectUtil;
import com.billow.mybatis.base.HighLevelApi;
import io.swagger.annotations.Api;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CovMapping
{
    public static void main(String[] args)
    {
        List<String> urls = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("product", "com.billow.product.api");
        for (String key : map.keySet())
        {
            //反射工具包，指明扫描路径
            Reflections reflections = new Reflections(map.get(key));
            Set<Class<?>> setClass = reflections.getTypesAnnotatedWith(RequestMapping.class);
            for (Class<?> aClass : setClass)
            {
                // 获取类上面的公有url
                RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
                String commonMapping = annotation.value()[0];

                // 获取注释信息
                Api api = aClass.getAnnotation(Api.class);
                String common = api.value();
                System.out.println(common);

                commonMapping = commonMapping.substring(0, commonMapping.lastIndexOf("Api"));
                // 获取父类公用方法
                if (HighLevelApi.class.isAssignableFrom(aClass))
                {
                    getUrl(key, urls, HighLevelApi.class, commonMapping);
                }
                getUrl(key, urls, aClass, commonMapping);
            }
        }

        Set<String> collect = urls.stream()
                .map(m -> {
                    if (m.indexOf("{") > -1)
                    {
                        m = m.substring(0, m.lastIndexOf("/"));
                    }
                    return m.replaceAll("\\/", "\\:");
                })
                .collect(Collectors.toSet());

        for (String s : collect)
        {
            System.out.println(s);
        }

    }


    private static void getUrl(String prefix, List<String> urls, Class<?> aClass, String commonMapping)
    {
        Method[] methods = ReflectUtil.getMethodsDirectly(aClass, false);
        for (Method method : methods)
        {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            if (Objects.nonNull(getMapping))
            {
                urls.add(prefix + commonMapping + getMapping.value()[0]);
                continue;
            }
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            if (Objects.nonNull(postMapping))
            {
                urls.add(prefix + commonMapping + postMapping.value()[0]);
                continue;
            }
            DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
            if (Objects.nonNull(deleteMapping))
            {
                urls.add(prefix + commonMapping + deleteMapping.value()[0]);
                continue;
            }
            PutMapping putMapping = method.getAnnotation(PutMapping.class);
            if (Objects.nonNull(putMapping))
            {
                urls.add(prefix + commonMapping + putMapping.value()[0]);
                continue;
            }
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (Objects.nonNull(requestMapping))
            {
                urls.add(prefix + commonMapping + requestMapping.value()[0]);
                continue;
            }
        }
    }


}
