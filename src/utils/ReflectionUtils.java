package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**反射utils函数 集合
 * 提供访问私有变量，获取泛型类型class ，提取集合中的元素属性等utils函数
 * @author k570
 *
 */
public class ReflectionUtils {
	/**通过反射，获得定义Class时声明的父类的泛型参数的类型
	 * 如：public EmployeeDao extends BaseDao<Employee,String>
	 * @param clazz
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index){
		Type genType = clazz.getGenericSuperclass();
		
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		
		Type [] params = ((ParameterizedType)genType).getActualTypeArguments();
		
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		
		return (Class) params[index];
	}
	
	/**通过反射，获得Class定义中的声明的父类的泛型参数类型
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> Class<T> getSuperGenericType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}
	
	/**循环向上转型，获取对象的的DeclaredMethod
	 * @param object
	 * @param methodname
	 * @param parameterTypes
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodname, Class<?>[] parameterTypes){
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				//
				return superClass.getDeclaredMethod(methodname, parameterTypes);
			} catch (NoSuchMethodException e) {
				//method 不在当前类定义，继续向上转型
			}
		}
		return null;
	}
	
	/**使field变为可访问
	 * @param field
	 */
	public static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}
	
	
	/**
	 * 直接调用对象方法，忽略修饰符（private , protected）
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Object object, String methodName, Class<?> [] parameterTypes, 
			Object [] parameters)throws InvocationTargetException {
		
		Method method  = getDeclaredMethod(object, methodName, parameterTypes);
		
		if (method == null) {
			throw new IllegalArgumentException("could not find method [" + methodName + "] on target [" 
					+ object + "]");
		}
		
		method.setAccessible(true);
		
		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			System.out.println("不可能抛出的异常。。");
		}
		return null;
	}
	
	/**设置对象属性值，忽略private，protected 修饰符也不经过 setter
	 * @param object
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) {
		Field field = getDeclaredField(object, fieldName);
		
		if (field == null) 
			throw new IllegalArgumentException("could not find field [" + fieldName + "] on target [" + object + "]");
			
		makeAccessible(field);
		
		try {
			
			field.set(object, value);
		} catch (IllegalAccessException e) {
			System.out.println("不可能出现的异常.。");
		}
		
		
	}

	/**循环向上转型，获取对象的 DeclaredMethod
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static Field getDeclaredField(Object object, String fieldName) {
		
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()){
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				//field 不在当前类定义，继续向上转型
			}
			
		}
		return null;
	}
	
	/**读取对象属性值，忽略private，protected 修饰符也不经过 setter
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		
		if (field == null) 
			throw new IllegalArgumentException("could not find field [" + fieldName + "] on target" + object + "]");
			
		makeAccessible(field);
		
		Object result = null;
		
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			System.out.println("不可能抛出的异常");
		}
		return result;
	}
}
