public class test0 {
    public static void main(String[] args) {


        try {
            throw new RuntimeException("xxx");
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                System.out.println(stackTraceElement.getFileName());
            }
            /*for (StackTraceElement a:stackTrace) {
                System.out.println(a.getLineNumber());

            }*/
        }

    }
}
class xx{


}