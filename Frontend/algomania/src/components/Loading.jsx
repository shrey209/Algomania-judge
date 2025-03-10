import React from 'react';
import { Loader2 } from 'lucide-react';

export function LoadingSpinner({ message = "Loading..." }) {
  return (
    <div className="flex flex-col items-center justify-center space-y-4">
      <div className="relative">
        {/* Main spinner */}
        <div className="w-16 h-16 border-4 border-purple-200 rounded-full animate-spin">
          <div className="absolute top-0 left-0 w-full h-full border-t-4 border-purple-600 rounded-full animate-spin-fast"></div>
        </div>
        {/* Centered loader icon */}
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
          <Loader2 className="w-6 h-6 text-purple-600 animate-spin" />
        </div>
      </div>
      {/* Loading text */}
      <div className="text-purple-800 font-medium text-lg">
        {message}
      </div>
      {/* Additional info message */}
      <p className="text-purple-600/80 text-sm text-center max-w-xs">
        Please be patient
      </p>
    </div>
  );
}