package restFTP.model;

import java.text.SimpleDateFormat;

import org.apache.commons.net.ftp.FTPFile;

public class ItemBuilder {
	

	public String buildList(String canonicalPath, FTPFile[] files) {
	
		final StringBuilder menu = new StringBuilder();
		menu.append("<h1>Index of " + canonicalPath + "</h1>");
		menu.append("<pre><img src=\"/static/blank.gif\" alt=\"Icon\">");
		menu.append(" <a href=\"?C=N;O=D\">Name</a>");
		menu.append(" <a href= \"?C=M;O=A\">Last modified</a>");
		menu.append(" <a href=\"?C=S;O=A\">Size</a>");
		menu.append(" <a href=\"?C=D;O=A\">Description</a>");
		menu.append("<hr>");
		menu.append("<img src=\"/static/back.gif\" alt=\"[DIR]\"> <a href=\".. \">Parent Directory</a><br/>");

		for (final FTPFile file : files) {
			menu.append(buildItem(canonicalPath, file));
			menu.append("<br>");
		}
		menu.append("<hr></pre>");
		return menu.toString();
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	private String buildItem(String canonicalPath, FTPFile file) {
	
		String itemPath = file.getName();
		String lien = "http://localhost:8080/rest/api/ftp/file";

		if (file.isDirectory()) {
			lien = lien + "/"+ file.getName();
			itemPath += "/";
			}
		final StringBuilder builder = new StringBuilder();

		final String icon = (file.isDirectory()) ? "folder.gif" : "text.gif";
		if(file.isDirectory()){		
			builder.append("<img src=\"/static/" + icon + "\" alt=\"[DIR]\">");
			builder.append(" <a href=\"" + itemPath + "\">" + file.getName()
					+ "</a> ");
		}
		else{
			builder.append("<img src=\"/static/" + icon + "\" alt=\"[TXT]\">");
			builder.append(" <a href="+ lien + "/" +itemPath + ">" + file.getName()
				+ "</a> ");
		}

		String[] element = file.toString().split("  ");
		
		for (int i = 0; i < 24 - file.getName().length(); i++) {
			builder.append(" ");
		}
		builder.append(element[5]+" "+ element[6]);
		builder.append(" " + humanReadableByteCount(file.getSize(), true));
		return builder.toString();
	}
}