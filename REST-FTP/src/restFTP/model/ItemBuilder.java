package restFTP.model;

import org.apache.commons.net.ftp.FTPFile;

public class ItemBuilder {

	public String buildList(final String canonicalPath, final FTPFile[] files) {

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

	public static String humanReadableByteCount(final long bytes,
			final boolean si) {
		final int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		final int exp = (int) (Math.log(bytes) / Math.log(unit));
		final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	private String buildItem(final String canonicalPath, final FTPFile file) {
		final String lien = "http://localhost:8080/rest/api/ftp/file/";
		String itemPath = file.getName();

		if (file.isDirectory()) {
			itemPath += "/";
		}
		final StringBuilder builder = new StringBuilder();

		final String icon = (file.isDirectory()) ? "folder.gif" : "text.gif";
		if (file.isDirectory()) {
			builder.append("<img src=\"/static/" + icon + "\" alt=\"[DIR]\">");
			builder.append(" <a href=\"" + itemPath + "\">" + file.getName()
					+ "</a> ");
		} else {
			builder.append("<img src=\"/static/" + icon + "\" alt=\"[TXT]\">");
			builder.append(" <a href=" + lien + canonicalPath + itemPath + ">"
					+ file.getName() + "</a> ");
		}

		final String[] element = file.toString().split("  ");

		for (int i = 0; i < 24 - file.getName().length(); i++) {
			builder.append(" ");
		}
		builder.append(element[5] + " " + element[6]);
		builder.append(" " + humanReadableByteCount(file.getSize(), true));
		return builder.toString();
	}
}