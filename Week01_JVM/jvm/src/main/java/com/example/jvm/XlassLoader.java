package com.example.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author kaizen
 * @date 2021/08/08
 */
public class XlassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        ClassLoader cl = new XlassLoader();
        Class<?> clazz = cl.loadClass("Hello");
        // 查看类中的方法
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(clazz.getSimpleName() + "." + method.getName());
        }
        // 初始化类
        Object instance = clazz.getDeclaredConstructor().newInstance();
        // 获取 hello 方法
        Method method = clazz.getMethod("hello");
        // 调用 hello 方法
        method.invoke(instance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(name + ".xlass");
        try {
            int length = inputStream.available();
            byte[] byteArray = new byte[length];
            inputStream.read(byteArray);
            byte[] classBytes = decode(byteArray);
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            close(inputStream);
        }
    }

    private byte[] decode(byte[] byteArray) {
        byte[] arr = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            arr[i] = (byte) (255 - byteArray[i]);
        }
        return arr;
    }

    private void close(InputStream res) {
        if (res != null) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
