import java.lang.reflect.Field;

public class JavaReferenceTest {

    public static void main(String[] args){
//        Integer a = 1;
//        Integer b = 2;
//        exchange(a ,b);
//        System.out.println("a="+a);
//        System.out.println("b="+b);

        String s = "第三方服务";
        System.out.println(reverse(s));
    }

    static void exchange(Integer a, Integer b){
        try {
            Field f = a.getClass().getDeclaredField("value");
            f.setAccessible(true);
            int temp = a ;
            f.set(a , b);
            f.set(b , new Integer(temp));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public static String reverse(String originStr) {
        if(originStr == null || originStr.length() <= 1)
            return originStr;
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }
}


