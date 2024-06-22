import React, { useState, useEffect } from 'react';
import Filter from './Filter';
import DisplayProblem from './Displayproblems';

const Problems = () => {
  const [selectedCategoryIds, setSelectedCategoryIds] = useState([]);
  const [selectedDifficultyIds, setSelectedDifficultyIds] = useState([]);
  const [solved, setSolved] = useState(false);
  // useEffect to log selected difficulty and category IDs
  useEffect(() => {
    console.log('Selected Difficulty IDs:', selectedDifficultyIds);
    console.log('Selected Category IDs:', selectedCategoryIds);
  }, [selectedDifficultyIds, selectedCategoryIds]);

  return (
    <div className="grid grid-cols-3 min-h-screen bg-gray-100" style={{ gridTemplateColumns: '250px 2fr 150px' }}>
      {/* Sidebar */}
      <div className="h-screen overflow-y-auto">
        <Filter
          setSelectedCategoryIds={setSelectedCategoryIds}
          setSelectedDifficultyIds={setSelectedDifficultyIds}
          selectedCategoryIds={selectedCategoryIds}
          selectedDifficultyIds={selectedDifficultyIds}
          solved={solved}
          setSolved={setSolved}
        />
      </div>

      {/* Main Content */}
      <div className="grid grid-rows-[auto_1fr]">
        <div className="p-6 pr-20">
          <div className="mb-6">
            <input
              type="text"
              placeholder="Search"
              className="w-full p-2 border rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
          </div>
        </div>
        <div className="relative overflow-hidden">
          <div className="absolute inset-0 overflow-y-auto">
            <DisplayProblem selectedCategoryIds={selectedCategoryIds} selectedDifficultyIds={selectedDifficultyIds} />
          </div>
        </div>
      </div>

      {/* Vertical Progress Bar */}
     
    </div>
  );
};

export default Problems;
