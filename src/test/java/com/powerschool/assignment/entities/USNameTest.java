package com.powerschool.assignment.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class USNameTest {

	@Test
    public void testSetter_setsProperly() throws NoSuchFieldException, IllegalAccessException {
        //given
        final USName USName = new USName();

        //when
        USName.setFirstName("prajwal");

        //then
        final Field field = USName.getClass().getDeclaredField("firstName");
        field.setAccessible(true);
        assertEquals("prajwal",field.get(USName));
    }

    @Test
    public void testGetter_getsValue() throws NoSuchFieldException, IllegalAccessException {
        //given
        final USName pojo = new USName();
        final Field field = pojo.getClass().getDeclaredField("firstName");
        field.setAccessible(true);
        field.set(pojo, "prajwal");

        //when
        final String result = pojo.getFirstName();

        //then
        assertEquals("prajwal",result);
    }
    
 
}
