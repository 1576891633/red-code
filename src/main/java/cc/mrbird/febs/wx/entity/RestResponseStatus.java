package cc.mrbird.febs.wx.entity;

/**
 * describe: 响应状态码静态常量封装
 * @author <a href="sam@jerry.com">Yang Xiang</a>
 *         2017/7/17 17:46
 * @since 0.1.0
 */

public interface RestResponseStatus {

	/** 请求处理成功 */
	public static final int OK = 200;

	/** 客户端输入有误 */
	public static final int BAD_REQUEST = 40000;

	/** 表示用户没有权限（令牌、用户名、密码错误） */
	public static final int UNAUTHORIZED = 40001;

	/** 表示用户得到授权（与401错误相对），但是访问是被禁止的 */
	public static final int FORBIDDEN = 40003;

	/** 用户发出的请求针对的是不存在的记录，服务器没有进行操作 */
	public static final int NOT_FOUND = 40004;

	/** 用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。 */
	public static final int NOT_ACCEPTABLE = 40006;

	/** 用户请求的资源被永久删除，且不会再得到的。 */
	public static final int GONE = 40010;

	/** 服务器发生错误，用户将无法判断发出的请求是否成功 */
	public static final int INTERNAL_SERVER_ERROR = 50000;
	
	/** 其他错误 */
	public static final int OTHER_ERROR = 50001;
	
	/** 未登录 */
	public static final int NOT_LOGIN=100000;
	
	/** 授权过期 */
	public static final int AUTH_EXPIRES=100001;
	
	/** 用户状态异常 */
	public static final int UNEXPECTED_STATUS=100002;

	/** 未查询到数据 */
	public static final int NOT_DATA=100003;

	/** 其他地方登录了 */
	public static final int OTHER_AREA_LOGIN=100005;

	/** 账号审核中 */
	public static final int ACCOUNT_AUDIT=100006;
}
