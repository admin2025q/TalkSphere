export class Constants {

  // API 相关  
  static readonly API_URL: string = '/admin';
  static readonly LOGIN_URL: string = `${this.API_URL}/login`;
  static readonly CAPTCHA_URL: string = `${this.API_URL}/captcha`;
  static readonly USER_INFO_URL: string = `${this.API_URL}/user/info`;
  static readonly LOGOUT_URL: string = `${this.API_URL}/logout`;

  // 请求头
  static readonly HEADER_CK: string = '_ck'; // Cookie 请求头名称
  // 时间相关
  static readonly TIMESTAMP_KEY: string = 'timestamp'; // 时间戳参数键名

  static readonly TRACE_ID_HEADER: string = "X-Trace-Id";

}