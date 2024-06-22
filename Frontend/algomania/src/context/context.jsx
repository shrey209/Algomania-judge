// AuthContext.jsx

import React, { createContext, useState, useContext, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const [login, setLogin] = useState(() => {
    return JSON.parse(localStorage.getItem('login')) || false;
  });

  const [jwt, setJwt] = useState(() => {
    return localStorage.getItem('jwt') || null;
  });

  const [userid, setUserid] = useState(() => {
    return localStorage.getItem('userid') || null;
  });

  const [Globalusername, setGlobalUsername] = useState(() => {
    return localStorage.getItem('Globalusername') || null;
  });

  const [userinformation, setUserinformation] = useState(() => {
    return JSON.parse(localStorage.getItem('userinformation')) || {};
  });

  useEffect(() => {
    localStorage.setItem('login', JSON.stringify(login));
  }, [login]);

  useEffect(() => {
    localStorage.setItem('jwt', jwt);
  }, [jwt]);

  useEffect(() => {
    localStorage.setItem('userid', userid);
  }, [userid]);

  useEffect(() => {
    localStorage.setItem('Globalusername', Globalusername);
  }, [Globalusername]);

  useEffect(() => {
    localStorage.setItem('userinformation', JSON.stringify(userinformation));
  }, [userinformation]);

  return (
    <AuthContext.Provider value={{ login, setLogin, jwt, setJwt, userid, setUserid, Globalusername, setGlobalUsername, userinformation, setUserinformation }}>
      {children}
    </AuthContext.Provider>
  );
};
