package desionmode.aop;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 97952 on 2017/11/28.
 */

public class AnnotateUtils {
    public static void injectViews(Activity activity){
        Class<? extends Activity> object=activity.getClass();//获取activity的Class
        Field[] fields=object.getDeclaredFields();
        for(Field field:fields){//遍历所有的字段
            //获取字段的注解，如果没有ViewInject注解，则返回null
            ViewInject viewInject=field.getAnnotation(ViewInject.class);
            if(viewInject!=null){
                int viewId=viewInject.value();//获取字段注解的参数，这就是我们传进去控件的ID
                if(viewId!=-1){
                    try{
                        //获取类中的findViewById方法，参数为int
                        Method method=object.getMethod("findViewById",int.class);
                        //执行该方法，返回一个object类型的view实例
                        Object resView=method.invoke(activity,viewId);
                        field.setAccessible(true);
                        //把字段的值设置为该view的实例
                        field.set(activity,resView);
                    }catch(NoSuchMethodException e){

                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
