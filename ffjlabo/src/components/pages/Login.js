import React from 'react';

const Login = () => {
  const login = () => {
    const url = `${API_ENDPOINT}/auth`;
    window.location.href = url;
  };

  return (
    <div>
      <p>YouTube認証が必要です</p>
      <button onClick={login}>認証</button>
    </div>
  );
};

export default Login;
