// 该文件用于处理请求的代理和 UUID 的生成
import { Constants } from "@/pgcontants/Contants";

export const request = async (url: string, options: RequestInit = {}) => {
  const uuid = crypto.randomUUID(); // 获取或生成 UUID
  const headers = {
    ...options.headers,
    [Constants.TRACE_ID_HEADER]: uuid, // 添加 UUID 到请求头
  };
  const response = await fetch(url, {
    ...options,
    headers,
  });
  return response;
};