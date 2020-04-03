
public class JsonGetArray {

	public static int nextIndexOf(String str, int fromIndex, char ch) throws Exception {
		int ret = -1;
		if (ch == ' ' || ch == '\r' || ch == '\n' || ch == '\t') {
			ret = str.indexOf(ch, fromIndex);
		} else {
			char thisChar;
			int i = fromIndex;
			while (i < str.length()) {
				thisChar = str.charAt(i);
				switch(thisChar) {
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					i++;
					continue;
				default:
					if (thisChar == ch) {
						ret = i;
					} else {
						throw new Exception("unexpected char : " + thisChar);
					}
					break;
				}
				if (ret > -1) break;
			}
		}
		return ret;
	}
	
	public static String[] getArray(String json, String key) throws Exception {
		String[] ret = null;
		if (json != null && key != null && !"".equals(json) && !"".equals(key)) {
			int keyLength = key.length();
			int startIndex = json.indexOf("\"" + key + "\"");
			if (startIndex > -1) {
				int colonIndex = nextIndexOf(json, startIndex + keyLength + 2, ':');
				int arrStart = nextIndexOf(json, colonIndex + 1, '[');
				int arrEnd = json.indexOf(']', arrStart + 1);
				if (arrEnd > -1) {
					String subStr = json.substring(arrStart + 1, arrEnd);
					String[] list = subStr.split(",");
					if (list.length > 0) {
						ret = new String[list.length];
						int leftquot, rightquot;
						for (int i = 0; i < list.length; i++) {
							leftquot = list[i].indexOf('"');
							if (leftquot > -1) {
								rightquot = list[i].indexOf('"', leftquot + 1);
								if (rightquot > -1) {
									ret[i] = list[i].substring(leftquot + 1, rightquot);
									continue;
								}
							}
							throw new Exception("Incomplete quotation marks");
						}
					}
				}
			}
		}
		return ret;
	}
 
	public static void main(String[] args) {
		String str = "{\"name\":\"张三\", \"age\":\"14\",\"friends\":[\"小明\", \"小红\"]}";
		String key = "friends";
		try {
			String[] arr = getArray(str, key);
			for (int i = 0; i < arr.length; i++) {
				System.out.println(arr[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
