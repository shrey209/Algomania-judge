import React from 'react';

const Smallcard = ({ points }) => {
  return (
    <div className="bg-white shadow-md rounded-lg p-4 flex-grow h-24 flex flex-col justify-center items-center min-w-[96px]">
      <h3 className="text-lg font-bold mb-2">Points</h3>
      <p className="text-xl text-purple-500">{points}</p>
    </div>
  );
};

export default Smallcard;
