import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/context';

const Solved = () => {
  const { userinformation } = useAuth();
  const [page, setPage] = useState(1);
  const [problems, setProblems] = useState([]);

  useEffect(() => {
    const fetchProblems = async () => {
      try {
        const params = {
          page: page,
          problemIds: userinformation.solvedProblemsId 
        };

        const queryString = new URLSearchParams(params).toString();
        const url = `http://localhost:8080/Algomania/problem/getByList?${queryString}`;

        const response = await fetch(url);
        if (response.ok) {
          const data = await response.json();
          setProblems(data.content); 
        } else {
          console.error('Failed to fetch data');
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchProblems();
  }, [userinformation.solvedProblemsId, page]);

  const handleNext = () => {
    setPage(prevPage => prevPage + 1);
  };

  const handlePrev = () => {
    setPage(prevPage => (prevPage > 1 ? prevPage - 1 : 1));
  };

  console.log(userinformation);

  return (
    <div className="flex items-center justify-center h-full w-full p-4">
      <div className="bg-white shadow-lg rounded-lg w-full h-full m-4 p-4">
        <div className="flex justify-between items-center">
          <div className="text-xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-purple-400 via-purple-600 to-purple-900">
            Solved Problems List
          </div>
          <div className="mt-4 flex space-x-2">
            <button 
              onClick={handlePrev} 
              className="bg-purple-600 text-white px-2 py-1 rounded hover:bg-purple-700">
              Prev
            </button>
            <button 
              onClick={handleNext} 
              className="bg-purple-600 text-white px-2 py-1 rounded hover:bg-purple-700">
              Next
            </button>
          </div>
        </div>
        
        {/* Displaying problems */}
        {problems.map(problem => (
          <div key={problem.id} className="flex justify-between items-center border-t border-b border-purple-300 py-2 px-4 mt-2">
            <div className="text-left">
              {problem.id}
            </div>
            <div className="text-center flex-1">
              {problem.title}
            </div>
          </div>
        ))}
        
      </div>
    </div>
  );
};

export default Solved;
