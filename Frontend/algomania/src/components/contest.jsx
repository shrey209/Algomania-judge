import React from "react";
import { Timer, Github } from "lucide-react";

const Contest = () => {
  return (
    <div className="bg-white rounded-2xl shadow-2xl p-8 mb-12">
      <h2 className="text-3xl font-bold text-purple-800 mb-4">Coming Soon</h2>
      <div className="flex items-center justify-center mb-6">
        <Timer className="w-8 h-8 text-purple-600 animate-pulse" />
      </div>
      <p className="text-gray-600 mb-8">
        We're working hard to bring you the most exciting competitive programming platform.
      </p>
      <a
        href="https://github.com/shrey209/Algomania-judge.git"
        target="_blank"
        rel="noopener noreferrer"
        className="inline-flex items-center gap-2 bg-purple-900 text-white px-6 py-3 rounded-lg hover:bg-purple-800 transition-colors"
      >
        <Github className="w-5 h-5" />
        Help us on GitHub
      </a>
    </div>
  );
};

export default Contest;
