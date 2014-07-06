package simple.foundation.javadoc;

/**
 * http://kiral.iteye.com/blog/1156895
 * JAVA DOC由<b>Java Tag</b>和<b>HTML标记</b>组成。
 * 
 * <br/>
 * 
 * <h3>HTML标记:</h3>
 * <ul>
 * <li>用来定义注释的格式，大多数HTML标记都支持，常用的有。</li>
 * <li>p：段落</li>
 * <li>br：换行</li>
 * <li>a：超链接</li>
 * <li>pre</li>
 * <li>b：粗体，如 <b>粗体</b> <u>下划线</u></li>
 * <li>code: 代码的标记，仅样式上和不加标记有所区分，如<code>String</code></li>
 * <li>img：支持图片标记。<img src=
 * "http://img.alibaba.com/images/cms/upload/banner/partner_bigbuyer/70x25_goodyear.jpg"
 * ></li>
 * </ul>
 * 
 * <h3>Java Tag:</h3>
 * <ul>
 * <li>Java Tag用来定义注释的属性，以@开头。</li>
 * <li>@link：链接到其他类，如{@link JavaDocRuntimeException}</li>
 * <li>@see：为了更加了解当前类，可以去看的其他类或者方法。see标记可以链接到类，方法，URL地址等。</li>
 * <li>@value：链接到变量。1.4以后的tag,如 {@value #SCRIPT_START}
 * 按住ctrl+鼠标点击，可链接到SCRIPT_START变量</li>
 * <li>@code：在code里可以使用特殊字符尖括号等，1.5后的tag,如：
 * 
 * <pre>
 * {@code List<String> users=new ArrayList<String>;}
 * </pre>
 * 
 * </li>
 * </ul>
 * 
 * @author tengfei.fangtf
 * @version 版本 1.0
 * 
 * @see "javadoc.html"
 * @see <a href="spec.html#section">Java Spec</a>
 * @see String#equals(Object) equals
 * @see <a
 *      href="http://download.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html">JavaDoc</a>
 * 
 */
public class JavaDocDemo {

	public static final String SCRIPT_START = "<script>";

	/**
	 * 方法描述
	 * 
	 * @param doc
	 *          参数说明
	 * @return 返回值说明
	 * @throws JavaDocRuntimeException
	 *           该方法可能会抛出的异常，如：当长升文档失败时候将抛出JavaDocRuntimeException异常
	 */
	public String genJavaDoc(String doc) {
		try {

		} catch (Exception e) {
			throw new JavaDocRuntimeException("产生javaDOC失败");
		}
		return null;
	}

	/**
	 * @deprecated 表示当前方法已经不推荐使用，已由新方法取代。
	 */
	public String genJavaDoc(String doc, String type) {
		return null;

	}

	/**
	 * JAVA doc运行时异常
	 * 
	 * @author tengfei.fangtf
	 * 
	 */
	class JavaDocRuntimeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public JavaDocRuntimeException(String string) {
			// TODO 代办任务，表示当前由于时间或者技术原因不能解决的问题，可以在Eclipse的tasks视图下查看
		}
	}

}