package com.mafashen.java;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author mafashen
 * @since 2019-08-10.
 */
public class MarkSix {

    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        while (true){
//            int num = scanner.nextInt();
//            System.out.println(new MarkSixProperty(num).toString());
//        }

        for (int i = 1; i <= 49; i++) {
            System.out.println(new MarkSixProperty(i).toString());
        }
    }

    public static final String[] twelve = {"猪","狗","鸡","猴","羊","马","蛇","龙","兔","虎","牛","鼠"};

    /**家肖*/
    public static final Set<String> jiaXiao = Sets.newHashSet("猪","狗", "羊", "牛", "马", "鸡");

    /**外肖*/
    public static final Set<String> waiXiao = Sets.newHashSet("龙","鼠", "兔", "虎", "蛇", "猴");

    /**前肖*/
    public static final Set<String> qianXiao = Sets.newHashSet("鼠","牛", "兔", "虎", "龙", "蛇");

    /**后肖*/
    public static final Set<String> houXiao = Sets.newHashSet("羊","马", "猴", "鸡", "狗", "猪");

    /**男肖*/
    public static final Set<String> nanXiao = Sets.newHashSet("龙","马", "鼠", "虎", "牛", "狗", "猴");

    /**女肖*/
    public static final Set<String> nvXiao = Sets.newHashSet("兔","蛇", "鸡", "猪", "羊");

    /**天肖*/
    public static final Set<String> tianXiao = Sets.newHashSet("龙","马", "兔", "牛", "猴", "猪");

    /**地肖*/
    public static final Set<String> diXiao = Sets.newHashSet("龙","马", "兔", "虎");

    /**阴肖*/
    public static final Set<String> yinXiao = Sets.newHashSet("鼠","马", "蛇", "龙", "猪", "狗");

    /**阳肖*/
    public static final Set<String> yangXiao = Sets.newHashSet("龙","马", "兔", "虎");

    /**大肖*/
    public static final Set<String> daXiao = Sets.newHashSet("牛", "虎", "马", "羊", "狗", "猪");

    /**小肖*/
    public static final Set<String> xiaoXiao = Sets.newHashSet("龙","马", "兔", "虎");

    /**红肖*/
    public static final Set<String> hongXiao = Sets.newHashSet("鼠", "兔", "马", "鸡");

    /**蓝肖*/
    public static final Set<String> lanXiao = Sets.newHashSet("虎", "蛇", "猴", "猪");

    /**绿肖*/
    public static final Set<String> lvXiao = Sets.newHashSet("牛", "龙", "羊", "狗");

    /**
     * 五行
     */
    public static  Map<String, String> wuXingMap = new HashMap<>();
    public static Set<Integer> numRed = Sets.newHashSet(1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45,46);
    public static Set<Integer> numBlue = Sets.newHashSet(3,4,9,10,14,15,20,25,26,31,36,37,41,42,47,48);
    public static Set<Integer> numGreen = Sets.newHashSet(5,6,11,16,17,21,22,27,28,32,33,38,39,43,44,49);
    static {
        wuXingMap.put("猴", "金");
        wuXingMap.put("鸡", "金");
        wuXingMap.put("虎", "木");
        wuXingMap.put("兔", "木");

        wuXingMap.put("鼠", "水");
        wuXingMap.put("猪", "水");
        wuXingMap.put("蛇", "火");
        wuXingMap.put("马", "火");

        wuXingMap.put("牛", "土");
        wuXingMap.put("羊", "土");
        wuXingMap.put("龙", "土");
        wuXingMap.put("狗", "土");
    }

    static class MarkSixProperty{

        public MarkSixProperty(int num) {
            this.num = num;
            this.shengXiao = twelve[(num -1) % 12];
            this.jiaWai = jiaXiao.contains(this.shengXiao) ?  "家肖" : "外肖";
            this.tianDi = tianXiao.contains(this.shengXiao) ? "天肖" : "地肖";
            this.yinYang = yinXiao.contains(this.shengXiao) ? "阴肖" : "阳肖";
            this.color = hongXiao.contains(this.shengXiao) ? "红肖" : (lanXiao.contains(this.shengXiao) ? "蓝肖" : "绿肖");
            this.nanNv = nanXiao.contains(this.shengXiao) ? "男肖" : "女肖";
            this.daxiao = daXiao.contains(this.shengXiao) ? "大肖" : "小肖";
            this.wuXing = wuXingMap.get(this.shengXiao);
            this.qianHou = qianXiao.contains(this.shengXiao) ? "前肖" : "后肖";
            this.shuBo = numRed.contains(num) ? "红波" : (numBlue.contains(num) ? "蓝波" : "绿波");
            this.danShuang = num % 2 ==0 ? "双" : "单";
            this.heShuDanShuang = getHeShuDanShuang();
            this.daXiaoShu = num < 25 ? "小" : "大";
        }

        public String getHeShuDanShuang(){
            //个位
            int ge = num % 10;
            //十位
            int shi = num / 10;
            return (ge + shi) %2 == 0 ? "双" : "单";
        }

        public String shengXiao;

        public int num;

        public String yinYang;

        public String jiaWai;

        public String tianDi;

        public String nanNv;

        public String color;

        public String wuXing;

        public String qianHou;

        public String daxiao;

        public String shuBo;

        public String danShuang;

        public String heShuDanShuang;

        public String daXiaoShu;


        @Override
        public String toString() {
            return "" +
                    shengXiao + '\t' +
                    num + '\t' +
                    shuBo + '\t' +
                    danShuang + '\t' +
                    wuXing + '\t' +
                    jiaWai + '\t' +
                    daXiaoShu + '\t' +
                    heShuDanShuang + '\t' +
                    qianHou + '\t' +
                    nanNv + '\t' +
                    yinYang + '\t' +
                    tianDi + '\t' +
                    color + '\t' +
                    daxiao + '\t' ;
        }
    }
}
