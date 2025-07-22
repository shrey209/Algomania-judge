import React from 'react';
import { Loader2 } from 'lucide-react';

export function LoadingSpinner({ message = "Loading..." }) {
  return (
    <div className="flex flex-col items-center justify-center space-y-4 px-4 text-center">
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

      {/* Loading message */}
      <div className="text-purple-800 font-medium text-lg">
        {message}
      </div>

      {/* Info message */}
      <p className="text-purple-600/80 text-sm max-w-xs">
        ⏳ Please be patient...
      </p>

      {/* Reason for slowness */}
      <p className="text-purple-600/80 text-sm max-w-xs">
        🚧 The app is hosted on Azure App Service (Free Tier), which only provides 60 minutes of compute per day and has cold starts (1–2 min). Also, the PostgreSQL database is deployed in Virginia 🌍, adding 5–10 seconds of latency. Sorry about that!
      </p>

      {/* Watch video link */}
      <p className="text-purple-600/90 text-sm max-w-xs">
        👉 Don’t want to wait? Watch the demo video here: <br />
        <a
          href="https://youtu.be/HC56CV8ce04?si=wiwPe0YDmtJgKUZM"
          target="_blank"
          rel="noopener noreferrer"
          className="text-purple-700 underline hover:text-purple-900"
        >
          https://youtu.be/HC56CV8ce04?si=wiwPe0YDmtJgKUZM
        </a>
      </p>
    </div>
  );
}
