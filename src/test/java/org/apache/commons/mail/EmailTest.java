package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	
	private static final String[] TEST_EMAILS = { "ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrstuvw@xyz.com.bd"};
	private static final String[] testemailnull = {};
	public static final String MAIL_HOST = EmailConstants.MAIL_HOST;
	
	//Concrete Email class for testing
	private EmailConcrete email;
	
	//Setup and Teardown methods for all test cases!!
	@Before
	public void setupEmailTest() throws Exception{
		
		email = new EmailConcrete();
		
	}
	
	@After
	public void teardownEmailTest() throws Exception{
		
		
		
	}
	
	//Testing addBcc(string email)
	@Test
	public void testAddBcc() throws Exception{
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3, email.getBccAddresses().size());
	}
	//testing null add Bcc
	@Test(expected = EmailException.class) //test case passes if exception is thrown, fails if not (using expected instad of assert)
	public void testnullAddBcc() throws Exception{
		
		email.addBcc(testemailnull);
		
		
	}
	
	//testing addCC(string ...email)
	@Test
	public void testAddCC()throws Exception{
		
		email.addCc(TEST_EMAILS);
		
		assertEquals(3, email.getCcAddresses().size());
	}
	
	//testing addCC(string email) ONLY 1 EMAIL (NOT LIST LIKE PREV TEST)
	@Test
	public void test1AddCC() throws Exception{
		
		String addr = "batata@potato.com";
		email.addCc(addr);
		
		//first check if size of cc list is 1 (which it should be since we added one 1 Cc
		//if this test passes, go to next assert to see if address is inside the list
		assertEquals(1, email.getCcAddresses().size());
		
		//if address is in list, pass test!
		assertEquals(addr,email.getCcAddresses().get(0).toString());
		
	}
	
	//testing addCC(string email) with null value
	@Test(expected = EmailException.class)//test case passes if exception is thrown, fails if not
	public void testnullAddCC()throws Exception{
		
		email.addCc(testemailnull);
		
	}
	
	//testing addHeader(string, string)	
	@Test
	public void testaddHeader()throws Exception{
		
		email.addHeader("BOB", "3"); //name and value
		Map<String, String> headersTest = email.getHeaders();
		
		//test to see if key was added to header
		assertTrue(headersTest.containsKey("BOB"));
		
		//test to see if value is in the map
		assertEquals("3", headersTest.get("BOB"));
	}
	
	//testing addHeader with null name 
	@Test(expected = IllegalArgumentException.class)//test case passes if exception is thrown, fails if not
	public void testNullNameHeader()throws Exception{
		
		email.addHeader("", "1");
	}
	//testing addHeader with null value
	@Test(expected = IllegalArgumentException.class)//test case passes if exception is thrown, fails if not
	public void testNullValueHeader()throws Exception{
		
		email.addHeader("sara", "");
	}
	
	//testing addReplyTo(String email, String name)
	@Test
	public void testReplyto() throws Exception{
		
		email.addReplyTo("ab@cd.com","sara"); //adding sample email and name
		
		List <InternetAddress> replylist = email.getReplyList();
		
		//passes if replylist size ==1
		assertEquals(1, replylist.size());
		}
	
	//testing buildMimeMessage() (happy case)
	@Test
	public void testbuildMimeMessage() throws Exception{
		
		
			email.setHostName("mycoolhostname");
			email.setSmtpPort(1234);
			email.setFrom("batata@umich.edu", "batata");
			email.addCc(TEST_EMAILS);
			email.addReplyTo("ab@cd.com", "sara");
			email.addHeader("Billy", "3");
			email.setSubject("Important: Potatos");
			email.setCharset("KOI8_R");
			email.setContent("Test Content", "text/plain");
		
			//List <InternetAddress> ToList = {"ab@cd.com"};
			email.buildMimeMessage();
			//email.buildMimeMessage();
		
		
		
		
		}
	
	//test to see if method accurately throws exception when attempting to build multiple mime msg
	@Test(expected = IllegalStateException.class)//using "expected" instead of assert statement!
	public void testMultipleMime() throws Exception{
		
		email.setHostName("mycoolhostname");
		email.setSmtpPort(1234);
		email.setFrom("batata@umich.edu", "batata");
		email.addCc(TEST_EMAILS);
		email.addReplyTo("ab@cd.com", "sara");
		email.addHeader("Billy", "3");
		email.setSubject("Important: Potatos");
		email.setCharset("KOI8_R");
		email.setContent("Test Content", "text/plain");
	
		email.buildMimeMessage();
		email.buildMimeMessage(); //this should cause the Illegal State Exception
		
	}
	
	
	//testing with null content
	@Test
	public void testcontentBuildMime() throws Exception{ 
		
		email.setHostName("mycoolhostname");
		email.setSmtpPort(1234);
		email.setFrom("batata@umich.edu", "batata");
		email.addCc(TEST_EMAILS);
		email.addBcc(TEST_EMAILS);
		email.setSubject("Important: Potatos");
		email.setCharset("KOI8_R");
		
		
		email.buildMimeMessage();
		//passes if getContentType is null! (also no exceptions should be raised)
		assertEquals(null, email.getContentType());
	}
	
	//testing with null emailbody
	@Test
	public void testemailbdyBuildMime() throws Exception{ 
		
		email.setHostName("mycoolhostname");
		email.setSmtpPort(1234);
		email.setFrom("batata@umich.edu", "batata");
		email.addCc(TEST_EMAILS);
		email.setSubject("Important: Potatos");
		email.setCharset("KOI8_R");
		
		
		//create mime multipart to test email body
		MimeMultipart multipart = new MimeMultipart();
		email.setContent(multipart);
		
		//passes if no exception is raised
		email.buildMimeMessage();
		
	}
	
	//testing with no from Address (FromAddress is null)
	@Test(expected = EmailException.class)//using "expected" instead of assert statement!!
	public void testaddressBuildMime() throws Exception{ 
		
		email.setHostName("mycoolhostname");
		email.setSmtpPort(1234);
		//email.setFrom("batata@umich.edu", "batata");
		email.addCc(TEST_EMAILS);
		email.setSubject("Important: Potatos");
		email.setCharset("KOI8_R");
		email.setContent("Test Content", "text/plain");
		
		//test passes if it raises EmailException (bcs of no From Address)
		email.buildMimeMessage();
	}
	
	//testing set/get HostName
	@Test
	public void testHostName() throws Exception{
		
		
		email.setHostName("epichostname");
		
		//compare hostnames, pass test if equal
		assertEquals("epichostname", email.getHostName());
		
	}
	
	
	//test get hostname with no host name and no session (should return null)
	@Test
	public void testNoSessionNoHost() throws Exception{
		
		//pass test if getHostName returns null
		assertNull(email.getHostName());
	}
	
	
	@Test
	public void testNoHostName() throws Exception{
		
		Properties props = new Properties();
		String hostName = "epiccoolhostname";
		
		//using MAIL_HOST constant from import
		props.setProperty(MAIL_HOST, hostName);
		Session session = Session.getInstance(props);
		
		email.setMailSession(session);
		
		//compare hostnames, if equal pass test
		assertEquals(hostName, email.getHostName());
		
	}
	
	//testing getMail session
	//start testing with null session and NO hostname
	@Test (expected = EmailException.class ) //using "expected" instead of assert statement!!
	public void testgetMailnullSession() throws Exception{
		
		//inputting no session, should raise exception to pass test!
		email.getMailSession();
		
		
	}
	
	//testing with not null session
	@Test
	public void testgetMailSession() throws Exception{
		
		Properties props = new Properties();
		//String sessionHostName = "SessionEpicHostName";
		
		Session session = Session.getInstance(props);
		
		email.setMailSession(session);
		
		//compare both sessions, if equal pass test
		assertEquals(session, email.getMailSession());
		
	}
	
	@Test
	public void testGetSentDate() throws Exception{
		Date date = new Date();
		date.setTime(10);
		
		email.setSentDate(date);
		
		assertEquals(date, email.getSentDate());
		
	}
	
	//testing getSocketConnectionTimeout() 
	@Test
	public void TestgetSocketConnectionTimeout() throws Exception{
		
		//setting timeout to random integer
		int socketconnTimeout = 29;
		
		email.setSocketConnectionTimeout(socketconnTimeout);
		
		//comparing both timeout, if equal pass test
		assertEquals(socketconnTimeout, email.getSocketConnectionTimeout());
		
		
	}
	
	//testing setFrom(string email)
	@Test
	public void TestsetFrom() throws Exception{
		
		String fromaddr = "issa@pizza.com";
		email.setFrom(fromaddr);
		
		//comparing the 2 addresses, if equal pass test
		assertEquals(fromaddr, email.getFromAddress().toString());
		
	}
}



