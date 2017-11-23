package com.liqw.util.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liqw on 2017/10/13.
 */
public class TestJackson {
    Logger logger = LoggerFactory.getLogger(TestJackson.class);
    TestJsonBean testJsonBean = new TestJsonBean();

    @Before
    public void before() {
        testJsonBean.setAge("123");
        testJsonBean.setBirthday(new Date());
        testJsonBean.setEmail("email");
        testJsonBean.setB(true);
        testJsonBean.setI(9);
        testJsonBean.setS("[{\"a\":\"string\",\"i\":9,\"b\":true}]");//java.lang.ClassCastException@3cb15770- had objects of type "[Ljava.lang.String;" but expected signature"java.lang.String"
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        testJsonBean.setStrings(strings);
    }

    //http://blog.csdn.net/accountwcx/article/details/24585987
    //http://blog.csdn.net/java_huashan/article/details/46375857
    //Jackson提供了三种可选的Json处理方法：流式API(Streaming API) 、树模型(Tree Model)、数据绑定(Data Binding)。从使用角度来看，比较一下这三种处理Json的方式的特性：
    //Streaming API：是效率最高的处理方式(开销低、读写速度快，但程序编写复杂度高)
    //Tree Model：是最灵活的处理方式
    //Data Binding：是最常用的处理方式
    @Test
    public void TestJackson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 为了使JSON视觉上的可读性，增加一行如下代码，注意，在生产中不需要这样，因为这样会增大Json的内容
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // 配置mapper忽略空属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 默认情况，Jackson使用Java属性字段名称作为 Json的属性名称,也可以使用Jackson annotations(注解)改变Json属性名称

        //当反序列化json时，未知属性会引起的反序列化被打断，这里我们禁用未知属性打断反序列化功能，
        //因为，例如json里有10个属性，而我们的bean中只定义了2个属性，其它8个属性将被忽略
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //对象与json 互转
        String s = mapper.writeValueAsString(testJsonBean);
        logger.info("mapper.writeValueAsString:{}==>{}", testJsonBean, s);
        TestJsonBean testJsonBean1 = mapper.readValue(s, TestJsonBean.class);
        logger.info("mapper.readValue:{}==>{}", s, testJsonBean1);
        //list 与 json 互转
        List<TestJsonBean> testJsonBeanList = new ArrayList<>();
        testJsonBeanList.add(testJsonBean);
        testJsonBeanList.add(testJsonBean1);
        String l = mapper.writeValueAsString(testJsonBeanList);
        logger.info("mapper.writeValueAsString:{}==>{}", testJsonBeanList, l);
        //json 转list 第一种方法 比较麻烦
        //先反序列化复杂类型 为泛型的Collection Type。
        //如果是ArrayList<YourBean>那么使用ObjectMapper 的getTypeFactory().constructParametricType(collectionClass, elementClasses);
        //如果是HashMap<String,YourBean>那么 ObjectMapper 的getTypeFactory().constructParametricType(HashMap.class,String.class, YourBean.class);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, TestJsonBean.class);
        ArrayList<TestJsonBean> arrayLists = mapper.readValue(l, javaType);
        logger.info(" mapper.readValue:{}==>{}", l, arrayLists);
        //json 转list 第二种方法
        List<TestJsonBean> beanList = mapper.readValue(l, new TypeReference<List<TestJsonBean>>() {
        });
        logger.info(" mapper.readValue:{}==>{}", l, beanList);

    }


    static class TestJsonBean {
        //不JSON序列化年龄属性
        @JsonIgnore
        private String age;
        //格式化日期属性
        @JsonFormat(pattern = "yyyy年MM月dd日")
        private Date birthday;
        //序列化email属性为mail
        @JsonProperty("mail")
        private String email;

        private String s;
        private int i;
        private boolean b;
        private List<String> strings;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public boolean isB() {
            return b;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public List<String> getStrings() {
            return strings;
        }

        public void setStrings(List<String> strings) {
            this.strings = strings;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "TestJsonBean{" +
                    "age='" + age + '\'' +
                    ", birthday=" + birthday +
                    ", email='" + email + '\'' +
                    ", s='" + s + '\'' +
                    ", i=" + i +
                    ", b=" + b +
                    ", strings=" + strings +
                    '}';
        }
    }
}
