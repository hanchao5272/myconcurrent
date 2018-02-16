package pers.hanchao.concurrent; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Hello Tester. 
* 
* @author <Authors name> 
* @since <pre>$tody</pre> 
* @version 1.0 
*/ 
public class HelloTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getOne() 
* 
*/ 
@Test
public void testGetOne() throws Exception {

    Assert.assertEquals(new Integer(1),Hello.getOne());
} 


} 
