package utilities;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SeleniumMethods {

	public String getDateAndTime(String timeFormat){
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	public String getDayName() {
		String dayNames[] = new DateFormatSymbols().getWeekdays();
		Calendar date2 = Calendar.getInstance();
		return dayNames[date2.get(Calendar.DAY_OF_WEEK)];
	}

	/**
	 * this method use to get today's date in given format
	 * @param format
	 * @return string
	 */
	public String getCurrentDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}


	/**
	 * this method use to get future date in given format
	 * @param days
	 * @param format
	 * @return string
	 */
	public String getDateAfterDays(int days, String format) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days); // +days
		DateFormat dateFormat = new SimpleDateFormat(format);		
		return dateFormat.format(cal.getTime());
	}

	/**
	 * this method use to get current working directory path
	 * @return string
	 */
	public String getWorkingDirectoryPath() {
		String workdingDirectoryPath = System.getProperty("user.dir");
		return workdingDirectoryPath;
	}

	/**
	 * this method use to get random alphanumeric string of given length
	 * @param length in int
	 * @return string
	 */
	public String randomStringGenerator(int len){
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}

	/**
	 * this method use to get random alphabetics string of given length
	 * @param length in int
	 * @return string
	 */
	public String getStringOnly(int len){
		final String AB = "abcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}

	/**
	 * this method use to get random numeric string of given length
	 * @param string
	 * @return int
	 */
	public int getOnlyNumber(String str) {
		int numberOnly=0;
		try {
			numberOnly= Integer.parseInt(str.replaceAll("[^0-9]", ""));
			return numberOnly;
		} catch (NumberFormatException e) {
			return numberOnly;
		}

	}

	/**
	 * this method use to get random email
	 * @return string
	 */
	public String randomEmailGenerator(){
		String randomEmail = "qatest" +getCurrentDate("ddmmyyhhmmss")+ "@gmail.com";
		return randomEmail;
	}

}
