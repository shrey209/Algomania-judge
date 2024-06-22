import React, { useState, useEffect } from 'react';
import { mdiAccountOutline, mdiLockOutline } from '@mdi/js';
import Icon from '@mdi/react';
import image from '../images/image.png';
import debounce from 'lodash.debounce';
import axios from 'axios';
import { useAuth } from '../context/context';
import { useNavigate } from 'react-router-dom';

const SignupComponent = () => {
  const [username, setUsername] = useState('');
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);
  const [isLoading, setIsLoading] = useState(false); 
  const { setLogin, setJwt, setUserid, setGlobalUsername } = useAuth();
  const navigate = useNavigate();
  const handleUsernameChange = (e) => {
    const { value } = e.target;
    setUsername(value);
    setError(false);
  };

  const handleFirstnameChange = (e) => {
    const { value } = e.target;
    setFirstname(value);
  };

  const handleLastnameChange = (e) => {
    const { value } = e.target;
    setLastname(value);
  };

  const handlePasswordChange = (e) => {
    const { value } = e.target;
    setPassword(value);
  };

  // Debounced function for API call
  const debouncedCheckUsername = debounce((username) => {
    setIsLoading(true);
    fetch(`http://localhost:8080/auth/finduser?username=${encodeURIComponent(username)}`)
      .then((response) => {
        setIsLoading(false);
        if (response.ok) {
          setError(true); // Username exists (assuming 200 means username exists)
        } else {
          setError(false); // Username does not exist
        }
      })
      .catch((error) => {
        setIsLoading(false);
        console.error('Error checking username:', error);
        setError(false); // Handle error case
      });
  }, 300);

  useEffect(() => {
    // Only perform API call if username is not empty
    if (username.trim() !== '') {
      debouncedCheckUsername(username);
    }
    // Cancel debounce on component unmount
    return () => {
      debouncedCheckUsername.cancel();
    };
  }, [username]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Username:', username);
    console.log('Firstname:', firstname);
    console.log('Lastname:', lastname);
    console.log('Password:', password);
  
    // Perform signup API call
    const postData = {
      username,
      firstname,
      lastname,
      password,
    };
  
    try {
      const response = await axios.post('http://localhost:8080/auth/register', postData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = response.data;
      console.log('Registration successful:', data);
      setJwt(data.jwt);
      setUserid(data.userid);
      setGlobalUsername(username); 
      setLogin(true);
      navigate('/');
    } catch (error) {
      console.error('Error during registration:', error.message);
      if (error.response && error.response.data) {
        console.error('Server response:', error.response.data);
      }
    }
  };

  return (
    <div className="min-w-screen min-h-screen bg-white flex items-center justify-center px-5 py-5">
      <div className="bg-purple-100 text-gray-500 rounded-3xl shadow-xl w-full overflow-hidden" style={{ maxWidth: '1000px' }}>
        <div className="md:flex w-full">
          <div className="hidden md:block w-1/2 bg-purple-500 py-10 px-10">
            <img src={image} alt="Illustration" className="w-full h-auto" />
          </div>
          <div className="w-full md:w-1/2 py-10 px-5 md:px-10">
            <div className="text-center mb-10">
              <h1 className="font-bold text-3xl text-purple-900">REGISTER</h1>
              <p>Enter your information to register</p>
            </div>
            <div>
              <div className="flex -mx-3">
                <div className="w-1/2 px-3 mb-5">
                  <label className="text-xs font-semibold px-1">First name</label>
                  <div className="flex">
                    <div className="w-10 z-10 pl-1 text-center pointer-events-none flex items-center justify-center">
                      <Icon path={mdiAccountOutline} size={1} className="text-gray-400" />
                    </div>
                    <input type="text" className="w-full -ml-10 pl-10 pr-3 py-2 rounded-lg border-2 border-purple-200 outline-none focus:border-purple-500" placeholder="John" value={firstname} onChange={handleFirstnameChange} />
                  </div>
                </div>
                <div className="w-1/2 px-3 mb-5">
                  <label className="text-xs font-semibold px-1">Last name</label>
                  <div className="flex">
                    <div className="w-10 z-10 pl-1 text-center pointer-events-none flex items-center justify-center">
                      <Icon path={mdiAccountOutline} size={1} className="text-gray-400" />
                    </div>
                    <input type="text" className="w-full -ml-10 pl-10 pr-3 py-2 rounded-lg border-2 border-purple-200 outline-none focus:border-purple-500" placeholder="Smith" value={lastname} onChange={handleLastnameChange} />
                  </div>
                </div>
              </div>
              <div className="flex -mx-3">
                <div className="w-full px-3 mb-5">
                  <label className="text-xs font-semibold px-1">Username{error && <span className="text-xs text-red-500 ml-2">Username is taken</span>}</label>
                  <div className="flex items-center">
                    <div className="flex-grow">
                      <div className="flex">
                        <div className="w-10 z-10 pl-1 text-center pointer-events-none flex items-center justify-center">
                          <Icon path={mdiAccountOutline} size={1} className="text-gray-400" />
                        </div>
                        <input
                          type="text"
                          className={`w-full -ml-10 pl-10 pr-3 py-2 rounded-lg border-2 ${error ? 'border-red-500' : 'border-purple-500'} outline-none`}
                          placeholder="username"
                          value={username}
                          onChange={handleUsernameChange}
                        />
                      </div>
                    </div>
                   
                  </div>
                </div>
              </div>
              <div className="flex -mx-3">
                <div className="w-full px-3 mb-12">
                  <label className="text-xs font-semibold px-1">Password</label>
                  <div className="flex">
                    <div className="w-10 z-10 pl-1 text-center pointer-events-none flex items-center justify-center">
                      <Icon path={mdiLockOutline} size={1} className="text-gray-400" />
                    </div>
                    <input type="password" className="w-full -ml-10 pl-10 pr-3 py-2 rounded-lg border-2 border-purple-200 outline-none focus:border-purple-500" placeholder="************" value={password} onChange={handlePasswordChange} />
                  </div>
                </div>
              </div>
              <div className="flex -mx-3">
                <div className="w-full px-3 mb-5">
                  <button onClick={handleSubmit} className="block w-full max-w-xs mx-auto bg-purple-500 hover:bg-purple-700 focus:bg-purple-700 text-white rounded-lg px-3 py-3 font-semibold">
                    REGISTER NOW
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignupComponent;
