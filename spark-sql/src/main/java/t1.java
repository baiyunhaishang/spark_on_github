import org.junit.Test;

import java.io.File;

public class t1 {
    @Test
    public void test(){
        File file = new File("X:\\Users\\SwordArtOnline\\Desktop\\spider_test\\6\\7\\8.txt");
        if (!file.exists()){
            boolean b = file.mkdirs();
            System.out.println(b);
        }
        System.out.println(file.length());
        System.out.println(file.getParent());

    }
}
