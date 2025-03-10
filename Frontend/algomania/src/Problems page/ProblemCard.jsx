import React from 'react';
import { useNavigate } from 'react-router-dom';

const ProblemCard = ({ id, problemDetailsId, difficulty, category, title }) => {
  const navigate = useNavigate();

  const handleSolveClick = () => {
    navigate(`/problemDetails/${problemDetailsId}`, {
      state: { problemDetailsId, difficulty, category ,title}
    });
  };

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

  return (
    <div className="flex w-full items-center justify-between rounded-lg bg-purple-100 p-4 shadow-md">
      <div className="flex items-center space-x-4">
        <div className="text-lg font-bold text-purple-600">{id}</div>
        <div>
          <div className="text-lg font-semibold text-gray-800">{title}</div>
          <div className="text-sm text-gray-600">
          <span className="text-purple-600">Difficulty: <span className={getDifficultyColor(difficulty)}>{difficulty}</span></span>
            <span className="ml-2 text-purple-600">Category: {category}</span>
          </div>
        </div>
      </div>
      <button 
        onClick={handleSolveClick}
        className="transform rounded-lg bg-purple-600 px-4 py-2 font-bold text-white transition duration-300 hover:-translate-y-1 hover:bg-purple-700 hover:shadow-lg"
      >
        Solve
      </button>
    </div>
  );
};

export default ProblemCard;
