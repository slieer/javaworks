package org.slieer.commons.BeanUtils.userguide;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CalendarConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.ClassConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FileConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.junit.jupiter.api.Test;
import org.slieer.commons.BeanUtils.userguide.bean.Address;
import org.slieer.commons.BeanUtils.userguide.bean.StudentPO;
import org.slieer.commons.BeanUtils.userguide.bean.Student;

public class BeanTest {
	@Test
	public void toPO() throws IllegalAccessException, InvocationTargetException{
		Student std = new Student();
		std.setName("org");
		std.setAge(1);
		std.setBirth(new Date());
		std.setDefaultAddr(Address.getDefaultAddr());
		std.setAddrList(Arrays.asList(Address.getDefaultAddr()));
		
		StudentPO po = new StudentPO();
		BeanUtils.copyProperties(po, std);
		
		System.out.println(po);
	}
	
	
	{
		// ConvertUtils.register(ArrayConverter(), A
		ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
		ConvertUtils.register(new BigIntegerConverter(), BigInteger.class);
		ConvertUtils.register(new BooleanConverter(), Boolean.class);
		ConvertUtils.register(new ByteConverter(), Byte.class);
		ConvertUtils.register(new CalendarConverter(), Calendar.class);
		ConvertUtils.register(new CharacterConverter(), Character.class);
		ConvertUtils.register(new ClassConverter(), Class.class);
		ConvertUtils.register(new DateConverter(), Date.class);

		// * <li><code>java.sql.Date</code></li>
		// * <li><code>java.sql.Time</code></li>
		// * <li><code>java.sql.Timestamp</code></li>
		ConvertUtils.register(new DateTimeConverter() {

			@Override
			protected Class<?> getDefaultType() {
				return null;
			}
		}, java.sql.Date.class);

		ConvertUtils.register(new DoubleConverter(), Double.class);
		ConvertUtils.register(new FileConverter(), File.class);
		ConvertUtils.register(new FloatConverter(), Float.class);
		ConvertUtils.register(new IntegerConverter(), Integer.class);
		ConvertUtils.register(new LongConverter(), Long.class);
		// ConvertUtils.register(new NumberConverter(), Number.class);
		ConvertUtils.register(new ShortConverter(), Short.class);

	}

}