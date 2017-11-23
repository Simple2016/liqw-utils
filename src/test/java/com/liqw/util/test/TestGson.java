package com.liqw.util.test;

import com.liqw.util.json.GsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqw on 2017/10/13.
 */
public class TestGson {
    Logger logger = LoggerFactory.getLogger(TestGson.class);
    TestJsonBean testJsonBean = new TestJsonBean();
    @Before
    public void before() {
        testJsonBean.setB(true);
        testJsonBean.setI(9);
        testJsonBean.setS("[{\"a\":\"string\",\"i\":9,\"b\":true}]");//java.lang.ClassCastException@3cb15770- had objects of type "[Ljava.lang.String;" but expected signature"java.lang.String"
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        testJsonBean.setStrings(strings);
    }
    @Test
    public void TestGson() {
        //对象与json 互转
        String s = GsonUtils.toJson(testJsonBean);
        logger.info("GsonUtils.toJson:{}==>{}", testJsonBean, s);
        TestJsonBean testJsonBean1 = GsonUtils.fromJson(s, TestJsonBean.class);
        logger.info("GsonUtils.fromJson:{}==>{}", s, testJsonBean1);
        //list 与 json 互转
        List<TestJsonBean> testJsonBeanList = new ArrayList<>();
        testJsonBeanList.add(testJsonBean);
        testJsonBeanList.add(testJsonBean1);
        String l = GsonUtils.toJson(testJsonBeanList);
        logger.info("GsonUtils.toJson:{}==>{}", testJsonBeanList, l);
        ArrayList<TestJsonBean> arrayLists = GsonUtils.fromJsonList(l, TestJsonBean.class);
        logger.info("GsonUtils.fromJsonList:{}==>{}", l, arrayLists);
    }


    static class TestJsonBean {
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

        @Override
        public String toString() {
            return "TestJsonBean{" +
                    "s='" + s + '\'' +
                    ", i=" + i +
                    ", b=" + b +
                    ", strings=" + strings +
                    '}';
        }
    }
}
