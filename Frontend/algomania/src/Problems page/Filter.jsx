import React, { useState, useEffect } from 'react';
import DifficultyService from '../Services/DifficultyService';
import CategoryService from '../Services/Categoryservice';
import { useAuth } from '../context/context';

const Filter = ({ setSelectedDifficultyIds, setSelectedCategoryIds, selectedCategoryIds, selectedDifficultyIds,
  setSolved,solved}) => {
  const [difficulties, setDifficulties] = useState([]);
  const [categories, setCategories] = useState([]);
  const [tempSelectedDifficultyIds, setTempSelectedDifficultyIds] = useState([]);
  const [tempSelectedCategoryIds, setTempSelectedCategoryIds] = useState([]);
  const { login } = useAuth();
  

  useEffect(() => {
    const fetchDifficulties = async () => {
      try {
        const data = await DifficultyService.getAllDifficulty();
        setDifficulties(data);
      } catch (error) {
        console.error('Failed to fetch difficulty levels', error);
      }
    };

    const fetchCategories = async () => {
      try {
        const data = await CategoryService.getAllCategories();
        setCategories(data);
      } catch (error) {
        console.error('Failed to fetch categories', error);
      }
    };

    fetchDifficulties();
    fetchCategories();
  }, []);

  const handleDifficultyChange = (id) => {
    setTempSelectedDifficultyIds((prevIds) =>
      prevIds.includes(id) ? prevIds.filter((difficultyId) => difficultyId !== id) : [...prevIds, id]
    );
  };

  const handleCategoryChange = (id) => {
    setTempSelectedCategoryIds((prevIds) =>
      prevIds.includes(id) ? prevIds.filter((categoryId) => categoryId !== id) : [...prevIds, id]
    );
  };

  const handleSolvedChange = () => {
    setSolved((prevSolved) => !prevSolved);
  };

  const handleApply = () => {
    setSelectedDifficultyIds(tempSelectedDifficultyIds);
    setSelectedCategoryIds(tempSelectedCategoryIds);
  };

  return (
    <div className="bg-purple-700 p-6 text-white">
      <h2 className="text-2xl font-bold mb-4">Filters</h2>
      <button
        className="bg-purple-500 hover:bg-purple-600 text-white px-4 py-2 rounded-lg mb-6"
        onClick={handleApply}
      >
        Apply
      </button>
      {login && (
        <div className="mb-6">
          <h3 className="text-xl font-semibold mb-2">Solved</h3>
          <label>
            <input
              type="checkbox"
              className="mr-2"
              checked={solved}
              onChange={handleSolvedChange}
            />
            Solved
          </label>
        </div>
      )}
      <div className="mb-6">
        <h3 className="text-xl font-semibold mb-2">Difficulty</h3>
        <ul>
          {difficulties.map((difficulty) => (
            <li key={difficulty.id} className="mb-1">
              <label>
                <input
                  type="checkbox"
                  className="mr-2"
                  checked={tempSelectedDifficultyIds.includes(difficulty.id)}
                  onChange={() => handleDifficultyChange(difficulty.id)}
                />
                {difficulty.level} <span className="text-gray-300">({difficulty.count})</span>
              </label>
            </li>
          ))}
        </ul>
      </div>
      <div className="mb-6">
        <h3 className="text-xl font-semibold mb-2">Category</h3>
        <ul>
          {categories.map((category) => (
            <li key={category.id} className="mb-1">
              <label>
                <input
                  type="checkbox"
                  className="mr-2"
                  checked={tempSelectedCategoryIds.includes(category.id)}
                  onChange={() => handleCategoryChange(category.id)}
                />
                {category.type} <span className="text-gray-300">({category.count})</span>
              </label>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Filter;
