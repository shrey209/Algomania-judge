import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/context';
import { useNavigate } from 'react-router-dom';

const SignInComponent = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const { setLogin, setJwt, setUserid, setGlobalUsername, setUserinformation } = useAuth();
  const navigate = useNavigate();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSignIn = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      const response = await fetch('http://localhost:8001/auth/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        const token = await response.json();
        setJwt(token.jwt);
        setLogin(true);
        setUserid(token.userid);
        setGlobalUsername(username);

        // Fetch additional user information if needed
        const userInfoResponse = await fetch(`http://localhost:8001/users/${token.userid}`, {
          headers: {
            'Authorization': `Bearer ${token.jwt}`
          }
        });

        if (userInfoResponse.ok) {
          const userInfo = await userInfoResponse.json();
          setUserinformation(userInfo);
        }

        navigate('/');
      } else {
        setError('Sign-in failed. Please check your credentials.');
      }
    } catch (err) {
      setError('An error occurred during sign-in.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      <section className="bg-white min-h-screen flex items-center justify-center">
        <div className="w-full max-w-md mx-auto bg-white rounded-lg shadow-lg overflow-hidden dark:bg-gray-800">
          <div className="p-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight text-gray-900 dark:text-white text-center mb-4">
              Sign in to your account
            </h1>
            <form className="space-y-4" onSubmit={handleSignIn}>
              <div>
                <label htmlFor="username" className="block mb-1 text-sm font-medium text-gray-900 dark:text-gray-300">
                  Username
                </label>
                <input
                  type="text"
                  name="username"
                  id="username"
                  value={username}
                  onChange={handleUsernameChange}
                  className={`bg-gray-200 dark:bg-gray-700 border border-gray-300 text-gray-900 dark:text-gray-300 sm:text-sm rounded-lg focus:ring-purple-600 focus:border-purple-600 block w-full p-3 ${
                    error ? 'border-red-500' : ''
                  }`}
                  placeholder="Enter username"
                  autoComplete="username"
                  required
                />
              </div>
              <div>
                <label htmlFor="password" className="block mb-1 text-sm font-medium text-gray-900 dark:text-gray-300">
                  Password
                </label>
                <input
                  type="password"
                  name="password"
                  id="password"
                  value={password}
                  onChange={handlePasswordChange}
                  className="bg-gray-200 dark:bg-gray-700 border border-gray-300 text-gray-900 dark:text-gray-300 sm:text-sm rounded-lg focus:ring-purple-600 focus:border-purple-600 block w-full p-3"
                  placeholder="••••••••"
                  autoComplete="current-password"
                  required
                />
              </div>
              <div className="flex items-center justify-between">
                <div className="flex items-start">
                  <input
                    id="remember"
                    aria-describedby="remember"
                    type="checkbox"
                    className="w-4 h-4 border-gray-300 rounded bg-gray-200 dark:bg-gray-700 focus:ring-3 focus:ring-purple-600 dark:focus:ring-purple-600 dark:ring-offset-gray-800"
                  />
                  <label htmlFor="remember" className="ml-2 text-sm text-gray-900 dark:text-gray-300">
                    Remember me
                  </label>
                </div>
                <Link to="/forgot-password" className="text-sm font-medium text-purple-600 hover:underline dark:text-purple-400">
                  Forgot password?
                </Link>
              </div>
              <button
                type="submit"
                className={`w-full bg-purple-600 hover:bg-purple-700 focus:ring-4 focus:outline-none focus:ring-purple-300 text-white font-medium rounded-lg text-sm px-5 py-3 mt-4 transition-colors duration-300 ease-in-out ${
                  isLoading ? 'opacity-50 cursor-not-allowed' : ''
                }`}
                disabled={isLoading}
              >
                {isLoading ? 'Signing in...' : 'Sign in'}
              </button>
              <p className="text-sm text-gray-900 dark:text-gray-300 mt-2">
                Don’t have an account yet?{' '}
                <Link to="/signup" className="font-medium text-purple-600 hover:underline dark:text-purple-400">
                  Sign up
                </Link>
              </p>
              {error && (
                <p className="text-sm text-red-500 dark:text-red-400 mt-2">
                  {error}
                </p>
              )}
            </form>
          </div>
        </div>
      </section>
    </div>
  );
};

export default SignInComponent;
