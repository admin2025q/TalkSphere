import React, { useState, useEffect } from 'react';
import { request } from '@/utils/requestProxy';
import { Constants } from '@/pgcontants/Contants';
import { StorageUtil } from '@/utils/storage';

const Login: React.FC = () => {

  const [_ck, setCk] = useState<string>('');
  // 用户名/邮箱
  const [emailAndphone, setEmailAndphone] = useState<string>('');
  // 密码
  const [password, setPassword] = useState<string>('');
  // 验证码
  const [captcha, setCaptcha] = useState<string>('');
  // 验证码图片 URL
  const [captchaSrc, setCaptchaSrc] = useState<string>('');

  const [msg, setMsg] = useState<string>('');
  // 初始化验证码图片
  const fetchCaptcha = async () => {
    try {
      const response = await request(`${Constants.CAPTCHA_URL}?${Constants.TIMESTAMP_KEY}=${Date.now()}`, {
        method: 'GET'
      });
      let _ck = response.headers.get(Constants.HEADER_CK) || '';
      console.log('响应头 _ck:', _ck);
      setCk(_ck);
      // 将 _ck 存储到 localStorage
      // StorageUtil.setLocal(Constants.HEADER_CK, _ck); 
      // 将图片数据转换为 Blob
      const blob = await response.blob();
      const imageUrl = URL.createObjectURL(blob);
      // 设置图片 URL
      setCaptchaSrc(imageUrl);
    } catch (error) {
      console.error('获取验证码失败:', error);
    }
  };
  const handleCaptchaClick = () => {
    fetchCaptcha();
  };

  // 处理表单提交
  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault(); // 阻止默认提交行为
    // 在这里处理表单数据，例如发送到后端
    console.log('表单已提交', _ck);
    console.log('用户名/邮箱:', emailAndphone);
    console.log('密码:', password);
    console.log('验证码:', captcha);
    const modal = document.getElementById('my_modal_3') as HTMLDialogElement;
    if (!emailAndphone || !password || !captcha) {
      setMsg('请填写所有字段');
      modal?.showModal();
      return; 
    }
      
    const response = await request(`${Constants.LOGIN_URL}?${Constants.TIMESTAMP_KEY}=${Date.now()}`, {
      method: 'POST',
      [Constants.HEADER_CK]: _ck,
      body: JSON.stringify({
        emailAndphone,  
        password,
        captcha
      }),
    });


  };

  // 在组件加载时初始化验证码
  useEffect(() => {
    fetchCaptcha();
  }, []);


  return (
    <div className="flex items-center justify-center h-screen bg-gradient-to-br from-blue-300 to-purple-200">
      <dialog id="my_modal_3" className="modal">
        <div className="modal-box">
          <form method="dialog">
            {/* if there is a button in form, it will close the modal */}
            <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
          </form>
          <h3 className="font-bold text-lg">警告</h3>
          <p className="py-4">{msg}</p>
        </div>
      </dialog>
      <div className="w-full max-w-md bg-white/30 backdrop-blur-md p-8 rounded-2xl shadow-lg">
        <h1 className="text-3xl font-bold text-center text-white mb-6">Sign in to your account        </h1>
        <form className="space-y-6" onSubmit={handleSubmit}>
          {/* 用户名/邮箱 */}
          <div className="form-control">
            <label htmlFor="email" className="label">
              <span className="label-text text-white">用户名/邮箱</span>
            </label>
            <input
              type="text"
              id="email"
              placeholder="请输入用户名或邮箱"
              value={emailAndphone}
              onChange={(e) => setEmailAndphone(e.target.value)}
              className="input input-bordered w-full bg-white/50 text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* 密码 */}
          <div className="form-control">
            <label htmlFor="password" className="label">
              <span className="label-text text-white">密码</span>
            </label>
            <input
              type="password"
              id="password"
              placeholder="请输入密码"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="input input-bordered w-full bg-white/50 text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* 验证码 */}
          <div className="form-control">
            <label htmlFor="captcha" className="label">
              <span className="label-text text-white">验证码</span>
            </label>
            <div className="flex items-center space-x-4">
              <input
                type="text"
                id="captcha"
                placeholder="请输入验证码"
                value={captcha}
                onChange={(e) => setCaptcha(e.target.value)}
                className="input input-bordered w-3/5 bg-white/50 text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              {captchaSrc ? (<img className='w-2/5 h-9 rounded-lg'
                onClick={handleCaptchaClick}
                src={captchaSrc}
                alt="checkcode"
              />) : (<span className="loading loading-ring loading-xl"></span>

              )}
            </div>
          </div>

          {/* 登录按钮 */}
          <div className="form-control mt-6">
            <button
              type="submit"
              className="btn btn-primary w-full bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              登录
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
