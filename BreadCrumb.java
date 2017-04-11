import java.util.*;

public class BreadCrumb {

    public static String generate_bc(String url, String separator) {
      // rules
      // if not root or home and longer than 30 chars turn it into an acronym
      // acronym removes dashes and capitalizes
      // split the url into sections based off of slash
      // ignore anything following a pound or question mark
      // convert each section into all caps
      // wrap each section in an anchor tag with a link leading to the sub domain
      // wrap home in anchor leading to /
      // wrap last element in a span and mark with class active
      // if last element is "index" go up one level in the


      // 1) extract the sections
      // 2) Replace top level section with HOME
      // 3) if last section has pound or question mark remove it
      // 4) If last section has index remove it
      // 5) Capitalize every section
      // 6) Acronymize long sections
      // 7) home's anchor tag is /
      // 8) Following anchors are previous anchor tag + original name of section
      // 8) Wrap all but last section in anchor tag
      // 10) Wrap last section in span with active class
      // 11) join string with separator
      // 12) return that shit

      url = url.split("//").length > 1 ? url.split("//")[1] : url;
      String[] extractedSections = url.split("/");
      int sectionCount = extractedSections.length;
      String[] sectionNames = new String[sectionCount];
      String[] sectionUrls = new String[sectionCount];

      sectionNames[0] = "HOME";
      sectionUrls[0] = "/";

      if (extractedSections[sectionCount - 1].indexOf("index.") > -1) {
        sectionCount--;
      } else {
        extractedSections[sectionCount - 1] = stripLast(extractedSections[sectionCount - 1]);
        if (extractedSections[sectionCount - 1].length() == 0) {
          sectionCount--;
        }
      }


      for (int i = 1; i < sectionCount - 1; i++) {
        sectionUrls[i] = sectionUrls[i - 1] + extractedSections[i] + "/";
      }

      for (int i = 1; i < sectionCount; i++) {
        sectionNames[i] = formatSectionName(extractedSections[i]);
      }

      String[] result = new String[sectionCount];
      for (int i = 0; i < sectionCount; i++) {
        if (i == sectionCount - 1) {
          result[i] = "<span class=\"active\">" + sectionNames[i] + "</span>";
        } else {
          result[i] = "<a href=\"" + sectionUrls[i] + "\">" + sectionNames[i] + "</a>";
        }
      }

      return String.join(separator, result);
    }

    public static String formatSectionName(String sectionName) {
        sectionName = sectionName.toUpperCase();
        sectionName = shorten(sectionName);
        sectionName = sectionName.replaceAll("-", " ");

        return sectionName;
    }

    public static String shorten(String longString) {
      String[] ignoreWords = {"the","of","in","from","by","with","and", "or", "for", "to", "at", "a"};
      String result = "";

      if (longString.length() > 30) {
        String[] hyphenWords = longString.split("-");
        for (String hyphenWord : hyphenWords) {
          Boolean containsIgnoreWord = false;

          for (String ignoreWord : ignoreWords) {
            if (hyphenWord.toLowerCase().equals(ignoreWord)) {
              containsIgnoreWord = true;
              break;
            }
          }

          if (containsIgnoreWord) {
            continue;
          } else {
            result += hyphenWord.toUpperCase().charAt(0) + "";
          }
        }
      }

      return result.length() > 0 ? result : longString;
    }

    public static String stripLast(String lastSection) {
      lastSection = stripAfterSymbol(lastSection, "?");
      lastSection = stripAfterSymbol(lastSection, "#");
      lastSection = stripAfterSymbol(lastSection, ".");

      return lastSection;
    }

    public static String stripAfterSymbol(String section, String symbol) {
      int markIndex = section.lastIndexOf(symbol);

      if (markIndex > -1) {
        section = section.substring(0, markIndex);
      }

      return section;
    }
}