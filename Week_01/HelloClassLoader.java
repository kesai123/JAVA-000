import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {
    private byte[] content;

    public static void main(String[] args){
        try {
            HelloClassLoader loader = new HelloClassLoader();
            Class<?> h = loader.findClass("Hello");
            Object o = h.newInstance();
            Method m = h.getMethod("hello");
            m.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = "C:\\Users\\d\\IdeaProjects\\test1\\moduleClassLoader\\resource\\Hello.xlass";
        try {
            FileInputStream file = new FileInputStream(path);
            content = new byte[file.available()];
            file.read(content);
            file.close();
            for (int i=0;i<content.length;i++){
                content[i] = (byte)(255-content[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defineClass(name, content, 0, content.length);
    }
}
