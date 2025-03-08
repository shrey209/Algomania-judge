import React from 'react';
import { X, Github, Chrome } from 'lucide-react';
import { BASE_URL } from "../constant"; 

function SignInModal({ isOpen, onClose }) {
  if (!isOpen) return null;
  

  const handleGithubSignIn = () => {
    window.location.href = `https://github.com/login/oauth/authorize?client_id=Ov23li4s00ZjAYcdwaGD&redirect_uri=${encodeURIComponent(
        BASE_URL+"/auth/github/callback"
      )}&scope=user:email`;
  };

  const handleGoogleSignIn = () => {
    const currentPath = "/"; 
    const state = encodeURIComponent(currentPath);
  console.log(currentPath)
    window.location.href = `https://accounts.google.com/o/oauth2/auth?client_id=233826009158-r0qpugemh5dd8jkke6n8uer1ajqr1m7u.apps.googleusercontent.com&redirect_uri=${encodeURIComponent(
      BASE_URL+"/auth/google/callback"
    )}&response_type=code&scope=openid%20email%20profile&state=${state}`;
  };

  return (
    <div className="fixed inset-0 bg-purple-900/20 backdrop-blur-sm flex items-center justify-center z-50">
      <div className="bg-white rounded-2xl p-8 w-full max-w-md relative shadow-xl shadow-purple-200">
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-purple-400 hover:text-purple-600 transition-colors"
        >
          <X size={24} />
        </button>
        
        <h2 className="text-2xl font-bold text-center mb-8 text-purple-900">Welcome Back</h2>
        
        <div className="space-y-4">
          <button
            onClick={handleGithubSignIn}
            className="w-full flex items-center justify-center gap-3 bg-purple-900 text-white py-3.5 rounded-xl hover:bg-purple-800 transition-colors shadow-md hover:shadow-lg"
          >
            <Github size={20} />
            <span className="font-medium">Continue with GitHub</span>
          </button>
          
          <button
            onClick={handleGoogleSignIn}
            className="w-full flex items-center justify-center gap-3 bg-white border-2 border-purple-100 py-3.5 rounded-xl hover:bg-purple-50 transition-colors shadow-sm hover:shadow-md"
          >
            <Chrome size={20} className="text-purple-600" />
            <span className="font-medium text-purple-900">Continue with Google</span>
          </button>
        </div>
        
        <p className="text-center text-sm text-purple-600/80 mt-8">
          By continuing, you agree to our Terms of Service and Privacy Policy
        </p>
      </div>
    </div>
  );
}

export default SignInModal;
