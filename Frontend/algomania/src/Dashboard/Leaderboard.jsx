import React, { useState, useEffect } from 'react';
import { MdLeaderboard } from "react-icons/md";
import axios from 'axios';

const Leaderboard = () => {
  const [scores, setScores] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8011/leaderboard/top-scores/10')
      .then((response) => {
        setScores(response.data);
      })
      .catch((error) => {
        console.error('Error fetching leaderboard data:', error);
      });
  }, []);

  return (
    <div>
      <div className="flex items-center justify-center mt-7 p-4">
        <MdLeaderboard className="text-6xl text-purple-600 mr-2" />
        <h2 className="text-2xl font-bold text-purple-800">Leaderboard</h2>
      </div>
      <div className="flex bg-purple-500 text-white p-3">
        <div className="flex-1 text-lg font-bold">#Rank</div>
        <div className="flex-1 text-lg font-bold text-center">Username</div>
        <div className="flex-1 text-lg font-bold text-right">Points</div>
      </div>
      {scores.map((score, index) => (
        <div key={index} className="flex bg-purple-300 text-white p-2 border-b-2 border-purple-1000">
          <div className="flex-1 text-md font-bold">#{index + 1}</div>
          <div className="flex-1 text-md font-bold text-center">{score.playerId}</div>
          <div className="flex-1 text-md font-bold text-right">{score.score}</div>
        </div>
      ))}
    </div>
  );
};

export default Leaderboard;
