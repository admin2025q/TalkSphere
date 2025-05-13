

export class Constants {

  // 登录相关
  private static readonly LOGIN_API_URL: string = '/login'; // 登录 API 地址
  static readonly LOGIN_URL: string = `${this.LOGIN_API_URL}/submit`;
  static readonly CAPTCHA_URL: string = `${this.LOGIN_API_URL}/captcha`;

  //用户管理模版  
  private static readonly API_URL: string = '/admin';
  static readonly USER_PAGE_LIST:string = `${this.API_URL}/user/pageListUser`
  
  static readonly USER_INFO_URL: string = `${this.API_URL}/user/info`;
  
  
  static readonly LOGOUT_URL: string = `${this.API_URL}/logout`;


  // 请求头
  static readonly HEADER_CK: string = '_ck'; // Cookie 请求头名称
  // 时间相关
  static readonly TIMESTAMP_KEY: string = 'timestamp'; // 时间戳参数键名

  static readonly TRACE_ID_HEADER: string = "X-Trace-Id";

}