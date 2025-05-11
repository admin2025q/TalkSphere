export class StorageUtil {
  /**
   * 存储数据到 localStorage
   * @param key 键名
   * @param value 值
   */
  static setLocal(key: string, value: any): void {
    localStorage.setItem(key, JSON.stringify(value));
  }

  /**
   * 从 localStorage 获取数据
   * @param key 键名
   * @returns 数据值
   */
  static getLocal<T>(key: string): T | null {
    const value = localStorage.getItem(key);
    return value ? JSON.parse(value) : null;
  }

  /**
   * 从 localStorage 删除数据
   * @param key 键名
   */
  static removeLocal(key: string): void {
    localStorage.removeItem(key);
  }

  /**
   * 存储数据到 sessionStorage
   * @param key 键名
   * @param value 值
   */
  static setSession(key: string, value: any): void {
    sessionStorage.setItem(key, JSON.stringify(value));
  }

  /**
   * 从 sessionStorage 获取数据
   * @param key 键名
   * @returns 数据值
   */
  static getSession<T>(key: string): T | null {
    const value = sessionStorage.getItem(key);
    return value ? JSON.parse(value) : null;
  }

  /**
   * 从 sessionStorage 删除数据
   * @param key 键名
   */
  static removeSession(key: string): void {
    sessionStorage.removeItem(key);
  }
}