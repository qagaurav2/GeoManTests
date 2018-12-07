package utilities;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class ReadingEmails {

	Properties properties = null;
	private Session session = null;
	private Store store = null;
	private Folder inbox = null;
	String userName = "testingautomation6@gmail.com";// change accordingly
	String password = "testing@123";// change accordingly

	public ReadingEmails() {

	}

	public Store authenticate() throws MessagingException {
		properties = new Properties();
		properties.setProperty("mail.host", "imap.gmail.com");
		properties.setProperty("mail.port", "993");
		properties.setProperty("mail.transport.protocol", "imaps");
		session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		store = session.getStore("imaps");
		store.connect();
		return store;
	}

	public boolean hasAttachments(String subject) throws Exception {
		boolean flag = false;
		store = authenticate();
		for (int j = 0; j < 10; j++) {
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			if(messages.length==0) {
				Thread.sleep(10000);
			}
			else {
				System.out.println("Number of mails = " + messages.length);
				for (int i = 0; i < messages.length; i++) { 
					Message message = messages[i]; 
					Address[] from = message.getFrom(); 
					System.out.println("Date : " + message.getSentDate()); 
					System.out.println("From : " + from[0]);
					System.out.println("Subject: " + message.getSubject());
					System.out.println("Content :"+message.getContent()); 
					if(message.getSubject().equals(subject)) {
						flag = true;
						message.setFlag(Flag.SEEN, true);
						System.out.println("--------------------------------");
						if (message.isMimeType("multipart/mixed")) {
							Multipart mp = (Multipart)message.getContent();
							if (mp.getCount() > 1)
								return true;
						}
						if(flag==true) {
							break;
						}
					}

				}
			}

			if(flag==true) {
				inbox.close(true);
				store.close(); 
				break;
			}

		}
		return flag;
	}

	public String getMailBody(String subject) throws Exception {
		boolean flag = false;
		store = authenticate();
		for (int j = 0; j < 10; j++) {
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			if(messages.length==0) {
				Thread.sleep(10000);
			}
			else {
				System.out.println("Number of mails = " + messages.length);
				for (int i = 0; i < messages.length; i++) { 
					Message message = messages[i]; 
					Address[] from = message.getFrom(); 
					System.out.println("Date : " + message.getSentDate()); 
					System.out.println("From : " + from[0]);
					System.out.println("Subject: " + message.getSubject());
					System.out.println("Content :"+message.getContent()); 
					if(message.getSubject().equals(subject)) {
						flag = true;
						message.setFlag(Flag.SEEN, true);
						System.out.println("--------------------------------");
						return message.getContent().toString();
					}
					if(flag==true) {
						break;
					}
				}

			}
			if(flag==true) {
				inbox.close(true);
				store.close(); 
				break;
			}
		}
		throw new Exception("no email found");
	}

	public String getMailBody(String subject,String mailUniqueContent) throws Exception {
		boolean flag = false;
		store = authenticate();
		for (int j = 0; j < 10; j++) {
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			if(messages.length==0) {
				Thread.sleep(10000);
			}
			else {
				System.out.println("Number of mails = " + messages.length);
				for (int i = 0; i < messages.length; i++) { 
					Message message = messages[i]; 
					Address[] from = message.getFrom(); 
					System.out.println("Date : " + message.getSentDate()); 
					System.out.println("From : " + from[0]);
					System.out.println("Subject: " + message.getSubject());
					System.out.println("Content :"+message.getContent()); 
					if(message.getSubject().equals(subject)) {
						Object content = message.getContent();
						if(String.valueOf(content).contains(mailUniqueContent)) {
							flag = true;
							message.setFlag(Flag.SEEN, true);
							System.out.println("--------------------------------");
							return message.getContent().toString();
						}
					}
					if(flag==true) {
						break;
					}
				}

			}
			if(flag==true) {
				inbox.close(true);
				store.close(); 
				break;
			}
		}
		throw new Exception("no email found");
	}
}


