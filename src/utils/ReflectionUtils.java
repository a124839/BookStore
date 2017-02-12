package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**����utils���� ����
 * �ṩ����˽�б�������ȡ��������class ����ȡ�����е�Ԫ�����Ե�utils����
 * @author k570
 *
 */
public class ReflectionUtils {
	/**ͨ�����䣬��ö���Classʱ�����ĸ���ķ��Ͳ���������
	 * �磺public EmployeeDao extends BaseDao<Employee,String>
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
	
	/**ͨ�����䣬���Class�����е������ĸ���ķ��Ͳ�������
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> Class<T> getSuperGenericType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}
	
	/**ѭ������ת�ͣ���ȡ����ĵ�DeclaredMethod
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
				//method ���ڵ�ǰ�ඨ�壬��������ת��
			}
		}
		return null;
	}
	
	/**ʹfield��Ϊ�ɷ���
	 * @param field
	 */
	public static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}
	
	
	/**
	 * ֱ�ӵ��ö��󷽷����������η���private , protected��
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
			System.out.println("�������׳����쳣����");
		}
		return null;
	}
	
	/**���ö�������ֵ������private��protected ���η�Ҳ������ setter
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
			System.out.println("�����ܳ��ֵ��쳣.��");
		}
		
		
	}

	/**ѭ������ת�ͣ���ȡ����� DeclaredMethod
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static Field getDeclaredField(Object object, String fieldName) {
		
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()){
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				//field ���ڵ�ǰ�ඨ�壬��������ת��
			}
			
		}
		return null;
	}
	
	/**��ȡ��������ֵ������private��protected ���η�Ҳ������ setter
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
			System.out.println("�������׳����쳣");
		}
		return result;
	}
}
