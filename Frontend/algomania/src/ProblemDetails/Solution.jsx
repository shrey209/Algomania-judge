import React, { useEffect, useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import axios from 'axios';
import { BASE_URL } from '../constant';
import { Code, BookOpen, Lightbulb, Brain } from 'lucide-react';

const Solution = () => {
  const location = useLocation();
  const { solutionId } = useParams(); 
  const { problemDetailsId = '', difficulty = '', category = '', title = '' } = location.state || {};

  const [solutionData, setSolutionData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  
  const getDifficultyColor = (level) => {
    switch (level) {
      case 'Easy':
        return 'bg-green-500 text-white';
      case 'Medium':
        return 'bg-yellow-500 text-black';
      case 'Hard':
        return 'bg-red-500 text-white';
      default:
        return 'bg-gray-500 text-white';
    }
  };

  useEffect(() => {
    const fetchSolution = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/Algomania/Solution/get/${solutionId}`);
        console.log("Solution Data:", response.data); 
        setSolutionData(response.data); 
      } catch (err) {
        console.error("Error fetching solution:", err);
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    if (solutionId) {
      fetchSolution();
    }
  }, [solutionId]);

  return (
    <div className="min-h-screen bg-purple-50 p-4">
      <header className="bg-purple-800 text-white py-4 px-6 shadow-lg">
        <div className="max-w-6xl mx-auto">
          <h1 className="text-2xl font-bold">Problem {problemDetailsId || solutionId}: {title}</h1>
          <div className="flex gap-4 mt-2">
            <strong>Difficulty:</strong> 
            <span className={`px-3 py-1 ml-2 rounded-full ${getDifficultyColor(difficulty)}`}>
              {difficulty}
            </span>
            <span className="bg-purple-700 px-3 py-1 rounded-full text-sm">{category}</span>
          </div>
        </div>
      </header>

      <main className="max-w-6xl mx-auto py-8 px-6">
        {loading && <p>Loading solution...</p>}
        {error && <p className="text-red-500">Error fetching solution</p>}

        {solutionData && (
          <div className="grid gap-6">
            {/* Overview Section */}
            <section className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center gap-2 mb-4">
                <BookOpen className="text-purple-600 w-6 h-6" />
                <h2 className="text-xl font-semibold text-purple-900">Overview</h2>
              </div>
              <p className="text-gray-700 whitespace-pre-line">{solutionData.overview}</p>
            </section>

            {/* Intuition Section */}
            <section className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center gap-2 mb-4">
                <Lightbulb className="text-purple-600 w-6 h-6" />
                <h2 className="text-xl font-semibold text-purple-900">Intuition</h2>
              </div>
              <p className="text-gray-700 whitespace-pre-line">{solutionData.intuition}</p>
            </section>

            {/* Algorithm Section */}
            <section className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center gap-2 mb-4">
                <Brain className="text-purple-600 w-6 h-6" />
                <h2 className="text-xl font-semibold text-purple-900">Algorithm</h2>
              </div>
              <p className="text-gray-700 whitespace-pre-line">{solutionData.algorithm}</p>
            </section>

            {/* Code Section */}
            <section className="bg-white rounded-lg shadow-md p-6">
              <div className="flex items-center gap-2 mb-4">
                <Code className="text-purple-600 w-6 h-6" />
                <h2 className="text-xl font-semibold text-purple-900">Solution Code</h2>
              </div>
              <pre className="bg-gray-100 p-4 rounded text-sm overflow-auto">
                {solutionData.code}
              </pre>
            </section>
          </div>
        )}
      </main>
    </div>
  );
};

export default Solution;
