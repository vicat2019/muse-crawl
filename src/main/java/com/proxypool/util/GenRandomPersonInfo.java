package com.proxypool.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.RandomUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 随机生成用户信息
 */
public class GenRandomPersonInfo {

    private static String firstName = "黄";
    private static String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽";
    private static String girl_2 = "若雨梦洁凌薇美莲雅静雪丽依娜雅芙雨婷晟涵梦舒秀影海琼雪娴梦梵笑薇瑾梅晟楠歆婷思颖欣然可岚天瑜婧琪媛馨玥婷滢心雪馨姝瑗颖娟歆瑶凌菲钰琪婧宸靖瑶瑾萱佑怡婳祎檀雅若翾熙雯语嫣妍洋滢玮沐卉琪涵佳琦伶韵思睿清菡欣溶菲絮璇滢静馨妙菱心琪雅媛晨芙婧诗露雪蕊琪舒雅婉玗怡悦诗茵静璇婕珍婉婷云薇霏羽妍琦珂玥茗茶昭雪倩雪玉珍茹雪正梅美琳欢馨优璇雨嘉娅楠明美可馨惠茜漫妮香茹月婵嫦曦凌薇美莲雅芙韵寒莉姿梦璐灵芸昭雪倩雪茹雪正梅美琳欢馨优璇雨嘉娅楠明美惠茜漫妮香茹月婵嫦曦静香美莲怡香韵寒梦璐沛玲欣妍玉珍美琳欢馨优璇雨嘉可馨雅芙曼婷雪慧淑颖钰彤璟雯天瑜婧琪彤萱玥婷媛馨梦涵碧萱慧妍璟雯梦婷雪怡彦歆芮涵婧涵鑫蕾希蓝";
    private static String girl_3 = "熙雯诗涵宁馨妙菱诗婧瑾萱欣妍茹雪美琳欢馨雨嘉娅楠明美可馨钰彤彤萱月婵嫦曦静香美莲茹珺云舒珺睿惠雅珺菡安娜媛娜惠睿晴茜岚嫦云涵晴惠怡翎裕梅涵惠惠絮涵菡雯婷寒淑润洁秉文晴清淑涵珺涵云华舒媛素娅花曼岚雅清华寒菊涵茵岚菡欣琳熙玉岚菲寒云茹絮寒媛岚瑜正妍琳竣淑淑惠语寒华涵婷晴珺妍如榕嫣寒瑜云嫦茵清茵嫣惠云洁玲雨蓉翔雯淑梦晴菡珺云清雅梓婧雯婧雯嘉雯舒茜菡云嫣清梦秀珊欣怡惠茜茜华茜茜舒菲婷雯晓悦芷秀欣瑶曦秀婷丽莉娜东玲巧娜佳艳秀秀新颖依娜欣瑶梦洁菁茹泽芳怡若陈红婧宁美怡悦帆莹莹莉绫德梅燕萍瑛蔓鹤梅蓉华佳莉蔡琳婧妍斯玉恺玲珂妍小莉成美倩冰优美长丽瑜文姝瑶春颖华琳妍婷娉娟燕艳妍青静美禾悦妍凌惋洁清嘉茜媛涵茹惠晴茵惠岚雁茜菲媛菲珺绮茹菲涵嫣翔鸣雨花思艳寒岚茜嫦茜菊涵媛淑云辰蓉贞颖舒嫣清云茵萍淑华媛语琳爰君茹云晴寒鸣云清岚华淑惠颜英真文雯语晴茹雯晴雯睿清茜青蓉妍羽芬迎恬梨芙婕宸旋翾琳丽霞悦淇桐卿宸英芳梨铭倩睿琳莉娉纯雪仲媛湘怡怡丞秀琴滇萍薇颖媛媛女生名字唯美映蓉长英丽美夕文琳涵燕星善玲琴子筱雪旭妍蝾婷怡婷莉颖芹悦翔嘉晴岚晴翠雅舒茹语若美娅清翔媛惠嘉云絮茹云雯翔寒睿舒玉雅淑雯嫣岚茹雯淑茜云寒嫣涵绮晴睿茜梦云菡岚萍茵雅莉萍梦丽涵菲茵茹晴茵岚婷涵语雯瑛惠淑寒雁淑雅岚舒婷雯梓悦秀娟瑶华芝蓉娅庭树艳小霞厦洁卷玉欣颖世悦艳蓉丹洁玲娇卓妍杭英玲丽睿颖炜琳雨莹炜婷丽英忠燕瑞芬婧文惠芳炎琳秀颖翠萍爱茹艳霞龙艳嫣钰志芳芷茹悦颖红英焱霞婷秀惠玲于娜沁媛诺瑶梓燕秀君昊怡小妍悦张秀芳欣怡晓梅午瑶会霞龙梅琳敏展文羽莹艺萍月玲歌玲海燕晶婧尤文仪琳玉娟钰洁雪娟卿蓉晓洁家颖安茹湘媛丹梅薏冉婷昱怡可琳淼卓婷瓶花爱萍桂燕石英腊梅雅莉莉娅娟娣艳红棠莉悦驰婉婷嘉洁彩红媛雪美芳莉轩燕齐昕妍倡文紊文子婧爱琴维娟思娜振文鸾瑶玲丽旦娅苏娟羽洁秋艳建颖泓茹富霞倩成诗茹承文雪芬妍玲晓霞依婷瑾琳永琴翰颖婉燕景文赛玉晓玉晋玉幸瑶歆琳曾燕国政彩娟三婷雪婧义文雪萍津文琳秀燕明纯洁素莉艳玲";
    private static String girl_4 = "月菲薇卿梦松菱婕菱悦芬瑛月嫣绮彦海蓝晓菊娜萍凤柔芷嫣晓槐语凝睿玫语迎曼君睿璇珊嘉语蓉霞樱静绿芬邑瑜柔雪妍华凝馨凌娅冰水绿婕冰桐江嘉柔凝菡芳杏颖嫦薇婕梦玫语南夏君嘉婉芬嫣晓嫦曦月芷馨花秀萍映睿蕊敏君娟菡素妍菡旋紫蓝素曦绮曼兰邑嫣瑾菡曼碧洁兰妍晓瑛娟瑛韵雪蓉绮彩曦素君素嫣嘉娅梦英娅曦绮思鸣璇睿馥水碧翠柳婉翾冰旋素菲芬华静儿萍姿洁明芬菡月菱睿幽语月月薇碧思菱秋燕枝海亦艳艺露桐月樱薇婉梦颖曼竹若曦霎华艳青霎菲绿梅华旋蓝娟晓菡碧音晓裳纹馨芙菱霎嘉芸菱颖依素瑜珠妙鸣婉芳妙若妍语颖花萍蓉佩绮敏睿月纹妙彦莺夏妍菡觅嫣怡梦蓉海曦华卿晓绮静嘉兰妙嘉映素语珊嫣梦柏珊菱嘉思倚曦芳辰绮海芙曦霎碧曼邑晓沛嘉洁芙绮芸华珊菲绮音丽曼怡樱琼凌恬曦瑛娜馨菡娜嫣悦菱卿婉晓瑜菱梅娜邑婉露语婕嘉婧翠婉嘉芍霎佳菱瑾娜瑛尔柏芬妙碧姿翠柔芳华菲韵婉妍悦曦丹丽蓝纹凝昕洁妮薇悦绮梅桐嫦婧羽蓝芸静睿菡蓉蓝娜嫣敏彩冰如敏梦香韵梅艳妮嘉蕊睿姿菲雪";

    // 循环次数
    private static final int loopCount = 10000000;


    public static void main(String[] args) {

        String allGirlLetter = handleNameStr();
        System.out.println(allGirlLetter);
        System.out.println(allGirlLetter.length());


/*        Set nameSet = Sets.newHashSet();
        for (int i = 0; i < loopCount; i++) {
            nameSet.add(getChineseName(firstName, allGirlLetter));
        }

        System.out.println(nameSet.size());
        nameFileter(nameSet, "翠张志忠建德幽石彩玉", "方芳莲莹君珍娟环玲");

        System.out.println(nameSet.size());
        nameSet.forEach(System.out::println);*/
    }

    /**
     * 处理字符串（组合、去重）
     *
     * @return String
     */
    private static String handleNameStr() {
        Set<String> nameStrSet = Sets.newHashSet();

        String allGirlLetter = girl + girl_2 + girl_3 + girl_4;
        for (int i = 0; i < allGirlLetter.length(); i++) {
            char temp = allGirlLetter.charAt(i);
            nameStrSet.add(String.valueOf(temp));
        }

        return Joiner.on("").join(nameStrSet);
    }


    /**
     * 获取组合
     *
     * @param firstName 第一个字
     * @param nameStr   后续的字符串
     * @return String
     */
    public static String getChineseName(String firstName, String nameStr) {
        // 第一个字
        int index = getNum(0, firstName.length());
        String first = firstName.substring(index, index + 1);

        // 第二个字
        int length = nameStr.length();
        index = getNum(0, length);
        String second = nameStr.substring(index, index + 1);

        // 第三个字
        index = getNum(0, length);
        String third = nameStr.substring(index, index + 1);
        return first + second + third;
    }

    /**
     * 生成指定范围内的随机数
     *
     * @param start 开始
     * @param end   结束
     * @return int
     */
    private static int getNum(int start, int end) {
        return RandomUtils.nextInt(start, end);
    }

    /**
     * 过滤指定位置的字符
     *
     * @param nameSet      字符串集合
     * @param secondFilter 过滤第二个包含的字符串
     * @param thirdFilter  过滤第三个包含的字符串
     */
    public static void nameFileter(Set<String> nameSet, String secondFilter, String thirdFilter) {
        filterNameAt(nameSet, 2, secondFilter);

        filterNameAt(nameSet, 3, thirdFilter);
    }


    /**
     * 过滤掉包含给定的字的内容
     *
     * @param nameSet     要过滤的字符串集合
     * @param filterIndex 过滤第几个字
     * @param filterStr   过滤关键字
     */
    private static void filterNameAt(Set<String> nameSet, int filterIndex, String filterStr) {
        // 过滤掉的
        List<String> filterNameList = Lists.newArrayList();

        // 要过滤掉的字
        List<Character> filterStrList = Lists.newArrayList();
        for (int i = 0; i < filterStr.length(); i++) {
            filterStrList.add(filterStr.charAt(i));
        }

        Iterator<String> nameIterator = nameSet.iterator();
        while (nameIterator.hasNext()) {
            String name = nameIterator.next();

            if (filterStrList.contains(name.charAt(filterIndex - 1))) {
                filterNameList.add(name);
                nameIterator.remove();
            }
        }

        System.out.println("过滤掉的有=" + filterNameList.size());
    }

}
